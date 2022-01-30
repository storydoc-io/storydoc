package io.storydoc.server.storydoc.infra.store;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.storydoc.server.storydoc.domain.Artifact;
import io.storydoc.server.storydoc.domain.*;
import io.storydoc.server.storydoc.domain.action.*;
import io.storydoc.server.storydoc.infra.store.model.StoryDoc;
import io.storydoc.server.storydoc.infra.store.model.*;
import io.storydoc.server.workspace.app.WorkspaceQueryService;
import io.storydoc.server.workspace.app.WorkspaceService;
import io.storydoc.server.workspace.app.WorkspaceSettings;
import io.storydoc.server.workspace.domain.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class StoryDocStorageImpl implements StoryDocStorage {

    private ObjectMapper objectMapper;

    private final WorkspaceService workspaceService;

    private final WorkspaceQueryService workspaceQueryService;

    public StoryDocStorageImpl(WorkspaceSettings folderSettings, WorkspaceService workspaceService, WorkspaceQueryService workspaceQueryService, WorkspaceQueryService workspaceQueryService1) {
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
    public FolderURN getArtifactBlockFolder(StoryDocId storyDocId, BlockId blockId) {
        return new FolderURN(List.of(storyDocId.getId(), blockId.getId()));
    }

    public FolderURN getArtifactBlockFolder(ArtifactBlockCoordinate coordinate) {
        return getArtifactBlockFolder(coordinate.getStoryDocId(), coordinate.getBlockId());
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
    public void renameBlock(ArtifactBlockCoordinate blockCoordinate, String new_name) {
        StoryDoc storyDoc = loadDocument(blockCoordinate.getStoryDocId());
        Block block = lookupBlock(blockCoordinate.getBlockId(), storyDoc);
        block.setName(new_name);
        saveDocument(storyDoc);
    }

    // todo merge with createBinaryCollectionArtifact()
    @Override
    public void addArtifact(StoryDocId storyDocId, BlockId blockId, ArtifactId artifactId, ArtifactMetaData metaData) {
        StoryDoc storyDoc = loadDocument(storyDocId);
        Block block  = lookupBlock(blockId, storyDoc);
        ArtifactBlock artifactBlock = (ArtifactBlock) block;

        if (artifactBlock.getArtifacts()==null) {
            artifactBlock.setArtifacts(new ArrayList<>());
        }

        io.storydoc.server.storydoc.infra.store.model.Artifact artifact = new io.storydoc.server.storydoc.infra.store.model.Artifact();
        artifact.setArtifactId(artifactId.getId());
        artifact.setArtifactType(metaData.getType());
        artifact.setName(metaData.getName());
        artifactBlock.getArtifacts().add(artifact);

        saveDocument(storyDoc);
     }

    @Override
    public void createBinaryCollectionArtifact(CreateBinaryCollectionArtifactAction action) {
        StoryDoc storyDoc = loadDocument(action.getCoordinate().getStoryDocId());
        Block block  = lookupBlock(action.getCoordinate().getBlockId(), storyDoc);
        ArtifactBlock artifactBlock = (ArtifactBlock) block;

        if (artifactBlock.getArtifacts()==null) {
            artifactBlock.setArtifacts(new ArrayList<>());
        }

        io.storydoc.server.storydoc.infra.store.model.Artifact artifact = new io.storydoc.server.storydoc.infra.store.model.Artifact();
        artifact.setArtifactId(action.getArtifactId().getId());
        artifact.setArtifactType(action.getArtifactType());
        artifact.setName(action.getArtifactName());
        artifact.setBinary(true);
        artifact.setCollection(true);
        artifact.setBinaryType(action.getBinaryType());
        artifact.setItems(new ArrayList<>());

        artifactBlock.getArtifacts().add(artifact);

        saveDocument(storyDoc);


    }

    @Override
    public ArtifactMetaData getArtifactMetaData(ArtifactBlockCoordinate coordinate, ArtifactId artifactId) {
        StoryDoc storyDoc = loadDocument(coordinate.getStoryDocId());
        ArtifactBlock block = (ArtifactBlock) lookupBlock(coordinate.getBlockId(), storyDoc);
        io.storydoc.server.storydoc.infra.store.model.Artifact artifact = block.getArtifacts().stream()
                .filter(a -> a.getArtifactId().equals(artifactId.getId()))
                .findFirst()
                .get();
        return ArtifactMetaData.builder()
                .name(artifact.getName())
                .type(artifact.getArtifactType())
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

    public Block getBlock(ArtifactBlockCoordinate coordinate) {
        StoryDoc storyDoc = loadDocument(coordinate.getStoryDocId());
        return lookupBlock(coordinate.getBlockId(), storyDoc);
    }

    private Block lookupBlock(BlockId blockId, StoryDoc storyDoc) {
        return lookupBlock(blockId, storyDoc.getBlocks());
    }

    private Block lookupBlock(BlockId blockId, List<Block> blocks) {
        for (Block block: blocks) {
            if (block instanceof Section) {
                Section section = (Section) block;
                Block subBlock = lookupBlock(blockId, section.getBlocks());
                if (subBlock != null) return subBlock;
            } else {  // (!(block instanceof Section))
                if (block.getId().equals(blockId.getId().toString())) {
                    return block;
                }
            }
        }
        return null;
    }


    @Override
    public void removeBlock(StoryDocId storyDocId, BlockId blockId) throws StoryDocException {
        try {
            StoryDoc storyDoc = loadDocument(storyDocId);

            Block block = storyDoc.getBlocks().stream()
                    .filter(b -> b.getId().equals(blockId.getId()))
                    .findFirst()
                    .orElseThrow(() -> new StoryDocException("cannot delete block with id " + blockId + " no such block found"));

            storyDoc.getBlocks().remove(block);

            saveDocument(storyDoc);

            if (block instanceof ArtifactBlock) {
                FolderURN artifactBlockFolder = getArtifactBlockFolder(storyDocId, blockId);
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

    public List<ArtifactId> getArtifacts(ArtifactBlockCoordinate coordinate, Function<ArtifactMetaData, Boolean> filter) {
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
            throw new StoryDocException("could not load storydoc " + storyDocId);
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
    public void saveArtifact(ArtifactSaveContext context) {
        try {
            workspaceService.saveResource(new ResourceSaveContext() {
                @Override
                public ResourceUrn getResourceUrn() {
                    return getArtifactBlockFolder(context.getStoryDocId(), context.getBlockId()).resolve(context.getRelativeUrn());
                }

                @Override
                public void write(OutputStream outputStream) throws IOException {
                    context.getSerializer().write(outputStream);
                }
            });

        } catch (WorkspaceException e) {
            throw new StoryDocException("could not save artifact " + context.getRelativeUrn());
        }
    }

    @Override
    public <A extends Artifact> A loadArtifact(ArtifactLoadContext context) {
        try {
            return workspaceService.loadResource(new ResourceLoadContext() {
                @Override
                public ResourceUrn getResourceUrn() {
                    return getArtifactBlockFolder(context.getBlockCoordinate().getStoryDocId(), context.getBlockCoordinate().getBlockId()).resolve(context.getRelativeUrn());
                }

                @Override
                public WorkspaceResource read(InputStream inputStream) throws IOException {
                    return context.getDeserializer().read(inputStream);
                }
            });

        } catch (WorkspaceException e) {
            throw new StoryDocException("could not save artifact " + context.getRelativeUrn(), e);
        }
    }

    @Override
    public void saveBinaryArtifact(SaveBinaryArtifactContext context) {
        try {
            workspaceService.saveResource(new ResourceSaveContext() {
                @Override
                public ResourceUrn getResourceUrn() {
                    return getArtifactBlockFolder(context.getCoordinate())
                            .resolve(ResourceUrn.of(context.getArtifactId().getId()));
                }

                @Override
                public void write(OutputStream outputStream) throws IOException {
                    context.getInputStream().transferTo(outputStream);
                }
            });

        } catch (WorkspaceException e) {
            throw new StoryDocException("could not save binary artifact ", e);
        }
    }

    @Override
    public void addItemToBinaryCollection(AddToBinaryCollectionAction action) {
        StoryDoc storyDoc = loadDocument(action.getCoordinate().getStoryDocId());
        ArtifactBlock block = (ArtifactBlock) lookupBlock(action.getCoordinate().getBlockId(), storyDoc);
        io.storydoc.server.storydoc.infra.store.model.Artifact artifact  = block.getArtifacts().stream()
            .filter(a -> a.getArtifactId().equals(action.getArtifactId().getId()))
            .findFirst()
            .get();
        artifact.getItems().add(ArtifactItem.builder()
            .id(action.getItemId().getId())
            .name(action.getItemName())
            .build());
        saveDocument(storyDoc);

        try {
            workspaceService.saveResource(new ResourceSaveContext() {
                @Override
                public ResourceUrn getResourceUrn() {
                    ItemId itemId = action.getItemId();
                    ArtifactBlockCoordinate coordinate = action.getCoordinate();
                    return StoryDocStorageImpl.this.getItemResourceUrn(coordinate, itemId);
                }

                @Override
                public void write(OutputStream outputStream) throws IOException {
                    action.getInputStream().transferTo(outputStream);
                }
            });

        } catch (WorkspaceException e) {
            throw new StoryDocException("could not save binary artifact ", e);
        }
    }

    private ResourceUrn getItemResourceUrn(ArtifactBlockCoordinate coordinate, ItemId itemId) {
        ResourceUrn relativeItemResourceUrn = ResourceUrn.of(itemId.getId());
        return getArtifactBlockFolder(coordinate).resolve(relativeItemResourceUrn);
    }

    @Override
    public InputStream getBinaryFromCollection(ArtifactBlockCoordinate coordinate, ArtifactId artifactId, ItemId itemId) throws WorkspaceException {
        ResourceUrn urn = getItemResourceUrn(coordinate, itemId);
        return workspaceQueryService.getInputStream(urn);
    }


}
