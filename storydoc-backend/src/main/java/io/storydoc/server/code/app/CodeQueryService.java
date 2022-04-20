package io.storydoc.server.code.app;

import io.storydoc.server.code.app.stitch.CodeTraceDTO;
import io.storydoc.server.code.app.stitch.StitchItemDTO;
import io.storydoc.server.code.domain.CodeExecutionCoordinate;
import io.storydoc.server.code.domain.CodeStorage;
import io.storydoc.server.code.domain.SourceCodeConfigCoordinate;
import io.storydoc.server.code.domain.SourceCodeConfigStorage;
import io.storydoc.server.code.infra.CodeStorageImpl;
import io.storydoc.server.code.infra.model.CodeExecution;
import io.storydoc.server.code.infra.model.SourceCodeConfig;
import io.storydoc.server.code.infra.stitch.StitchFileScannerNew;
import io.storydoc.server.code.infra.stitch.StitchLine;
import io.storydoc.server.storydoc.app.StoryDocQueryService;
import io.storydoc.server.storydoc.app.dto.AssociationDto;
import io.storydoc.server.storydoc.app.dto.StoryDocSummaryDTO;
import io.storydoc.server.storydoc.domain.ArtifactCoordinate;
import io.storydoc.server.storydoc.domain.ArtifactMetaData;
import io.storydoc.server.storydoc.domain.StoryDocId;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CodeQueryService {

    private final SourceCodeConfigStorage sourceCodeConfigStorage;

    private final CodeStorage codeStorage;

    private final StoryDocQueryService storyDocQueryService;

    public CodeQueryService(SourceCodeConfigStorage sourceCodeConfigStorage, CodeStorage codeStorage, StoryDocQueryService storyDocQueryService) {
        this.sourceCodeConfigStorage = sourceCodeConfigStorage;
        this.codeStorage = codeStorage;
        this.storyDocQueryService = storyDocQueryService;
    }

    public CodeTraceDTO getExecution(CodeExecutionCoordinate coordinate) {
        CodeExecution codeExecution = codeStorage.load(coordinate);

        ArtifactMetaData metaData = storyDocQueryService.getArtifactMetaData(coordinate.getBlockCoordinate(), coordinate.getCodeExecutionId().asArtifactId());

        List<StitchLine> stitchLines = getStitchLineList(codeExecution);

        return CodeTraceDTO.builder()
                .name(metaData.getName())
                .storyDocSummary(getStoryDocSummary(coordinate.getBlockCoordinate().getStoryDocId()))
                .config(getAssociatedConfig(coordinate))
                .items(stitchLines.stream()
                    .map(stitchLine -> toDto(stitchLine))
                    .collect(Collectors.toList())
                )
                .build();
    }

    private List<StitchLine> getStitchLineList(CodeExecution codeExecution)  {
        try {
            InputStream inputstream = new FileInputStream(codeExecution.getStitchFile());
            StitchFileScannerNew logFileScanner = new StitchFileScannerNew(inputstream, codeExecution.getLineFrom(), codeExecution.getLineTo());
            List<StitchLine> stitchLines = logFileScanner.run();
            return stitchLines;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private StitchItemDTO toDto(StitchLine stitchLine) {
        return StitchItemDTO.builder()
                .modelName(stitchLine.getModelName())
                .eventName(stitchLine.getEventName())
                .attributes(stitchLine.getAttributes())
                .children(stitchLine.getChildren().stream()
                        .map(this::toDto)
                        .collect(Collectors.toList()))
                .build();
    }

    private SourceCodeConfigCoordinate getAssociatedConfig(CodeExecutionCoordinate coordinate) {
        List<AssociationDto> associatedConfigs = storyDocQueryService.getAssociationsFrom(coordinate.asArtifactCoordinate(), CodeStorageImpl.ASSOCIATED_SOURCE_CODE_CONFIG);
        if (associatedConfigs.size()==0) {
            return null;
        }
        ArtifactCoordinate associatedCoord = associatedConfigs.get(0).getTo();
        return SourceCodeConfigCoordinate.of(associatedCoord);
    }


    public SourceCodeConfigDTO getSourceCodeConfig(SourceCodeConfigCoordinate coordinate) {
        SourceCodeConfig sourceCodeConfig = sourceCodeConfigStorage.load(coordinate);
        ArtifactMetaData metaData = storyDocQueryService.getArtifactMetaData(coordinate.getBlockCoordinate(), coordinate.getSourceCodeConfigId().asArtifactId());

        return SourceCodeConfigDTO.builder()
                .id(sourceCodeConfig.getId())
                .name(metaData.getName())
                .storyDocSummary(getStoryDocSummary(coordinate.getBlockCoordinate().getStoryDocId()))
                .dirs(sourceCodeConfig.getDirs())
                .build();
    }

    private StoryDocSummaryDTO getStoryDocSummary(StoryDocId storyDocId) {
        return storyDocQueryService.getStoryDocSummary(storyDocId);
    }

}
