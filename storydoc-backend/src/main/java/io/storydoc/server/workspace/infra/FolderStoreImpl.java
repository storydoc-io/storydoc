package io.storydoc.server.workspace.infra;

import io.storydoc.server.workspace.app.WorkspaceSettings;
import io.storydoc.server.workspace.app.dto.FolderDTO;
import io.storydoc.server.workspace.domain.FolderStorage;
import io.storydoc.server.workspace.domain.FolderURN;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class FolderStoreImpl implements FolderStorage {

    private WorkspaceSettings folderSettings;

    public FolderStoreImpl(WorkspaceSettings folderSettings) {
        this.folderSettings = folderSettings;
    }

    @Override
    public void createFolder(FolderURN folderURN) {
        Path folder = resolve(folderURN);
        File file = folder.toFile();
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    @Override
    public FolderURN addFolder(FolderURN folderURN, String folderName) {
        Path parentFolder = resolve(folderURN);
        Path newFolder = parentFolder.resolve(folderName);
        File file = newFolder.toFile();
        if (!file.exists()) {
            file.mkdirs();
        }
        return subUrn(folderURN, folderName);
    }

    @Override
    public List<FolderDTO> listSubFolders(FolderURN folderURN) {
        Path parentFolder = resolve(folderURN);
        File file = parentFolder.toFile();

        FilenameFilter onlyDirectories = new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        };
        String[] subDirNames = file.list(onlyDirectories);
        if (subDirNames==null) {
            return new ArrayList<>();
        }
        return Arrays.stream(subDirNames)
                .sorted()
                .map(name -> {
                    FolderDTO folderDTO = new FolderDTO();
                    folderDTO.setName(name);
                    folderDTO.setUrn(subUrn(folderURN, name));
                    return folderDTO;
                })
                .collect(Collectors.toList());
    }

    private FolderURN subUrn(FolderURN parentURN, String name) {
        List<String> path = new ArrayList<>(parentURN.getPath());
        path.add(name);
        FolderURN result = new FolderURN(path);
        return result;
    }

    private Path resolve(FolderURN folderURN) {
        Path path = folderSettings.getWorkspaceFolder();
        for (String subDirName: folderURN.getPath()) {
            path= path.resolve(subDirName);
        }
        return path;
    }

}
