package io.storydoc.server.workspace.app;

import io.storydoc.server.workspace.app.dto.FolderDTO;
import io.storydoc.server.workspace.app.dto.ResourceDTO;
import io.storydoc.server.workspace.domain.*;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class WorkspaceQueryServiceImpl implements WorkspaceQueryService {

    private final FolderStorage folderStorage;

    private final ResourceStorage resourceStorage;

    public WorkspaceQueryServiceImpl(FolderStorage folderStorage, ResourceStorage resourceStorage) {
        this.folderStorage = folderStorage;
        this.resourceStorage = resourceStorage;
    }

    @Override
    public FolderDTO getRootFolder() {
        FolderDTO dto = new FolderDTO();
        List<String> path = new ArrayList<>();
        dto.setUrn(new FolderURN(path));
        return dto;
    }

    @Override
    public List<FolderDTO> listSubFolders(FolderURN folderURN) {
        return folderStorage.listSubFolders(folderURN);
    }

    @Override
    public InputStream getInputStream(ResourceUrn urn) throws WorkspaceException {
        return resourceStorage.getInputStream(urn);
    }

    @Override
    public List<ResourceDTO> listResources(FolderURN folderURN) {
        return resourceStorage.listResources(folderURN);
    }
}
