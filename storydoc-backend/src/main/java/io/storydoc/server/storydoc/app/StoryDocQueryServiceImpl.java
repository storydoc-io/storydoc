package io.storydoc.server.storydoc.app;

import io.storydoc.server.storydoc.app.dto.ArtifactDTO;
import io.storydoc.server.storydoc.app.dto.BlockDTO;
import io.storydoc.server.storydoc.app.dto.StoryDocDTO;
import io.storydoc.server.storydoc.domain.*;
import io.storydoc.server.storydoc.infra.store.model.*;
import io.storydoc.server.storydoc.infra.store.model.Artifact;
import io.storydoc.server.storydoc.infra.store.model.StoryDoc;
import io.storydoc.server.workspace.domain.FolderURN;
import org.springframework.stereotype.Service;

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
    public StoryDocDTO getDocument(StoryDocId storyDocId) {

        StoryDoc storyDoc = storyDocStorage.loadDocument(storyDocId);

        List<BlockDTO> blockDTOList = new ArrayList<>();
        addBlocks(storyDoc.getBlocks(), blockDTOList, null, new int[]{});


        return StoryDocDTO.builder()
                .storyDocId(storyDocId)
                .urn(storyDocStorage.getStoryDocUrn(storyDocId))
                .blocks(blockDTOList)
                .title(storyDoc.getName())
                .build();

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
                .urn(artifact.getUrn())
                .build();
    }

    private int[] combinedNumbering(int[] numbering, int nr) {
        int[] result = Arrays.copyOf(numbering, numbering.length + 1); //create new array from old array and allocate one more element
        result[result.length - 1] = nr;
        return result;
    }

    @Override
    public FolderURN getArtifactBlockFolder(StoryDocId storyDocId, BlockId blockId) {
        return storyDocStorage.getArtifactBlockFolder(storyDocId, blockId);
    }

    @Override
    public List<ArtifactId> getArtifactsByType(ArtifactBlockCoordinate coordinate, String artifactType) {
        return storyDocStorage.getArtifacts(coordinate, (metaData)-> metaData.getType().equals(artifactType) );
    }

    @Override
    public ArtifactMetaData getArtifactMetaData(ArtifactBlockCoordinate coordinate, ArtifactId artifactId) {
        return storyDocStorage.getArtifactMetaData(coordinate, artifactId);
    }
}
