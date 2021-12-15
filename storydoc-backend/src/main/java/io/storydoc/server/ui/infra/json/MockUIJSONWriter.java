package io.storydoc.server.ui.infra.json;

import io.storydoc.server.document.domain.DocumentException;
import io.storydoc.server.document.infra.store.JacksonConfig;

import java.io.IOException;
import java.io.OutputStream;

public class MockUIJSONWriter {

    private JacksonConfig jacksonConfig;

    public MockUIJSONWriter(JacksonConfig jacksonConfig) {
        this.jacksonConfig = jacksonConfig;
    }

    public void write(MockUI mockUI, OutputStream outputStream) throws DocumentException {
        try {
            jacksonConfig.getObjectMapper().writerWithDefaultPrettyPrinter().writeValue(outputStream, mockUI);
        } catch (IOException e) {
            throw new DocumentException("error while writing mock ui", e);
        }
    }

}
