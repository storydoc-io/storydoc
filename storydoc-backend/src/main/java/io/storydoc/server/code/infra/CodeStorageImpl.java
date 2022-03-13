package io.storydoc.server.code.infra;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.storydoc.server.code.domain.CodeExecutionCoordinate;
import io.storydoc.server.code.domain.CodeStorage;
import io.storydoc.server.code.infra.model.CodeExecution;
import io.storydoc.server.storydoc.app.StoryDocService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

@Component
public class CodeStorageImpl implements CodeStorage {

    private ObjectMapper objectMapper;

    private final StoryDocService storyDocService;

    public CodeStorageImpl(StoryDocService storyDocService) {
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
    public void createCodeExecution(CodeExecutionCoordinate coordinate, String name) {
        CodeExecution codeExecution = new CodeExecution();
        codeExecution.setId(coordinate.getCodeExecutionId());
        storyDocService.addArtifact(coordinate.getBlockCoordinate(), coordinate.getCodeExecutionId().asArtifactId(),
                io.storydoc.server.code.domain.CodeExecution.ARTIFACT_TYPE, name) ;

        save(coordinate, codeExecution);
    }

    private void save(CodeExecutionCoordinate coordinate, CodeExecution codeExecution) {
        storyDocService.saveArtifact(coordinate.asArtifactCoordinate(), (OutputStream os) -> { write(codeExecution, os);});
    }

    private void write(CodeExecution codeExecution, OutputStream outputStream) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputStream, codeExecution);
    }


}
