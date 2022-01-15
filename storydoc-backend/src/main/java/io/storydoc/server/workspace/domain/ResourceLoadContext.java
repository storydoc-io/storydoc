package io.storydoc.server.workspace.domain;

import java.io.IOException;
import java.io.InputStream;

public interface ResourceLoadContext<R extends WorkspaceResource > {
    ResourceUrn getResourceUrn();
    R read(InputStream inputStream) throws IOException;
}
