package io.storydoc.server.workspace.app;

import io.storydoc.server.workspace.domain.*;
import org.springframework.stereotype.Service;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    private final FolderStorage folderStore;

    private final ResourceStorage resourceStore;

    public WorkspaceServiceImpl(FolderStorage folderStore, ResourceStorage resourceStore) {
        this.folderStore = folderStore;
        this.resourceStore = resourceStore;
    }

    @Override
    public FolderURN addFolder(FolderURN folderURN, String folderName) {
        return folderStore.addFolder(folderURN, folderName);
    }

    @Override
    public void createFolder(FolderURN folderURN) {
        folderStore.createFolder(folderURN);
    }

    @Override
    public void saveResource(ResourceSaveContext context) throws WorkspaceException {
        resourceStore.store(context);
    }

    @Override
    public <R extends WorkspaceResource> R loadResource(ResourceLoadContext context) throws WorkspaceException {
        return resourceStore.load(context);
    }
}
