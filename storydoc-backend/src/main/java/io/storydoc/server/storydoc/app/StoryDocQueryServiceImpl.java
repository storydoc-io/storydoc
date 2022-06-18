package io.storydoc.server.storydoc.app;

import io.storydoc.server.storydoc.app.dto.*;
import io.storydoc.server.storydoc.domain.*;
import io.storydoc.server.storydoc.infra.store.model.Artifact;
import io.storydoc.server.storydoc.infra.store.model.Settings;
import io.storydoc.server.storydoc.infra.store.model.StoryDoc;
import io.storydoc.server.storydoc.infra.store.model.*;
import io.storydoc.server.workspace.domain.FolderURN;
import io.storydoc.server.workspace.domain.ResourceUrn;
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
        //StitchConfigSingleton.INSTANCE.getCodeExecutionTracer()
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
                .state(artifact.getState())
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
    public FolderURN getArtifactBlockFolder(BlockCoordinate blockCoordinate) {
        return storyDocStorage.getBlockFolder(blockCoordinate.getStoryDocId(), blockCoordinate.getBlockId());
    }

    @Override
    public ResourceUrn getArtifactUrn(ArtifactCoordinate artifactCoordinate) {
        return storyDocStorage.getArtifactUrn(artifactCoordinate);
    }

    @Override
    public List<ArtifactId> getArtifactsByType(BlockCoordinate coordinate, String artifactType) {
        return storyDocStorage.findArtifacts(coordinate, (metaData)-> metaData.getType().equals(artifactType) );
    }

    @Override
    @Deprecated // TODO
    public ArtifactMetaData getArtifactMetaData(BlockCoordinate coordinate, ArtifactId artifactId) {
        return storyDocStorage.getArtifactMetaData(coordinate, artifactId);
    }

    @Override
    public ArtifactMetaData getArtifactMetaData(ArtifactCoordinate coordinate) {
        return getArtifactMetaData(coordinate.getBlockCoordinate(), coordinate.getArtifactId());
    }

    @Override
    public ArtifactDTO getArtifact(BlockCoordinate coordinate, ArtifactId artifactId) {
        ArtifactBlock artifactBlock = (ArtifactBlock) storyDocStorage.getBlock(coordinate);
        Artifact artifact = artifactBlock.getArtifacts().stream()
            .filter(a -> a.getArtifactId().equals(artifactId.getId()))
            .findFirst()
            .get();
        return toArtifactDTO(artifact);

    }

    @Override
    public InputStream getBinaryFromCollection(ArtifactCoordinate coordinate, ItemId itemId) throws WorkspaceException {
        return storyDocStorage.getBinaryFromCollection(coordinate, itemId);
    }

    @Override
    public ResourceUrn getArtifactItemUrn(ArtifactCoordinate artifactCoordinate, ItemId itemId) {
        return storyDocStorage.getCollectionItemUrn(artifactCoordinate, itemId);
    }

    @Override
    public List<AssociationDto> getAssociationsFrom(ArtifactCoordinate artifactCoordinate, String name) {
        List<Association> associations = storyDocStorage.getAssociations(artifactCoordinate);
        return associations.stream()
                .filter(a -> a.getName().equals(name))
                .map(a -> toAssociationDto(a))
                .collect(Collectors.toList());
    }

    private AssociationDto toAssociationDto(Association association) {
        return AssociationDto.builder()
            .name(association.getName())
            .from(ArtifactCoordinate.of(
                BlockCoordinate.of(
                    StoryDocId.fromString(association.getDocumentIdFrom()),
                    BlockId.fromString(association.getBlockIdFrom())),
                ArtifactId.fromString(association.getArtifactIdFrom())))
             .to(ArtifactCoordinate.of(
                BlockCoordinate.of(
                    StoryDocId.fromString(association.getDocumentIdTo()),
                    BlockId.fromString(association.getBlockIdTo())),
                ArtifactId.fromString(association.getArtifactIdTo())))
            .build();
    }

    @Override
    public ArtifactCoordinate getDefaultArtifact(BlockCoordinate blockCoordinate, String type) {
        List<ArtifactId> artifactIds =  storyDocStorage.findArtifacts(blockCoordinate, (metaData)-> metaData.getType().equals(type));
        if (artifactIds.size()>0) {
            return ArtifactCoordinate.of(blockCoordinate, artifactIds.get(0));
        } else {
            return null;
        }
    }

    @Override
    public SettingsDTO getGlobalSettings() {
        return toSettingsDto(storyDocStorage.loadGlobalSettings());
    }

    @Override
    public ResourceUrn getGlobalSettingsUrn() {
        return storyDocStorage.getGlobalSettingsUrn();
    }

    @Override
    public SettingsEntryDTO getGlobalSetting(String namespace, String key) {
        return storyDocStorage.loadGlobalSettings().getEntries().stream()
                .filter(entry->entry.getNameSpace().equals(namespace) && entry.getKey().equals(key))
                .findFirst()
                .map(this::toEntryDto)
                .orElse(null);
    }

    private SettingsDTO toSettingsDto(Settings settings) {
        return SettingsDTO.builder()
            .entries(settings.getEntries().stream()
                .map(this::toEntryDto
                )
                .collect(Collectors.toList())
            )
            .build();
    }

    private SettingsEntryDTO toEntryDto(SettingsEntry entry) {
        return SettingsEntryDTO.builder()
                .nameSpace(entry.getNameSpace())
                .key(entry.getKey())
                .value(entry.getValue())
                .build();
    }
}
