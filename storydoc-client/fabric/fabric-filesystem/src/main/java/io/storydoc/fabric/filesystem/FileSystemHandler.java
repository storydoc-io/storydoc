package io.storydoc.fabric.filesystem;

import io.storydoc.fabric.core.ComponentDescriptor;
import io.storydoc.fabric.core.ComponentHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.file.Path;

@Slf4j
public class FileSystemHandler extends ComponentHandler {
    public FileSystemHandler() {
        super(FileSystemComponent.class);
    }

    @Override
    @SneakyThrows
    public void createBundle(ComponentDescriptor componentDescriptor) {
        super.createBundle(componentDescriptor);
        FileSystemComponentDescriptor fileSystemComponentDescriptor = (FileSystemComponentDescriptor) componentDescriptor;

        copyFolder(fileSystemComponentDescriptor.getSourcePath(), fileSystemComponentDescriptor.getDestinationPath());
    }

    public  void copyFolder(Path src, Path dest) throws IOException {
        FileUtils.copyDirectory(src.toFile(), dest.toFile());
    }

}
