package io.storydoc.fabric.filesystem;

import io.storydoc.fabric.core.BundleDescriptor;
import io.storydoc.fabric.core.Fabric;
import lombok.SneakyThrows;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.nio.file.Path;
import java.util.UUID;

public class FileSystemHandlerTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    @SneakyThrows
    public void createBundle()  {

        Path destFolderPath = temporaryFolder.newFolder("dest-" + UUID.randomUUID()).toPath();

        Fabric fabric = new Fabric();
        fabric.addHandler(new FileSystemHandler());

        FileSystemComponentDescriptor workspaceDesc = FileSystemComponentDescriptor.builder()
                .name("workspace")
                .sourcePath(Path.of("src", "test", "resources", "data"))
                .destinationPath(destFolderPath)
                .build();

        BundleDescriptor bundleDesc = BundleDescriptor.builder()
                .component(workspaceDesc)
                .build();

        fabric.createBundle(bundleDesc);


    }

}
