package io.storydoc.server.folder.app;

import io.storydoc.server.folder.app.dto.FolderDTO;
import io.storydoc.server.folder.domain.FolderURN;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/folder")
public class FolderRestController {

    private final FolderService folderService;

    private final FolderQueryService folderQueryService;

    private final FolderSettings folderSettings;

    public FolderRestController(FolderService folderService, FolderQueryService folderQueryService, FolderSettings folderSettings) {
        this.folderService = folderService;
        this.folderQueryService = folderQueryService;
        this.folderSettings = folderSettings;
    }

    @GetMapping(value = "/settings", produces = MediaType.APPLICATION_JSON_VALUE)
    public FolderSettings getSettings() {
        return folderSettings;
    }

    @GetMapping(value = "/root", produces = MediaType.APPLICATION_JSON_VALUE)
    public FolderURN getRootFolder() {
        return folderQueryService.getRootFolder().getUrn();
    }

    @GetMapping(value = "/folders", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FolderDTO> getFolders(FolderURN folderURN) {
        if (folderURN.getPath()==null) {
            folderURN.setPath(new ArrayList<>());
        }
        return folderQueryService.listSubFolders(folderURN);
    }

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public FolderURN addFolder(FolderURN parentFolderUrn, String folderName) {
        return folderService.addFolder(parentFolderUrn, folderName);
    }

}
