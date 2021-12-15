package io.storydoc.server.folder.app;

import io.storydoc.server.folder.domain.FolderStore;
import io.storydoc.server.folder.infra.FileSystemFolderStore;

import java.nio.file.Path;

public class FolderServiceTestFixture {

    private final FolderStore folderStore;

    private final FolderService folderService;

    private final FolderQueryService folderQueryService;

    public FolderServiceTestFixture(Path rootFolder) {
        this.folderStore = new FileSystemFolderStore(rootFolder);
        this.folderService = new FolderServiceImpl(folderStore);
        this.folderQueryService = new FolderQueryServiceImpl(folderStore);
    }

    public FolderQueryService getFolderQueryService() {
        return folderQueryService;
    }

    public FolderService getFolderService() {
        return folderService;
    }

}
