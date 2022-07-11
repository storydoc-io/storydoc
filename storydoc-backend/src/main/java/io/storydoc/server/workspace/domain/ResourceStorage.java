package io.storydoc.server.workspace.domain;

import io.storydoc.server.workspace.app.dto.ResourceDTO;

import java.io.InputStream;
import java.util.List;

public interface ResourceStorage {

    void saveResource(ResourceUrn resourceUrn, WorkspaceResourceSerializer serializer) throws WorkspaceException;

    <R extends WorkspaceResource> R loadResource(ResourceUrn resourceUrn, WorkspaceResourceDeserializer<R> deserializer) throws WorkspaceException;

    List<ResourceDTO> listResources(FolderURN folderURN);

    InputStream getInputStream(ResourceUrn urn) throws WorkspaceException;

    void delete(ResourceUrn resourceUrn) throws WorkspaceException;

}
