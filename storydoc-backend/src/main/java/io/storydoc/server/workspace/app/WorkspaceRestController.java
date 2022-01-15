package io.storydoc.server.workspace.app;

import io.storydoc.server.workspace.app.dto.FolderDTO;
import io.storydoc.server.workspace.domain.FolderURN;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/folder")
public class WorkspaceRestController {

    private final WorkspaceService workspaceService;

    private final WorkspaceQueryService workspaceQueryService;

    private final WorkspaceSettings workspaceSettings;

    public WorkspaceRestController(WorkspaceService workspaceService, WorkspaceQueryService workspaceQueryService, WorkspaceSettings workspaceSettings) {
        this.workspaceService = workspaceService;
        this.workspaceQueryService = workspaceQueryService;
        this.workspaceSettings = workspaceSettings;
    }

    @GetMapping(value = "/settings", produces = MediaType.APPLICATION_JSON_VALUE)
    public WorkspaceSettings getSettings() {
        return workspaceSettings;
    }

    @GetMapping(value = "/root", produces = MediaType.APPLICATION_JSON_VALUE)
    public FolderURN getRootFolder() {
        return workspaceQueryService.getRootFolder().getUrn();
    }

    @GetMapping(value = "/folders", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FolderDTO> getFolders(FolderURN folderURN) {
        if (folderURN.getPath()==null) {
            folderURN.setPath(new ArrayList<>());
        }
        return workspaceQueryService.listSubFolders(folderURN);
    }

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public FolderURN addFolder(FolderURN parentFolderUrn, String folderName) {
        return workspaceService.addFolder(parentFolderUrn, folderName);
    }

}
