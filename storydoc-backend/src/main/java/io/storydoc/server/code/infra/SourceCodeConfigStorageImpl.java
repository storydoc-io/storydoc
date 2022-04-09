package io.storydoc.server.code.infra;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.storydoc.server.code.domain.SourceCodeConfigCoordinate;
import io.storydoc.server.code.domain.SourceCodeConfigStorage;
import io.storydoc.server.code.infra.model.SourceCodeConfig;
import io.storydoc.server.storydoc.app.StoryDocService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

@Component
public class SourceCodeConfigStorageImpl implements SourceCodeConfigStorage {

    private ObjectMapper objectMapper;

    private final StoryDocService storyDocService;

    public SourceCodeConfigStorageImpl(StoryDocService storyDocService) {
        this.storyDocService = storyDocService;
        initObjectMapper();
    }

    private void initObjectMapper() {
        objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @Override
    public void createSourceCodeConfig(SourceCodeConfigCoordinate coordinate, String name) {
        SourceCodeConfig sourceCodeConfig = new SourceCodeConfig();
        sourceCodeConfig.setId(coordinate.getSourceCodeConfigId());
        sourceCodeConfig.setDirs(new ArrayList<>());
        save(coordinate, sourceCodeConfig);

        storyDocService.addArtifact(coordinate.getBlockCoordinate(), coordinate.getSourceCodeConfigId().asArtifactId(),
                io.storydoc.server.code.domain.SourceCodeConfig.ARTIFACT_TYPE, name) ;

    }

    @Override
    public void setSourcePath(SourceCodeConfigCoordinate coordinate, String path) {
        SourceCodeConfig sourceCodeConfig = load(coordinate);
        sourceCodeConfig.getDirs().add(path);
        save(coordinate, sourceCodeConfig);
    }

    private void save(SourceCodeConfigCoordinate coordinate, SourceCodeConfig sourceCodeConfig) {
        storyDocService.saveArtifact(coordinate.asArtifactCoordinate(), (OutputStream os) -> { write(sourceCodeConfig, os);});
    }

    private void write(SourceCodeConfig sourceCodeConfig, OutputStream outputStream) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputStream, sourceCodeConfig);
    }

    @Override
    public SourceCodeConfig load(SourceCodeConfigCoordinate coordinate) {
        return storyDocService.loadArtifact(coordinate.asArtifactCoordinate(),this::read);
    }

    private SourceCodeConfig read(InputStream inputStream) throws IOException {
        return objectMapper.readValue(inputStream, SourceCodeConfig.class);
    }
}
