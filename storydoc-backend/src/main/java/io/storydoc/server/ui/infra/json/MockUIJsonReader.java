package io.storydoc.server.ui.infra.json;

import io.storydoc.server.document.domain.DocumentException;
import io.storydoc.server.document.infra.store.JacksonConfig;
import io.storydoc.server.document.infra.store.model.StoryDoc;

import java.io.IOException;
import java.io.InputStream;

public class MockUIJsonReader {

    private JacksonConfig jacksonConfig;

    public MockUIJsonReader(JacksonConfig jacksonConfig) {
        this.jacksonConfig = jacksonConfig;
    }

    public MockUI read(InputStream inputStream) throws DocumentException {
        try {
            return jacksonConfig.getObjectMapper().readValue(inputStream, MockUI.class);
        } catch (IOException e) {
            throw new DocumentException("error when reading mock ui", e);
        }
    }

}
