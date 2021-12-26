package io.storydoc.server.folder.app;

import io.storydoc.server.folder.domain.FolderStore;
import io.storydoc.server.folder.domain.FolderURN;
import org.springframework.stereotype.Service;

@Service
public class FolderServiceImpl implements FolderService  {

    private final FolderStore folderStore;

    public FolderServiceImpl(FolderStore folderStore) {
        this.folderStore = folderStore;
    }

    @Override
    public FolderURN addFolder(FolderURN folderURN, String folderName) {
        return folderStore.addFolder(folderURN, folderName);

    }

}
