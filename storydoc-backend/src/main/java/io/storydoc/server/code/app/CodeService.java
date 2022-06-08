package io.storydoc.server.code.app;

import io.storydoc.blueprint.BluePrint;
import io.storydoc.blueprint.Classifier;
import io.storydoc.server.code.domain.*;
import io.storydoc.server.infra.IDGenerator;
import io.storydoc.server.infra.StoryDocBluePrintFactory;
import io.storydoc.server.storydoc.app.StoryDocService;
import io.storydoc.server.storydoc.app.dto.SettingsEntryDTO;
import io.storydoc.server.storydoc.domain.BlockCoordinate;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

@Service
public class CodeService {

    public static final String SETTINGS_NAMESPACE = "io.storydoc";
    public static final String SETTINGS_KEY__STITCH_DIR = "stitch.dir";

    private final IDGenerator idGenerator;

    private final CodeDomainService domainService;

    private final StoryDocService storyDocService;

    private final SourceCodeAccess sourceCodeAccess;

    private final SourceCodeConfigStorage sourceCodeConfigStorage;

    public CodeService(IDGenerator idGenerator, CodeDomainService codeDomainService, StoryDocService storyDocService, SourceCodeAccess sourceCodeAccess, SourceCodeConfigStorage sourceCodeConfigStorage) {
        this.idGenerator = idGenerator;
        this.domainService = codeDomainService;
        this.storyDocService = storyDocService;
        this.sourceCodeAccess = sourceCodeAccess;
        this.sourceCodeConfigStorage = sourceCodeConfigStorage;
    }

    public CodeExecutionCoordinate createCodeExecution(BlockCoordinate blockCoordinate, String name) {
        CodeExecutionId codeExecutionId = new CodeExecutionId(idGenerator.generateID(CodeExecutionId.ID_PREFIX));
        CodeExecutionCoordinate coordinate = CodeExecutionCoordinate.of(blockCoordinate, codeExecutionId);
        domainService.createCodeExecution(coordinate, name);
        return coordinate;
    }

    public void setStitchDetails(CodeExecutionCoordinate coordinate, String stitchFile, String testClass, String testMethod) {
        CodeExecution codeExecution = domainService.getCodeExecution(coordinate);
        codeExecution.setStitchDetails(coordinate, stitchFile, testClass, testMethod);
    }

    public SourceCodeDTO getSource(String className, SourceCodeConfigCoordinate configCoord) {
        io.storydoc.server.code.infra.model.SourceCodeConfig sourceCodeConfig  = sourceCodeConfigStorage.load(configCoord);
        return SourceCodeDTO.builder()
                .lines(sourceCodeAccess.getSource(className, sourceCodeConfig.getDirs()))
                .build();
    }

    public SourceCodeConfigCoordinate createSourceCodeConfig(BlockCoordinate blockCoordinate, String name) {
        SourceCodeConfigId codeExecutionId = new SourceCodeConfigId(idGenerator.generateID(SourceCodeConfigId.ID_PREFIX));
        SourceCodeConfigCoordinate coordinate = SourceCodeConfigCoordinate.of(blockCoordinate, codeExecutionId);
        domainService.createSourceCodeConfig(coordinate, name);
        return coordinate;
    }

    public void setSourcePath(SourceCodeConfigCoordinate coordinate, String path) {
        SourceCodeConfig sourceCodeConfig = domainService.getSourceCodeConfig(coordinate);
        sourceCodeConfig.setSourcePath(coordinate, path);
    }

    public void setSourceConfigForExecution(CodeExecutionCoordinate codeExecutionCoordinate, SourceCodeConfigCoordinate sourceCodeConfigCoordinate) {
        CodeExecution codeExecution = domainService.getCodeExecution(codeExecutionCoordinate);
        codeExecution.setSourceConfig(codeExecutionCoordinate, sourceCodeConfigCoordinate);
    }

    private BluePrint bluePrint;

    public BluePrint getBluePrint() {
        if (bluePrint==null) {
            StoryDocBluePrintFactory factory = new StoryDocBluePrintFactory();
            bluePrint = factory.createBluePrint();
        }
        return bluePrint;
    }

    private Classifier classifier;

    @SneakyThrows
    public List<String> classify(String className) {
        if (classifier==null) {
            classifier = new Classifier(getBluePrint());
        }
        return classifier.classify(Class.forName(className));
    }

    public Map<String, List<String>> classifyMultiple(String[] classNames) {
        return Arrays.stream(classNames)
            .collect(Collectors.toMap(identity(), this::classify));
    }

    public void setStitchSettings(String stitchDir) {
        storyDocService.setGlobalSettings(List.of(new SettingsEntryDTO(SETTINGS_NAMESPACE, SETTINGS_KEY__STITCH_DIR, stitchDir)));
    }

}
