package io.storydoc.server.document.infra.store;

import io.storydoc.server.document.infra.store.model.StoryDoc;
import io.storydoc.server.document.domain.DocumentException;

import java.io.IOException;
import java.io.InputStream;

public class StoryDocJsonReader {

    private JacksonConfig jacksonConfig;

    public StoryDocJsonReader(JacksonConfig jacksonConfig) {
        this.jacksonConfig = jacksonConfig;
    }

    public StoryDoc read(InputStream inputStream) throws DocumentException {
        try {
            return jacksonConfig.getObjectMapper().readValue(inputStream, StoryDoc.class);
        } catch (IOException e) {
            throw new DocumentException("error when reading storydoc", e);
        }
    }

}
