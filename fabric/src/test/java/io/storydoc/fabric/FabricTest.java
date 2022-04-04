package io.storydoc.fabric;

import io.storydoc.fabric.core.BundleDescriptor;
import io.storydoc.fabric.filesystem.FileSystemComponentDescriptor;
import org.junit.Test;

import java.nio.file.Path;

public class FabricTest {

    @Test
    public void init() {

        Fabric fabric = new Fabric();

        FileSystemComponentDescriptor workspaceDesc = FileSystemComponentDescriptor.builder()
                .name("workspace")
                .sourcePath(Path.of("src", "test", "resources", "data"))
                .destinationPath(Path.of("target", "testdata"))
                .build();

        BundleDescriptor bundleDesc = BundleDescriptor.builder()
                .component(workspaceDesc)
                .build();

        fabric.createBundle(bundleDesc);


    }

}
