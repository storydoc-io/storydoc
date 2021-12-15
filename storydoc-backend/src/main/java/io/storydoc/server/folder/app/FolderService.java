package io.storydoc.server.folder.app;

import io.storydoc.server.folder.domain.FolderURN;

public interface FolderService {
    FolderURN addFolder(FolderURN rootFolderURN, String folderName);
}
