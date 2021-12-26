package io.storydoc.server.folder.app;

import io.storydoc.server.TestBase;
import io.storydoc.server.folder.app.dto.FolderDTO;
import io.storydoc.server.folder.domain.FolderURN;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FolderServiceTest extends TestBase {

    @Autowired
    private FolderQueryService folderQueryService;

    @Autowired
    private FolderService folderService;

    @Test
    public void getRootFolder()  {
        // when
        FolderDTO rootFolderDto = folderQueryService.getRootFolder();

        // then
        assertNotNull(rootFolderDto);
        assertNotNull(rootFolderDto.getUrn());
        assertEquals(0, rootFolderDto.getUrn().getPath().size());

    }

    @Test
    public void addMultiplelFolders() {
        // given
        FolderURN rootFolderURN = folderQueryService.getRootFolder().getUrn();

        // when
        {
            String folderName = "folder-1 -" + UUID.randomUUID();
            folderService.addFolder(rootFolderURN, folderName);

            // then
            {
                List<FolderDTO> subFolders = folderQueryService.listSubFolders(rootFolderURN);
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
            folderService.addFolder(rootFolderURN, folderName2);

            // then
            {
                List<FolderDTO> subFolders = folderQueryService.listSubFolders(rootFolderURN);
                assertEquals(2, subFolders.size());
                FolderDTO folderDto = subFolders.get(1);
                assertEquals(folderName2, folderDto.getName());
                assertEquals(folderName2, folderDto.getUrn().getPath().get(0));
            }
        }


    }

    @Test
    public void addSubSubFolder() {
        // given
        FolderURN rootFolderURN = folderQueryService.getRootFolder().getUrn();

        String subFolderName = "sub-folder-" + UUID.randomUUID();
        FolderURN subFolderUrn = folderService.addFolder(rootFolderURN, subFolderName);

        // when
        String subSubFolderName = "sub-folder-" + UUID.randomUUID();
        folderService.addFolder(subFolderUrn, subSubFolderName);

        // then
        {
            List<FolderDTO> subFolders = folderQueryService.listSubFolders(rootFolderURN);
            assertEquals(1, subFolders.size());

            FolderDTO folderDto = subFolders.get(0);
            assertEquals(subFolderName, folderDto.getName());

            List<String> path = folderDto.getUrn().getPath();
            assertEquals(1, path.size());
            assertEquals(subFolderName, path.get(0));
        }
        {
            List<FolderDTO> subFolders = folderQueryService.listSubFolders(subFolderUrn);
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
        FolderURN rootFolderURN = folderQueryService.getRootFolder().getUrn();

        // when
        String folderName = "folder-" + UUID.randomUUID();
        {
            folderService.addFolder(rootFolderURN, folderName);

            // then
            {
                List<FolderDTO> subFolders = folderQueryService.listSubFolders(rootFolderURN);
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
            folderService.addFolder(rootFolderURN, folderName);

            // then
            {
                List<FolderDTO> subFolders = folderQueryService.listSubFolders(rootFolderURN);
                assertEquals(1, subFolders.size());
            }
        }


    }



}