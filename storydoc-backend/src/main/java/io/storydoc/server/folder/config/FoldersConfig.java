package io.storydoc.server.folder.config;

import io.storydoc.server.folder.app.FolderQueryService;
import io.storydoc.server.folder.app.FolderQueryServiceImpl;
import io.storydoc.server.folder.app.FolderService;
import io.storydoc.server.folder.app.FolderServiceImpl;
import io.storydoc.server.folder.domain.FolderStore;
import io.storydoc.server.folder.infra.FileSystemFolderStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

@Configuration
public class FoldersConfig {

    @Bean
    FolderStore folderStore() {
        return new FileSystemFolderStore(Path.of("/home/stiepeb/_/project/github/storydoc-io/workspace"));
    }

    @Bean
    FolderService folderService() {
        return new FolderServiceImpl(folderStore());
    }

    @Bean
    FolderQueryService folderQueryService() {
        return new FolderQueryServiceImpl(folderStore());
    }

}
