package io.storydoc.server.storydoc.domain;

import java.io.IOException;
import java.io.InputStream;

@FunctionalInterface
public interface ArtifactDeserializer<A extends Artifact> {

    A read(InputStream inputStream) throws IOException;

}
