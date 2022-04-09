package io.storydoc.server.storydoc.infra.store;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.storydoc.server.storydoc.domain.Artifact;
import io.storydoc.server.storydoc.domain.*;
import io.storydoc.server.storydoc.infra.store.model.StoryDoc;
import io.storydoc.server.storydoc.infra.store.model.*;
import io.storydoc.server.workspace.app.WorkspaceQueryService;
import io.storydoc.server.workspace.app.WorkspaceService;
import io.storydoc.server.workspace.domain.*;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class StoryDocStorageImpl implements StoryDocStorage {

    private ObjectMapper objectMapper;

    private final WorkspaceService workspaceService;

    private final WorkspaceQueryService workspaceQueryService;

    public StoryDocStorageImpl(WorkspaceService workspaceService, WorkspaceQueryService workspaceQueryService1) {
        this.workspaceService = workspaceService;
        this.workspaceQueryService = workspaceQueryService1;
        initMapper();
    }

    private void initMapper() {
        objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    private FolderURN getStoryDocRootFolder() {
        return workspaceQueryService.getRootFolder().getUrn();
    }

    @Override
    public ResourceUrn getStoryDocUrn(StoryDocId storyDocId) {
        String fileName = (storyDocId.getId() + ".json");
        return new ResourceUrn(getStoryDocFolder(storyDocId), fileName);
    }

    @Override
    public ResourceUrn getStoryDocsUrn() {
        String fileName = "storydocs.json";
        return new ResourceUrn(new FolderURN(List.of()), fileName);
    }


    private FolderURN getStoryDocFolder(StoryDocId storyDocId) {
        return new FolderURN(List.of(storyDocId.getId()));
    }

    @Override
    public FolderURN getBlockFolder(StoryDocId storyDocId, BlockId blockId) {
        return new FolderURN(List.of(storyDocId.getId(), blockId.getId()));
    }

    public FolderURN getBlockFolder(BlockCoordinate coordinate) {
        return getBlockFolder(coordinate.getStoryDocId(), coordinate.getBlockId());
    }

    @Override
    public ResourceUrn getArtifactUrn(ArtifactCoordinate artifactCoordinate) {
        return getBlockFolder(artifactCoordinate.getBlockCoordinate())
                .resolve(getArtifactUrnRelativeToBlockFolder(artifactCoordinate.getArtifactId()));
    }

    private ResourceUrn getArtifactUrnRelativeToBlockFolder(ArtifactId artifactId) {
        return new ResourceUrn(new FolderURN(List.of()), artifactId.getId() + ".json");
    }

    public FolderURN getCollectionFolder(ArtifactCoordinate artifactCoordinate) {
        return getBlockFolder(artifactCoordinate.getBlockCoordinate())
                .resolve(getCollectionFolderRelativeToBlockFolder(artifactCoordinate.getArtifactId()));
    }

    private FolderURN getCollectionFolderRelativeToBlockFolder(ArtifactId artifactId) {
        return new FolderURN(List.of(artifactId.getId()));
    }

    @Override
    public ResourceUrn getCollectionItemUrn(ArtifactCoordinate artifactCoordinate, ItemId itemId) {
        return getCollectionFolder(artifactCoordinate)
                .resolve(getCollectionItemUrnRelativeToCollectionFolder(itemId));
    }

    private ResourceUrn getCollectionItemUrnRelativeToCollectionFolder(ItemId itemId) {
        return new ResourceUrn(new FolderURN(List.of()), itemId.getId());
    }

    @Override
    public void createDocument(StoryDocId storyDocId, String name) {
        StoryDocMetaData storyDocMetaData = new StoryDocMetaData();
        storyDocMetaData.setId(storyDocId.getId());
        storyDocMetaData.setName(name);

        StoryDocs storyDocs = loadDocuments();
        storyDocs.getStoryDocs().add(storyDocMetaData);

        saveDocuments(storyDocs);

        StoryDoc storyDoc = new StoryDoc();
        storyDoc.setId(storyDocId.getId());
        storyDoc.setBlocks(new ArrayList<>());

        workspaceService.addFolder(getStoryDocRootFolder(), storyDocId.getId());
        saveDocument(storyDoc);
    }


    @Override
    public void addArtifactBlock(StoryDocId storyDocId, BlockId blockId, String name) {
        FolderURN parentFolder = getStoryDocFolder(storyDocId);
        workspaceService.addFolder(parentFolder, blockId.getId());

        StoryDoc storyDoc = loadDocument(storyDocId);
        ArtifactBlock block = createArtifactBlock(blockId, name);
        storyDoc.getBlocks().add(block);
        saveDocument(storyDoc);

    }

    @Override
    public void addArtifactBlock(StoryDocId storyDocId, BlockId blockId, SectionId sectionId) {
        String name = null;
        StoryDoc storyDoc = loadDocument(storyDocId);
        Section section = lookupSection(sectionId, storyDoc);
        Block block = createArtifactBlock(blockId, name);
        section.getBlocks().add(block);
        saveDocument(storyDoc);
    }

    private ArtifactBlock createArtifactBlock(BlockId blockId, String name) {
        ArtifactBlock block = new ArtifactBlock();
        block.setId(blockId.getId());
        block.setName(name);
        block.setTags(new ArrayList<>());
        return block;
    }

    @Override
    public void renameBlock(BlockCoordinate blockCoordinate, String new_name) {
        StoryDoc storyDoc = loadDocument(blockCoordinate.getStoryDocId());
        Block block = lookupBlock(blockCoordinate.getBlockId(), storyDoc);
        block.setName(new_name);
        saveDocument(storyDoc);
    }

    @Override
    public void renameArtifact(BlockCoordinate blockCoordinate, ArtifactId artifactId, String new_name) {
        StoryDoc storyDoc = loadDocument(blockCoordinate.getStoryDocId());
        Block block = lookupBlock(blockCoordinate.getBlockId(), storyDoc);
        ArtifactBlock artifactBlock = (ArtifactBlock) block;
        io.storydoc.server.storydoc.infra.store.model.Artifact artifact = artifactBlock.getArtifacts().stream()
                .filter(a -> a.getArtifactId().equals(artifactId.getId()))
                .findFirst()
                .get();
        artifact.setName(new_name);
        saveDocument(storyDoc);
    }

    @Override
    public void changeArtifactState(ArtifactCoordinate coordinate, ArtifactState state) {
        StoryDoc storyDoc = loadDocument(coordinate.getBlockCoordinate().getStoryDocId());
        Block block = lookupBlock(coordinate.getBlockCoordinate().getBlockId(), storyDoc);
        ArtifactBlock artifactBlock = (ArtifactBlock) block;
        io.storydoc.server.storydoc.infra.store.model.Artifact artifact = lookupArtifact(coordinate.getArtifactId(), artifactBlock);
        artifact.setState(state);
        saveDocument(storyDoc);
    }

    @Override
    public void moveBlock(BlockCoordinate coordinateBlockToMove, BlockCoordinate coordinateNewParent, int indexInNewParent) {
        StoryDoc storyDoc = loadDocument(coordinateBlockToMove.getStoryDocId());
        CompositeBlock currentParentBlock = lookupParentBlock(coordinateBlockToMove.getBlockId(), storyDoc);
        CompositeBlock newParentBlock = (CompositeBlock) lookupBlock(coordinateNewParent.getBlockId(), storyDoc);
        Block blockToMove = lookupBlock(coordinateBlockToMove.getBlockId(), currentParentBlock);
        int indexInCurrentParent = IntStream.range(0, currentParentBlock.getBlocks().size())
                .filter(idx -> currentParentBlock.getBlocks().get(idx).getId().equals(blockToMove.getId()))
                .findFirst().getAsInt();
        currentParentBlock.getBlocks().remove(indexInCurrentParent);
        newParentBlock.getBlocks().add(indexInNewParent, blockToMove);
        saveDocument(storyDoc);
    }

    public void createArtifact(BlockCoordinate blockCoordinate, ArtifactId artifactId, ArtifactMetaData metaData) {
        StoryDoc storyDoc = loadDocument(blockCoordinate.getStoryDocId());
        Block block  = lookupBlock(blockCoordinate.getBlockId(), storyDoc);
        ArtifactBlock artifactBlock = (ArtifactBlock) block;

        if (artifactBlock.getArtifacts()==null) {
            artifactBlock.setArtifacts(new ArrayList<>());
        }

        io.storydoc.server.storydoc.infra.store.model.Artifact artifact = new io.storydoc.server.storydoc.infra.store.model.Artifact();
        artifact.setArtifactId(artifactId.getId());
        artifact.setArtifactType(metaData.getType());
        artifact.setName(metaData.getName());
        artifact.setState(ArtifactState.CREATED);
        artifact.setCollection(metaData.isCollection());
        if (metaData.isCollection()) {
            artifact.setItems(new ArrayList<>());
            workspaceService.createFolder(getCollectionFolder(ArtifactCoordinate.of(blockCoordinate, artifactId)));
        }
        artifact.setBinary(metaData.isBinary());
        if (metaData.isBinary()) {
            artifact.setBinaryType(metaData.getBinaryType());
        }
        artifactBlock.getArtifacts().add(artifact);

        saveDocument(storyDoc);

    }

    public void addArtifactAssociation(ArtifactCoordinate coordinateFrom, ArtifactCoordinate coordinateTo, String name) {
        StoryDoc storyDoc = loadDocument(coordinateFrom.getBlockCoordinate().getStoryDocId());
        Block block = lookupBlock(coordinateFrom.getBlockCoordinate().getBlockId(), storyDoc);
        io.storydoc.server.storydoc.infra.store.model.Artifact artifact = lookupArtifact(coordinateFrom.getArtifactId(), (ArtifactBlock) block);
        if (artifact.getAssociations()==null) {
            artifact.setAssociations(new ArrayList<>());
        }
        artifact.getAssociations().add(Association.builder()
                .documentIdFrom(coordinateFrom.getBlockCoordinate().getStoryDocId().getId())
                .blockIdFrom(coordinateFrom.getBlockCoordinate().getBlockId().getId())
                .artifactIdFrom(coordinateFrom.getArtifactId().getId())
                .documentIdTo(coordinateTo.getBlockCoordinate().getStoryDocId().getId())
                .blockIdTo(coordinateTo.getBlockCoordinate().getBlockId().getId())
                .artifactIdTo(coordinateTo.getArtifactId().getId())
                .name(name)
                .build());
        saveDocument(storyDoc);
    }

    @Override
    public List<Association> getAssociations(ArtifactCoordinate coordinateFrom) {
        StoryDoc storyDoc = loadDocument(coordinateFrom.getBlockCoordinate().getStoryDocId());
        Block block = lookupBlock(coordinateFrom.getBlockCoordinate().getBlockId(), storyDoc);
        io.storydoc.server.storydoc.infra.store.model.Artifact artifact = lookupArtifact(coordinateFrom.getArtifactId(), (ArtifactBlock) block);
        List<Association> associations = artifact.getAssociations();
        if (associations==null) return new ArrayList<>();
        return associations;
    }

    @Override
    @SneakyThrows
    public void removeArtifact(ArtifactCoordinate artifactCoordinate) {
        StoryDoc storyDoc = loadDocument(artifactCoordinate.getBlockCoordinate().getStoryDocId());
        ArtifactBlock block = (ArtifactBlock) lookupBlock(artifactCoordinate.getBlockCoordinate().getBlockId(), storyDoc);
        io.storydoc.server.storydoc.infra.store.model.Artifact artifact = lookupArtifact(artifactCoordinate.getArtifactId(), block);
        block.getArtifacts().remove(artifact);
        saveDocument(storyDoc);

        ArtifactMetaData metaData = getMetaData(artifact);
        if (metaData.isCollection()) {
            workspaceService.deleteFolder(getCollectionFolder(artifactCoordinate), true);
        }
        if (!metaData.isBinary()) {
            workspaceService.deleteResource(getArtifactUrn(artifactCoordinate));
        }

    }

    private io.storydoc.server.storydoc.infra.store.model.Artifact lookupArtifact(ArtifactId artifactId, ArtifactBlock block) {
        return block.getArtifacts().stream()
                .filter(a -> a.getArtifactId().equals(artifactId.getId()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public ArtifactMetaData getArtifactMetaData(BlockCoordinate coordinate, ArtifactId artifactId) {
        StoryDoc storyDoc = loadDocument(coordinate.getStoryDocId());
        ArtifactBlock block = (ArtifactBlock) lookupBlock(coordinate.getBlockId(), storyDoc);
        io.storydoc.server.storydoc.infra.store.model.Artifact artifact = block.getArtifacts().stream()
                .filter(a -> a.getArtifactId().equals(artifactId.getId()))
                .findFirst()
                .get();
        return ArtifactMetaData.builder()
                .name(artifact.getName())
                .type(artifact.getArtifactType())
                .state(artifact.getState())
                .build();
    }

    private Section lookupSection(SectionId sectionId, StoryDoc storyDoc) {
        for (Block block: storyDoc.getBlocks()){
            if (block instanceof Section) {
                Section section = (Section) block;
                if (section.getId().equals(sectionId.getId())) {
                    return section;
                } else {
                    // recurse
                }
            }
        }
        return null;
    }

    public Block getBlock(BlockCoordinate coordinate) {
        StoryDoc storyDoc = loadDocument(coordinate.getStoryDocId());
        return lookupBlock(coordinate.getBlockId(), storyDoc);
    }

    private Block lookupBlock(BlockId blockId, Block block) {
        if (block.getId().equals(blockId.getId())) return block;
        if (block instanceof CompositeBlock) {
            CompositeBlock compositeBlock = (CompositeBlock) block;
            for (Block subBlock : compositeBlock.getBlocks()) {
                Block foundBlock = lookupBlock(blockId, subBlock);
                if (foundBlock != null) return foundBlock;
            }
        }
        return null;
    }

    private CompositeBlock lookupParentBlock(BlockId blockId, Block block) {
        if (block instanceof CompositeBlock) {
            CompositeBlock parentBlock = (CompositeBlock) block;
            for (Block childBlock : parentBlock.getBlocks()) {
                if (childBlock.getId().equals(blockId.getId())) {
                    return parentBlock;
                }
                CompositeBlock foundParentBlock = lookupParentBlock(blockId, childBlock);
                if (foundParentBlock != null) return foundParentBlock;
            }
        }
        return null;
    }

    private ArtifactItem lookupItem(ItemId itemId, io.storydoc.server.storydoc.infra.store.model.Artifact artifact) {
        return artifact.getItems().stream()
                .filter(item -> item.getId().equals(itemId.getId()))
                .findFirst()
                .get();
    }

    @Override
    public void removeBlock(BlockCoordinate blockCoordinate) throws StoryDocException {
        try {
            StoryDoc storyDoc = loadDocument(blockCoordinate.getStoryDocId());

            CompositeBlock parentBlock = lookupParentBlock(blockCoordinate.getBlockId(), storyDoc);
            Block block = lookupBlock(blockCoordinate.getBlockId(), parentBlock);
            parentBlock.getBlocks().remove(block);

            saveDocument(storyDoc);

            if (block instanceof ArtifactBlock) {
                FolderURN artifactBlockFolder = getBlockFolder(blockCoordinate);
                workspaceService.deleteFolder(artifactBlockFolder, true);
            }
        } catch (WorkspaceException e) {
            throw new StoryDocException("could not remove block", e);
        }
    }

    @Override
    public void addSection(StoryDocId storyDocId, SectionId sectionId) {
        StoryDoc storyDoc = loadDocument(storyDocId);

        Section section = new Section();
        section.setId(sectionId.getId());
        section.setBlocks(new ArrayList<>());

        storyDoc.getBlocks().add(section);

        saveDocument(storyDoc);
    }

    @Override
    public void addTag(StoryDocId storyDocId, BlockId blockId, String tag) {
        StoryDoc storyDoc = loadDocument(storyDocId);
        Block block = lookupBlock(blockId, storyDoc);
        block.getTags().add(tag);
        saveDocument(storyDoc);
    }

    public List<ArtifactId> findArtifacts(BlockCoordinate coordinate, Function<ArtifactMetaData, Boolean> filter) {
        StoryDoc storyDoc = loadDocument(coordinate.getStoryDocId());
        ArtifactBlock block = (ArtifactBlock)lookupBlock(coordinate.getBlockId(), storyDoc);
        return block.getArtifacts().stream()
            .filter(artifact -> filter.apply(getMetaData(artifact)))
            .map(a -> ArtifactId.fromString(a.getArtifactId()))
            .collect(Collectors.toList());
    }

    private ArtifactMetaData getMetaData(io.storydoc.server.storydoc.infra.store.model.Artifact artifact) {
        return ArtifactMetaData.builder()
                .type(artifact.getArtifactType())
                .collection(artifact.isCollection())
                .binary(artifact.isBinary())
                .binaryType(artifact.getBinaryType())
                .build();
    }

    public StoryDocs loadDocuments()  {
        try {
            return workspaceService.loadResource(new ResourceLoadContext<StoryDocs>() {
                @Override
                public ResourceUrn getResourceUrn() {
                    return getStoryDocsUrn();
                }

                @Override
                public StoryDocs read(InputStream inputStream) throws IOException {
                    return objectMapper.readValue(inputStream, StoryDocs.class);
                }
            });
        } catch (WorkspaceException e) {
            return new StoryDocs(new ArrayList<>());
        }
    }

    @Override
    public StoryDoc loadDocument(StoryDocId storyDocId) throws StoryDocException {
        try {
            return workspaceService.loadResource(new ResourceLoadContext<StoryDoc>() {
                @Override
                public ResourceUrn getResourceUrn() {
                    return getStoryDocUrn(storyDocId);
                }

                @Override
                public StoryDoc read(InputStream inputStream) throws IOException {
                    return objectMapper.readValue(inputStream, StoryDoc.class);
                }
            });
        } catch (WorkspaceException e) {
            throw new StoryDocException("could not load storydoc " + storyDocId, e);
        }
    }

    @Override
    public void removeDocument(StoryDocId storyDocId) throws StoryDocException{
        try {
            StoryDocs storyDocs = loadDocuments();
            StoryDocMetaData toRemove = getStoryDocMetaData(storyDocId, storyDocs);
            storyDocs.getStoryDocs().remove(toRemove);
            saveDocuments(storyDocs);

            workspaceService.deleteResource(getStoryDocUrn(storyDocId));
            workspaceService.deleteFolder(getStoryDocFolder(storyDocId), true);
        } catch(WorkspaceException e) {
            throw new StoryDocException("could not remove document " + storyDocId, e);
        }
    }

    private StoryDocMetaData getStoryDocMetaData(StoryDocId storyDocId, StoryDocs storyDocs) {
        return storyDocs.getStoryDocs().stream()
                .filter(metaData -> metaData.getId().equals(storyDocId.getId()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void renameDocument(StoryDocId storyDocId, String new_name) {
        StoryDocs storyDocs = loadDocuments();
        StoryDocMetaData toRename = getStoryDocMetaData(storyDocId, storyDocs);
        toRename.setName(new_name);
        saveDocuments(storyDocs);
    }

    private void saveDocuments(StoryDocs storyDocs) throws StoryDocException {
        try {
            workspaceService.saveResource(new ResourceSaveContext() {
                @Override
                public ResourceUrn getResourceUrn() {
                    return getStoryDocsUrn();
                }

                @Override
                public void write(OutputStream outputStream) throws IOException {
                    objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputStream, storyDocs);
                }
            });
        } catch (WorkspaceException e) {
            throw new StoryDocException("could not save storydocs ", e);
        }
    }

    private void saveDocument(StoryDoc storyDoc) throws StoryDocException {
        try {
            workspaceService.saveResource(new ResourceSaveContext() {
                @Override
                public ResourceUrn getResourceUrn() {
                    return getStoryDocUrn(StoryDocId.fromString(storyDoc.getId()));
                }

                @Override
                public void write(OutputStream outputStream) throws IOException {
                    objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputStream, storyDoc);
                }
            });
        } catch (WorkspaceException e) {
            throw new StoryDocException("could not save storydoc " + storyDoc.getId(), e);
        }
    }

    @Override
    public void setArtifactContent(ArtifactCoordinate artifactCoordinate, ArtifactSerializer serializer) {
        try {
            workspaceService.saveResource(new ResourceSaveContext() {
                @Override
                public ResourceUrn getResourceUrn() {
                    return getArtifactUrn(artifactCoordinate);
                }

                @Override
                public void write(OutputStream outputStream) throws IOException {
                    serializer.write(outputStream);
                }
            });

        } catch (WorkspaceException e) {
            throw new StoryDocException("could not save artifact " + artifactCoordinate);
        }
    }

    @Override
    public <A extends Artifact> A getArtifactContent(ArtifactCoordinate coordinate, ArtifactDeserializer deserializer) {
        try {
            return workspaceService.loadResource(new ResourceLoadContext() {
                @Override
                public ResourceUrn getResourceUrn() {
                    return getArtifactUrn(coordinate);
                }

                @Override
                public WorkspaceResource read(InputStream inputStream) throws IOException {
                    return deserializer.read(inputStream);
                }
            });

        } catch (WorkspaceException e) {
            throw new StoryDocException("could not save artifact " + coordinate, e);
        }
    }

    @Override
    public void saveBinaryArtifact(ArtifactCoordinate artifactCoordinate, InputStream inputStream) {
        try {
            workspaceService.saveResource(new ResourceSaveContext() {
                @Override
                public ResourceUrn getResourceUrn() {
                    return getBlockFolder(artifactCoordinate.getBlockCoordinate())
                            .resolve(ResourceUrn.of(artifactCoordinate.getArtifactId().getId()));
                }

                @Override
                public void write(OutputStream outputStream) throws IOException {
                    inputStream.transferTo(outputStream);
                }
            });

        } catch (WorkspaceException e) {
            throw new StoryDocException("could not save binary artifact ", e);
        }
    }

    @Override
    public void addItemToBinaryCollection(ArtifactCoordinate coordinate, String itemName, ItemId itemId, InputStream inputStream) {
        StoryDoc storyDoc = loadDocument(coordinate.getBlockCoordinate().getStoryDocId());
        ArtifactBlock block = (ArtifactBlock) lookupBlock(coordinate.getBlockCoordinate().getBlockId(), storyDoc);
        io.storydoc.server.storydoc.infra.store.model.Artifact artifact  = lookupArtifact(coordinate.getArtifactId(), block);
        artifact.getItems().add(ArtifactItem.builder()
            .id(itemId.getId())
            .name(itemName)
            .build());
        saveDocument(storyDoc);

        try {
            workspaceService.saveResource(new ResourceSaveContext() {
                @Override
                public ResourceUrn getResourceUrn() {
                    return getCollectionItemUrn(coordinate, itemId);
                }

                @Override
                public void write(OutputStream outputStream) throws IOException {
                    inputStream.transferTo(outputStream);
                }
            });

        } catch (WorkspaceException e) {
            throw new StoryDocException("could not save binary artifact ", e);
        }
    }

    @Override
    public void renameItemFromBinaryCollection(ArtifactCoordinate artifactCoordinate, ItemId itemId, String name) {
        BlockCoordinate blockCoordinate = artifactCoordinate.getBlockCoordinate();
        StoryDoc storyDoc = loadDocument(blockCoordinate.getStoryDocId());
        ArtifactBlock block = (ArtifactBlock) lookupBlock(blockCoordinate.getBlockId(), storyDoc);
        io.storydoc.server.storydoc.infra.store.model.Artifact artifact  = lookupArtifact(artifactCoordinate.getArtifactId(), block);
        ArtifactItem itemToRename = lookupItem(itemId, artifact);
        itemToRename.setName(name);
        saveDocument(storyDoc);
    }

    @Override
    public void removeItemFromBinaryCollection(ArtifactCoordinate artifactCoordinate, ItemId itemId) {
        BlockCoordinate blockCoordinate = artifactCoordinate.getBlockCoordinate();
        StoryDoc storyDoc = loadDocument(blockCoordinate.getStoryDocId());
        ArtifactBlock block = (ArtifactBlock) lookupBlock(blockCoordinate.getBlockId(), storyDoc);
        io.storydoc.server.storydoc.infra.store.model.Artifact artifact  = lookupArtifact(artifactCoordinate.getArtifactId(), block);
        ArtifactItem itemToRemove = lookupItem(itemId, artifact);
        artifact.getItems().remove(itemToRemove);
        saveDocument(storyDoc);
    }

    @Override
    public InputStream getBinaryFromCollection(ArtifactCoordinate coordinate, ItemId itemId) throws WorkspaceException {
        ResourceUrn urn = getCollectionItemUrn(coordinate, itemId);
        return workspaceQueryService.getInputStream(urn);
    }


}
