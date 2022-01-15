package io.storydoc.server.storydoc.infra.store;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.storydoc.server.storydoc.domain.*;
import io.storydoc.server.storydoc.domain.action.ArtifactLoadContext;
import io.storydoc.server.storydoc.domain.action.ArtifactSaveContext;
import io.storydoc.server.storydoc.domain.action.SaveBinaryArtifactContext;
import io.storydoc.server.storydoc.infra.store.model.ArtifactBlock;
import io.storydoc.server.storydoc.infra.store.model.Block;
import io.storydoc.server.storydoc.infra.store.model.Section;
import io.storydoc.server.storydoc.infra.store.model.StoryDoc;
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
    public void createDocument(StoryDocId storyDocId) {
        StoryDoc storyDoc = new StoryDoc();
        storyDoc.setId(storyDocId.getId());
        storyDoc.setBlocks(new ArrayList<>());

        workspaceService.addFolder(getStoryDocRootFolder(), storyDocId.getId());
        saveDocument(storyDoc);
    }


    @Override
    public void addArtifactBlock(StoryDocId storyDocId, BlockId blockId) {
        FolderURN parentFolder = getStoryDocFolder(storyDocId);
        workspaceService.addFolder(parentFolder, blockId.getId());

        StoryDoc storyDoc = loadDocument(storyDocId);
        ArtifactBlock block = createArtifactBlock(blockId);
        storyDoc.getBlocks().add(block);
        saveDocument(storyDoc);

    }

    @Override
    public void addArtifactBlock(StoryDocId storyDocId, BlockId blockId, SectionId sectionId) {
        StoryDoc storyDoc = loadDocument(storyDocId);
        Section section = lookupSection(sectionId, storyDoc);
        Block block = createArtifactBlock(blockId);
        section.getBlocks().add(block);
        saveDocument(storyDoc);
    }

    private ArtifactBlock createArtifactBlock(BlockId blockId) {
        ArtifactBlock block = new ArtifactBlock();
        block.setId(blockId.getId());
        block.setTags(new ArrayList<>());
        return block;
    }

    @Override
    public void addArtifact(StoryDocId storyDocId, BlockId blockId, ArtifactId artifactId, ArtifactMetaData metaData) {
        StoryDoc storyDoc = loadDocument(storyDocId);
        Block block  = lookupBlock(blockId, storyDoc.getBlocks());
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
    public ArtifactMetaData getArtifactMetaData(ArtifactBlockCoordinate coordinate, ArtifactId artifactId) {
        StoryDoc storyDoc = loadDocument(coordinate.getStoryDocId());
        ArtifactBlock block = (ArtifactBlock) lookupBlock(coordinate.getBlockId(), storyDoc.getBlocks());
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
    public void removeBlock(StoryDocId storyDocId, BlockId blockId) {
        StoryDoc storyDoc = loadDocument(storyDocId);

        Block block = storyDoc.getBlocks().stream()
                .filter(b->b.getId().equals(blockId.getId().toString()))
                .findFirst()
                .orElseThrow(() -> new StoryDocException("cannot delete block with id " + blockId + " no such block found"));

        storyDoc.getBlocks().remove(block);

        saveDocument(storyDoc);
    }

    @Override
    public void addSection(StoryDocId storyDocId, SectionId sectionId) {
        StoryDoc storyDoc = loadDocument(storyDocId);

        Section section = new Section();
        section.setId(sectionId.getId().toString());
        section.setBlocks(new ArrayList<>());

        storyDoc.getBlocks().add(section);

        saveDocument(storyDoc);
    }

    @Override
    public void addTag(StoryDocId storyDocId, BlockId blockId, String tag) {
        StoryDoc storyDoc = loadDocument(storyDocId);
        Block block = lookupBlock(blockId, storyDoc.getBlocks());
        block.getTags().add(tag);
        saveDocument(storyDoc);
    }

    public List<ArtifactId> getArtifacts(ArtifactBlockCoordinate coordinate, Function<ArtifactMetaData, Boolean> filter) {
        StoryDoc storyDoc = loadDocument(coordinate.getStoryDocId());
        ArtifactBlock block = (ArtifactBlock)lookupBlock(coordinate.getBlockId(), storyDoc.getBlocks());
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
                    return getArtifactBlockFolder(context.getStoryDocId(), context.getBlockId()).resolve(context.getRelativeUrn());
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


}
