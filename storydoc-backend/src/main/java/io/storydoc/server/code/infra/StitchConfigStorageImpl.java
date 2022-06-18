package io.storydoc.server.code.infra;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.storydoc.server.code.domain.StitchConfigCoordinate;
import io.storydoc.server.code.domain.StitchConfigStorage;
import io.storydoc.server.code.infra.model.StitchConfig;
import io.storydoc.server.storydoc.app.StoryDocService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Component
public class StitchConfigStorageImpl implements StitchConfigStorage {

    private ObjectMapper objectMapper;

    private final StoryDocService storyDocService;

    public StitchConfigStorageImpl(StoryDocService storyDocService) {
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
    public void createStitchConfig(StitchConfigCoordinate coordinate, String name) {
        StitchConfig sourceCodeConfig = new StitchConfig();
        sourceCodeConfig.setId(coordinate.getStitchConfigId());
        save(coordinate, sourceCodeConfig);

        storyDocService.addArtifact(coordinate.getBlockCoordinate(), coordinate.getStitchConfigId().asArtifactId(),
                io.storydoc.server.code.domain.StitchConfig.ARTIFACT_TYPE, name) ;

    }

    @Override
    public void setPath(StitchConfigCoordinate coordinate, String path) {
        StitchConfig sourceCodeConfig = load(coordinate);
        sourceCodeConfig.setDir(path);
        save(coordinate, sourceCodeConfig);
    }

    private void save(StitchConfigCoordinate coordinate, StitchConfig sourceCodeConfig) {
        storyDocService.saveArtifact(coordinate.asArtifactCoordinate(), (OutputStream os) -> { write(sourceCodeConfig, os);});
    }

    private void write(StitchConfig sourceCodeConfig, OutputStream outputStream) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputStream, sourceCodeConfig);
    }

    @Override
    public StitchConfig load(StitchConfigCoordinate coordinate) {
        return storyDocService.loadArtifact(coordinate.asArtifactCoordinate(),this::read);
    }

    private StitchConfig read(InputStream inputStream) throws IOException {
        return objectMapper.readValue(inputStream, StitchConfig.class);
    }
}
