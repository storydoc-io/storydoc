package io.storydoc.server.workspace.domain;

import java.io.IOException;
import java.io.InputStream;

@FunctionalInterface
public interface WorkspaceResourceDeserializer<A extends WorkspaceResource> {

    A read(InputStream inputStream) throws IOException;

}
