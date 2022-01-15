package io.storydoc.server.workspace.domain;

import java.io.IOException;
import java.io.OutputStream;

public interface ResourceSaveContext {

    ResourceUrn getResourceUrn();

    void write(OutputStream outputStream) throws IOException;
}
