package io.storydoc.server.workspace.domain;

import java.io.IOException;
import java.io.OutputStream;

@FunctionalInterface
public interface WorkspaceResourceSerializer {

    void write(OutputStream outputStream) throws IOException;

}
