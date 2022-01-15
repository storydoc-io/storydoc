package io.storydoc.server.workspace.app;

import io.storydoc.server.workspace.app.dto.FolderDTO;
import io.storydoc.server.workspace.app.dto.ResourceDTO;
import io.storydoc.server.workspace.domain.FolderURN;
import io.storydoc.server.workspace.domain.ResourceUrn;
import io.storydoc.server.workspace.domain.WorkspaceException;

import java.io.InputStream;
import java.util.List;

public interface WorkspaceQueryService {
    FolderDTO getRootFolder();
    List<FolderDTO> listSubFolders(FolderURN folderURN);
    List<ResourceDTO> listResources(FolderURN folderURN);
    InputStream getInputStream(ResourceUrn urn) throws WorkspaceException;
}
