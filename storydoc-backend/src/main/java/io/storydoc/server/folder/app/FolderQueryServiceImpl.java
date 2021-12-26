package io.storydoc.server.folder.app;

import io.storydoc.server.folder.app.dto.FolderDTO;
import io.storydoc.server.folder.domain.FolderStore;
import io.storydoc.server.folder.domain.FolderURN;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FolderQueryServiceImpl implements FolderQueryService{

    private final FolderStore folderStore;

    public FolderQueryServiceImpl(FolderStore folderStore) {
        this.folderStore = folderStore;
    }

    @Override
    public FolderDTO getRootFolder() {
        FolderDTO dto = new FolderDTO();
        List<String> path  = new ArrayList<>();
        dto.setUrn(new FolderURN(path));
        return dto;
    }


    @Override
    public List<FolderDTO> listSubFolders(FolderURN folderURN) {
        return folderStore.listSubFolders(folderURN);
    }

}
