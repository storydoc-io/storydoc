package io.storydoc.server.storydoc.app;

import io.storydoc.server.storydoc.app.dto.*;
import io.storydoc.server.storydoc.domain.*;
import io.storydoc.server.storydoc.infra.store.model.Artifact;
import io.storydoc.server.storydoc.infra.store.model.StoryDoc;
import io.storydoc.server.storydoc.infra.store.model.*;
import io.storydoc.server.workspace.domain.FolderURN;
import io.storydoc.server.workspace.domain.WorkspaceException;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoryDocQueryServiceImpl implements StoryDocQueryService {

    private final StoryDocStorage storyDocStorage;

    public StoryDocQueryServiceImpl(StoryDocStorage storyDocStorage) {
        this.storyDocStorage = storyDocStorage;
    }

    @Override
    public List<StoryDocSummaryDTO> getStoryDocs() {
        return storyDocStorage.loadDocuments().getStoryDocs().stream()
            .map(this::toSummary)
            .collect(Collectors.toList());
    }

    private StoryDocSummaryDTO toSummary(StoryDocMetaData metaData) {
        return StoryDocSummaryDTO.builder()
                .storyDocId(StoryDocId.fromString(metaData.getId()))
                .name(metaData.getName())
                .build();
    }

    @Override
    public StoryDocSummaryDTO getStoryDocSummary(StoryDocId storyDocId) {
        return storyDocStorage.loadDocuments().getStoryDocs().stream()
                .filter(metaData -> metaData.getId().equals(storyDocId.getId()))
                .findFirst()
                .map(this::toSummary)
                .orElse(null);
    }

    @Override
    public StoryDocDTO getDocument(StoryDocId storyDocId) {

        StoryDocMetaData storyDocMetaData = storyDocStorage.loadDocuments().getStoryDocs().stream()
                .filter(metaData -> metaData.getId().equals(storyDocId.getId()))
                .findFirst()
                .orElse(null);

        if (storyDocMetaData==null) {
            return null;
        } else {
            StoryDoc storyDoc = storyDocStorage.loadDocument(storyDocId);

            List<BlockDTO> blockDTOList = new ArrayList<>();
            addBlocks(storyDoc.getBlocks(), blockDTOList, null, new int[]{});


            return StoryDocDTO.builder()
                    .storyDocId(storyDocId)
                    .urn(storyDocStorage.getStoryDocUrn(storyDocId))
                    .blocks(blockDTOList)
                    .title(storyDocMetaData.getName())
                    .build();
        }
    }

    private void addBlocks(List<Block> blocks, List<BlockDTO> blockDTOList, BlockId parentBlockId, int[] parentNumbering) {
        int nr = 1;
        for (Block block: blocks) {
            BlockId blockId = BlockId.fromString(block.getId());
            int[] numbering = combinedNumbering(parentNumbering, nr);

            BlockDTO.BlockDTOBuilder blockDTO = BlockDTO.builder()
                    .blockId(blockId)
                    .numbering(numbering)
                    .blockType(block.getBlockType())
                    .title(block.getName())
                    .parentBlockId(parentBlockId);

            if (block instanceof Section) {
                blockDTO
                    .blockType("SECTION");
                blockDTOList.add(blockDTO.build());
                Section section = (Section) block;
                addBlocks(section.getBlocks(), blockDTOList, blockId, numbering);
            } else if (block instanceof ArtifactBlock) {
                blockDTO
                     .blockType("ARTIFACT");

                ArtifactBlock artifactBlock = (ArtifactBlock) block;

                if (artifactBlock.getArtifacts()!=null) {
                    blockDTO
                            .artifacts(artifactBlock.getArtifacts().stream()
                                    .map((artifact) -> toArtifactDTO(artifact))
                                    .collect(Collectors.toList()));
                } else {
                    blockDTO.artifacts(new ArrayList<>());
                }
                blockDTOList.add(blockDTO.build());
            }
            nr++;
        }
    }

    private ArtifactDTO toArtifactDTO(Artifact artifact) {
        return ArtifactDTO.builder()
                .artifactId(ArtifactId.fromString(artifact.getArtifactId()))
                .name(artifact.getName())
                .artifactType(artifact.getArtifactType())
                .binary(artifact.isBinary())
                .collection(artifact.isCollection())
                .binaryType(artifact.getBinaryType())
                .items(artifact.getItems()==null? null : artifact.getItems().stream()
                    .map(item -> ItemDTO.builder()
                        .id(item.getId())
                        .name(item.getName())
                        .build()
                    )
                    .collect(Collectors.toList())
                )
                .urn(artifact.getUrn())
                .build();
    }

    private int[] combinedNumbering(int[] numbering, int nr) {
        int[] result = Arrays.copyOf(numbering, numbering.length + 1); //create new array from old array and allocate one more element
        result[result.length - 1] = nr;
        return result;
    }

    @Override
    public FolderURN getArtifactBlockFolder(ArtifactBlockCoordinate blockCoordinate) {
        return storyDocStorage.getArtifactBlockFolder(blockCoordinate.getStoryDocId(), blockCoordinate.getBlockId());
    }

    @Override
    public List<ArtifactId> getArtifactsByType(ArtifactBlockCoordinate coordinate, String artifactType) {
        return storyDocStorage.getArtifacts(coordinate, (metaData)-> metaData.getType().equals(artifactType) );
    }

    @Override
    public ArtifactMetaData getArtifactMetaData(ArtifactBlockCoordinate coordinate, ArtifactId artifactId) {
        return storyDocStorage.getArtifactMetaData(coordinate, artifactId);
    }

    @Override
    public ArtifactDTO getArtifact(ArtifactBlockCoordinate coordinate, ArtifactId artifactId) {
        ArtifactBlock artifactBlock = (ArtifactBlock) storyDocStorage.getBlock(coordinate);
        Artifact artifact = artifactBlock.getArtifacts().stream()
            .filter(a -> a.getArtifactId().equals(artifactId.getId()))
            .findFirst()
            .get();
        return toArtifactDTO(artifact);

    }

    @Override
    public InputStream getBinaryFromCollection(ArtifactBlockCoordinate coordinate, ArtifactId artifactId, ItemId itemId) throws WorkspaceException {
        return storyDocStorage.getBinaryFromCollection(coordinate, artifactId, itemId);
    }
}
