package io.storydoc.server.workspace.domain;

import io.storydoc.server.workspace.app.dto.FolderDTO;

import java.util.List;

public interface FolderStorage {
    void createFolder(FolderURN folderURN);
    FolderURN addFolder(FolderURN folderURN, String folderName);
    List<FolderDTO> listSubFolders(FolderURN folderURN);
}
