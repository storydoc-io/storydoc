package io.storydoc.server.storydoc.domain;

import java.io.IOException;
import java.io.OutputStream;

@FunctionalInterface
public interface ArtifactSerializer {

    void write(OutputStream outputStream) throws IOException;

}
