package io.storydoc.server.workspace.domain;

import io.storydoc.server.workspace.app.dto.ResourceDTO;

import java.io.InputStream;
import java.util.List;

public interface ResourceStorage {

    void store(ResourceSaveContext context) throws WorkspaceException ;

    <R extends WorkspaceResource> R load(ResourceLoadContext context) throws WorkspaceException;

    List<ResourceDTO> listResources(FolderURN folderURN);

    InputStream getInputStream(ResourceUrn urn) throws WorkspaceException;
}
