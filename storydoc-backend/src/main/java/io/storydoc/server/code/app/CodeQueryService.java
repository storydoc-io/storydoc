package io.storydoc.server.code.app;

import io.storydoc.server.code.app.stitch.CodeTraceDTO;
import io.storydoc.server.code.app.stitch.StitchItemDTO;
import io.storydoc.server.code.app.stitch.StitchStructureDTO;
import io.storydoc.server.code.domain.*;
import io.storydoc.server.code.infra.model.CodeExecution;
import io.storydoc.server.code.infra.model.SourceCodeConfig;
import io.storydoc.server.code.infra.stitch.StitchFileScannerNew;
import io.storydoc.server.code.infra.stitch.StitchLine;
import io.storydoc.server.code.infra.stitch.StitchStructureReader;
import io.storydoc.server.storydoc.app.StoryDocQueryService;
import io.storydoc.server.storydoc.app.dto.SettingsEntryDTO;
import io.storydoc.server.storydoc.app.dto.StoryDocSummaryDTO;
import io.storydoc.server.storydoc.domain.ArtifactCoordinate;
import io.storydoc.server.storydoc.domain.ArtifactMetaData;
import io.storydoc.server.storydoc.domain.BlockCoordinate;
import io.storydoc.server.storydoc.domain.StoryDocId;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CodeQueryService {

    private final SourceCodeConfigStorage sourceCodeConfigStorage;

    private final StitchConfigStorage stitchConfigStorage;

    private final CodeStorage codeStorage;

    private final StoryDocQueryService storyDocQueryService;

    public CodeQueryService(SourceCodeConfigStorage sourceCodeConfigStorage, StitchConfigStorage stitchConfigStorage, CodeStorage codeStorage, StoryDocQueryService storyDocQueryService) {
        this.sourceCodeConfigStorage = sourceCodeConfigStorage;
        this.stitchConfigStorage = stitchConfigStorage;
        this.codeStorage = codeStorage;
        this.storyDocQueryService = storyDocQueryService;
    }

    public CodeTraceDTO getExecution(CodeExecutionCoordinate coordinate) {
        CodeExecution codeExecution = codeStorage.load(coordinate);

        BlockCoordinate blockCoordinate = coordinate.getBlockCoordinate();

        ArtifactMetaData metaData = storyDocQueryService.getArtifactMetaData(blockCoordinate, coordinate.getCodeExecutionId().asArtifactId());

        List<StitchLine> stitchLines = getStitchLineList(codeExecution);

        return CodeTraceDTO.builder()
                .name(metaData.getName())
                .storyDocSummary(getStoryDocSummary(coordinate.getBlockCoordinate().getStoryDocId()))
                .config(getAssociatedConfig(coordinate))
                .stitchConfigCoordinate(getAssociatedStitchConfig(coordinate))
                .items(stitchLines.stream()
                    .map(stitchLine -> toDto(stitchLine))
                    .collect(Collectors.toList())
                )
                .build();
    }

    private List<StitchLine> getStitchLineList(CodeExecution codeExecution)  {
        try {
            InputStream inputstream = new FileInputStream(codeExecution.getStitchFile());
            String lineFrom = String.format("TestScenario|TestEntered|{ \\\"testCaseName\\\": \\\"%s %s(%s)\\\"", codeExecution.getTestClass(), codeExecution.getTestMethod(), codeExecution.getTestClass());
            String lineTo = String.format("TestScenario|TestFinished|{ \\\"testCaseName\\\": \\\"%s %s(%s)\\\"", codeExecution.getTestClass(), codeExecution.getTestMethod(), codeExecution.getTestClass());
            StitchFileScannerNew logFileScanner = new StitchFileScannerNew(inputstream, lineFrom, lineTo);
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

    private SourceCodeConfigCoordinate getAssociatedConfig(CodeExecutionCoordinate codeExecutionCoordinate) {
        ArtifactCoordinate artifactCoordinate = storyDocQueryService.getDefaultArtifact(codeExecutionCoordinate.getBlockCoordinate(), io.storydoc.server.code.domain.SourceCodeConfig.ARTIFACT_TYPE);
        return artifactCoordinate != null ? SourceCodeConfigCoordinate.of(artifactCoordinate) : null;
    }

    private StitchConfigCoordinate getAssociatedStitchConfig(CodeExecutionCoordinate codeExecutionCoordinate) {
        ArtifactCoordinate artifactCoordinate = storyDocQueryService.getDefaultArtifact(codeExecutionCoordinate.getBlockCoordinate(), io.storydoc.server.code.domain.StitchConfig.ARTIFACT_TYPE);
        return artifactCoordinate != null ? StitchConfigCoordinate.of(artifactCoordinate) : null;
    }

    public StitchConfigDTO getStitchConfig(StitchConfigCoordinate coordinate) {
        io.storydoc.server.code.infra.model.StitchConfig sourceCodeConfig = stitchConfigStorage.load(coordinate);
        ArtifactMetaData metaData = storyDocQueryService.getArtifactMetaData(coordinate.getBlockCoordinate(), coordinate.getStitchConfigId().asArtifactId());

        return StitchConfigDTO.builder()
                .id(sourceCodeConfig.getId())
                .name(metaData.getName())
                .storyDocSummary(getStoryDocSummary(coordinate.getBlockCoordinate().getStoryDocId()))
                .dir(sourceCodeConfig.getDir())
                .build();
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

    public SettingsEntryDTO getStitchSettings() {
        return storyDocQueryService.getGlobalSetting(CodeService.SETTINGS_NAMESPACE, CodeService.SETTINGS_KEY__STITCH_DIR);
    }

    public StitchStructureDTO getStitchStructure(StitchConfigCoordinate stitchConfigCoordinate) {
        Path root = Paths.get(getStitchConfig(stitchConfigCoordinate).getDir());
        StitchStructureReader structureReader = new StitchStructureReader();
        return structureReader.readStructure(root);
    }


}
