package io.storydoc.server.workspace.app;

import io.storydoc.server.TestBase;
import io.storydoc.server.workspace.WorkspaceTestFixture;
import io.storydoc.server.workspace.app.dto.FolderDTO;
import io.storydoc.server.workspace.domain.*;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class WorkspaceServiceTest extends TestBase {

    @Autowired
    private WorkspaceQueryService workspaceQueryService;

    @Autowired
    private WorkspaceService workspaceService;

    private WorkspaceTestFixture utils;

    @Before
    public void setup() {
        utils = new WorkspaceTestFixture(workspaceQueryService);
    }

    @Test
    public void getRootFolder()  {
        // when
        FolderDTO rootFolderDto = workspaceQueryService.getRootFolder();
        // then
        assertNotNull(rootFolderDto);
        assertNotNull(rootFolderDto.getUrn());
        assertEquals(0, rootFolderDto.getUrn().getPath().size());

    }

    @Test
    public void addMultipleFolders() {
        // given
        FolderURN rootFolderURN = workspaceQueryService.getRootFolder().getUrn();

        // when
        {
            String folderName = "folder-1-" + UUID.randomUUID();
            workspaceService.addFolder(rootFolderURN, folderName);

            // then
            {
                List<FolderDTO> subFolders = workspaceQueryService.listSubFolders(rootFolderURN);
                assertNotNull(subFolders);
                assertEquals(1, subFolders.size());
                FolderDTO folderDto = subFolders.get(0);
                assertEquals(folderName, folderDto.getName());
                assertEquals(folderName, folderDto.getUrn().getPath().get(0));
            }
        }

        // and

        // when
        {
            String folderName2 = "folder-2-" + UUID.randomUUID();
            workspaceService.addFolder(rootFolderURN, folderName2);

            // then
            {
                List<FolderDTO> subFolders = workspaceQueryService.listSubFolders(rootFolderURN);
                assertEquals(2, subFolders.size());
                FolderDTO folderDto = subFolders.get(1);
                assertEquals(folderName2, folderDto.getName());
                assertEquals(folderName2, folderDto.getUrn().getPath().get(0));
            }
        }

        utils.logFolderStructure("after adding 2 subfolders to root folder");
    }

    @Test
    public void addSubSubFolder() {
        // given
        FolderURN rootFolderURN = workspaceQueryService.getRootFolder().getUrn();

        String subFolderName = "sub-folder-" + UUID.randomUUID();
        FolderURN subFolderUrn = workspaceService.addFolder(rootFolderURN, subFolderName);

        // when
        String subSubFolderName = "sub-folder-" + UUID.randomUUID();
        workspaceService.addFolder(subFolderUrn, subSubFolderName);

        utils.logFolderStructure("after adding folder with subfolder");

        // then
        {
            List<FolderDTO> subFolders = workspaceQueryService.listSubFolders(rootFolderURN);
            assertEquals(1, subFolders.size());

            FolderDTO folderDto = subFolders.get(0);
            assertEquals(subFolderName, folderDto.getName());

            List<String> path = folderDto.getUrn().getPath();
            assertEquals(1, path.size());
            assertEquals(subFolderName, path.get(0));
        }
        {
            List<FolderDTO> subFolders = workspaceQueryService.listSubFolders(subFolderUrn);
            assertEquals(1, subFolders.size());

            FolderDTO folderDto = subFolders.get(0);
            assertEquals(subSubFolderName, folderDto.getName());

            List<String> path = folderDto.getUrn().getPath();
            assertEquals(2, path.size());
            assertEquals(subFolderName, path.get(0));
            assertEquals(subSubFolderName, path.get(1));
        }



    }



    @Test
    public void cannotAddSameFolderTwice() {
        // given
        FolderURN rootFolderURN = workspaceQueryService.getRootFolder().getUrn();

        // when
        String folderName = "folder-" + UUID.randomUUID();
        {
            workspaceService.addFolder(rootFolderURN, folderName);

            // then
            {
                List<FolderDTO> subFolders = workspaceQueryService.listSubFolders(rootFolderURN);
                assertNotNull(subFolders);
                assertEquals(1, subFolders.size());
                FolderDTO folderDto = subFolders.get(0);
                assertEquals(folderName, folderDto.getName());
                assertEquals(folderName, folderDto.getUrn().getPath().get(0));
            }
        }

        // and

        // when
        {
            workspaceService.addFolder(rootFolderURN, folderName);

            // then
            {
                List<FolderDTO> subFolders = workspaceQueryService.listSubFolders(rootFolderURN);
                assertEquals(1, subFolders.size());
            }
        }


    }

    class DummyResource implements WorkspaceResource {
        String text;
    }


    private DummyResource readDummyResource(InputStream inputStream) {
        DummyResource resource = new DummyResource();
        resource.text = new BufferedReader(new InputStreamReader(inputStream))
                .lines().collect(Collectors.joining("\n"));
        return resource;
    }

    private void writeContent(String content, OutputStream outputStream) {
        try (PrintWriter p = new PrintWriter(outputStream)) {
            p.println(content);
        }

    }

    @SneakyThrows
    @Test
    public void new_write_content() {
        // given the content of a resource to be written in the root folder
        String content = "content-" + UUID.randomUUID();
        InputStream inputStream = new ByteArrayInputStream(content.getBytes());

        FolderURN rootFolder = workspaceQueryService.getRootFolder().getUrn();
        String resourceName = "name-" + UUID.randomUUID();
        ResourceUrn resourceUrn = new ResourceUrn(rootFolder, resourceName);

        //when I write the resource
        workspaceService.saveResource(resourceUrn, (outputStream) -> this.writeContent(content, outputStream));

        // then I can retrieve the content
        DummyResource read = workspaceService.loadResource(resourceUrn, this::readDummyResource);
        assertEquals(content, read.text);

    }



}