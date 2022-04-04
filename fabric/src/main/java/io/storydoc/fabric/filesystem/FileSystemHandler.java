package io.storydoc.fabric.filesystem;

import io.storydoc.fabric.core.ComponentDescriptor;
import io.storydoc.fabric.core.ComponentHandler;
import lombok.SneakyThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

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
        try (Stream<Path> stream = Files.walk(src)) {
            stream.forEach(source -> copy(source, dest.resolve(src.relativize(source))));
        }
    }

    private void copy(Path source, Path dest) {
        try {
            Files.copy(source, dest);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
