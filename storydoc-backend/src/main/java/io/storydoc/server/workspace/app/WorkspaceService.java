package io.storydoc.server.workspace.app;

import io.storydoc.server.workspace.domain.*;

public interface WorkspaceService {
    void createFolder(FolderURN folderURN);
    FolderURN addFolder(FolderURN folderURN, String folderName);
    void saveResource(ResourceUrn resourceUrn, WorkspaceResourceSerializer serializer) throws WorkspaceException;
    void deleteResource(ResourceUrn resourceUrn) throws WorkspaceException;
    <R extends WorkspaceResource> R loadResource(ResourceUrn resourceUrn, WorkspaceResourceDeserializer<R> deserializer) throws WorkspaceException;

    void deleteFolder(FolderURN storyDocFolder, boolean recursive) throws WorkspaceException;

}
