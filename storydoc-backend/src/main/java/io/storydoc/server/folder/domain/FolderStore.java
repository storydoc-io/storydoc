package io.storydoc.server.folder.domain;

import io.storydoc.server.folder.app.dto.FolderDTO;

import java.util.List;

public interface FolderStore {
    FolderURN addFolder(FolderURN folderURN, String folderName);
    List<FolderDTO> listSubFolders(FolderURN folderURN);
}
