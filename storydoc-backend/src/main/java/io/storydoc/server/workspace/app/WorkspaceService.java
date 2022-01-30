package io.storydoc.server.workspace.app;

import io.storydoc.server.workspace.domain.*;

public interface WorkspaceService {
    void createFolder(FolderURN folderURN);
    FolderURN addFolder(FolderURN folderURN, String folderName);
    void saveResource(ResourceSaveContext context) throws WorkspaceException;
    void deleteResource(ResourceUrn resourceUrn) throws WorkspaceException;
    <R extends WorkspaceResource> R loadResource(ResourceLoadContext context) throws WorkspaceException;
    void deleteFolder(FolderURN storyDocFolder, boolean recursive) throws WorkspaceException;
}
