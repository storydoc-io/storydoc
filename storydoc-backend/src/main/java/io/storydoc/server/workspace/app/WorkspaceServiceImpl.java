package io.storydoc.server.workspace.app;

import io.storydoc.server.workspace.domain.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

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
    public void deleteFolder(FolderURN folderURN, boolean recursive) throws WorkspaceException {
        try {
            folderStore.deleteFolder(folderURN, recursive);
        } catch (IOException ioe) {
            throw new WorkspaceException("could not delete folder " + folderURN, ioe);
        }
    }

    @Override
    public void deleteResource(ResourceUrn resourceUrn) throws WorkspaceException {
        resourceStore.delete(resourceUrn);
    }

    @Override
    public void saveResource(ResourceUrn resourceUrn, WorkspaceResourceSerializer serializer) throws WorkspaceException {
        resourceStore.saveResource(resourceUrn, serializer);
    }

    @Override
    public <R extends WorkspaceResource> R loadResource(ResourceUrn resourceUrn, WorkspaceResourceDeserializer<R> deserializer) throws WorkspaceException {
        return resourceStore.loadResource(resourceUrn, deserializer);
    }
}
