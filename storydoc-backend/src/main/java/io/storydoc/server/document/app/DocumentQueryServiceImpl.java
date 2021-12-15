package io.storydoc.server.document.app;

import io.storydoc.server.document.app.dto.ArtifactDTO;
import io.storydoc.server.document.app.dto.BlockDTO;
import io.storydoc.server.document.app.dto.StoryDocDTO;
import io.storydoc.server.document.domain.ArtifactId;
import io.storydoc.server.document.domain.BlockId;
import io.storydoc.server.document.domain.DocumentStore;
import io.storydoc.server.document.domain.StoryDocId;
import io.storydoc.server.document.infra.store.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentQueryServiceImpl implements  DocumentQueryService {

    private final DocumentStore documentStore;

    public DocumentQueryServiceImpl(DocumentStore documentStore) {
        this.documentStore = documentStore;
    }

    @Override
    public StoryDocDTO getDocument(StoryDocId storyDocId) {

        StoryDoc storyDoc = documentStore.loadDocument(storyDocId);

        List<BlockDTO> blockDTOList = new ArrayList<>();
        addBlocks(storyDoc.getBlocks(), blockDTOList, null, new int[]{});


        return StoryDocDTO.builder()
                .storyDocId(storyDocId)
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
                .build();
    }

    private int[] combinedNumbering(int[] numbering, int nr) {
        int[] result = Arrays.copyOf(numbering, numbering.length + 1); //create new array from old array and allocate one more element
        result[result.length - 1] = nr;
        return result;
    }


}
