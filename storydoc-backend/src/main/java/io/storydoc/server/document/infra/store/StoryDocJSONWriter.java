package io.storydoc.server.document.infra.store;

import io.storydoc.server.document.infra.store.model.StoryDoc;
import io.storydoc.server.document.domain.DocumentException;

import java.io.IOException;
import java.io.OutputStream;

public class StoryDocJSONWriter {

    private JacksonConfig jacksonConfig;

    public StoryDocJSONWriter(JacksonConfig jacksonConfig) {
        this.jacksonConfig = jacksonConfig;
    }

    public void write(StoryDoc storyDoc, OutputStream outputStream) throws DocumentException {
        try {
            jacksonConfig.getObjectMapper().writerWithDefaultPrettyPrinter().writeValue(outputStream, storyDoc);
        } catch (IOException e) {
            throw new DocumentException("error while writing storydoc", e);
        }
    }

}
