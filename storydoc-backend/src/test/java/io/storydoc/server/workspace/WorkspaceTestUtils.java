package io.storydoc.server.workspace;

import io.storydoc.server.workspace.app.WorkspaceQueryService;
import io.storydoc.server.workspace.app.dto.FolderDTO;
import io.storydoc.server.workspace.app.dto.ResourceDTO;
import io.storydoc.server.workspace.domain.ResourceUrn;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Slf4j
@Component
public class WorkspaceTestUtils {

    private final WorkspaceQueryService queryService;

    public WorkspaceTestUtils(WorkspaceQueryService queryService) {
        this.queryService = queryService;
    }

    @SneakyThrows
    public void logResourceContent(String msg, ResourceUrn resourceUrn)  {
        log.trace("");
        log.trace(msg + ":" );
        log.trace("\turl: " + format(resourceUrn));
        log.trace("\tcontent:" );

        log.trace(new String(queryService.getInputStream(resourceUrn).readAllBytes()));
    }

    @SneakyThrows
    public void logBinaryResourceContent(String msg, ResourceUrn resourceUrn){
        log.trace("");
        log.trace("\turl: " + format(resourceUrn));
        log.trace(msg + ":" );
        log.trace("\tcontent:" );

        try(InputStream inputStream = queryService.getInputStream(resourceUrn)) {
            logBinaryInputstream(inputStream);
        }
    }

    @SneakyThrows
    public void logBinaryInputstream(InputStream inputStream) {
        int numberOfColumns = 20;
        long streamPtr=0;
        StringBuilder stringBuilder = new StringBuilder();
        while (inputStream.available() > 0) {
            final long col = streamPtr++ % numberOfColumns;
            stringBuilder.append(String.format("%02x ", inputStream.read()));
            if (col == (numberOfColumns-1)) {
                log.trace(stringBuilder.toString());
                stringBuilder = new StringBuilder();
            }
        }
        log.trace(stringBuilder.toString());
    }

    private String format(ResourceUrn resourceUrn) {
        StringBuilder stringBuilder = new StringBuilder();
        for(String pathElem: resourceUrn.getFolder().getPath()) {
            stringBuilder.append('/');
            stringBuilder.append(pathElem);
        }
        stringBuilder.append('/');
        stringBuilder.append(resourceUrn.getFileName());
        return stringBuilder.toString();
    }

    public void logFolderStructure(String msg){
        log.trace("folder structure " + msg);
        FolderDTO rootFolder = queryService.getRootFolder();
        String indent = "";
        recursiveLogFolderStructure(rootFolder, indent);
    }

    private void recursiveLogFolderStructure(FolderDTO parentFolder, String indent) {
        if (parentFolder.getUrn().getPath().size()==0) {
            log.trace(indent + "<root>");
        } else {
            log.trace(indent + "d " + parentFolder.getName());
        }
        String newIndent = "   " + indent;
        List<FolderDTO> subFolders = queryService.listSubFolders(parentFolder.getUrn());
        if (subFolders!=null) {
            for(FolderDTO subFolder: subFolders) {
                recursiveLogFolderStructure(subFolder, newIndent);
            }
        }
        List<ResourceDTO> resources = queryService.listResources(parentFolder.getUrn());
        if (resources != null) {
            for(ResourceDTO resourceDTO: resources) {
                log.trace(newIndent + "- " + resourceDTO.getName());
            }
        }
    }
}
