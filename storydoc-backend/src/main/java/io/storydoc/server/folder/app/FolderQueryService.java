package io.storydoc.server.folder.app;

import io.storydoc.server.folder.app.dto.FolderDTO;
import io.storydoc.server.folder.domain.FolderURN;

import java.util.List;

public interface FolderQueryService {
    FolderDTO getRootFolder();
    List<FolderDTO> listSubFolders(FolderURN rootFolderURN);

}
