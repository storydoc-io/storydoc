package io.storydoc.fabric.filesystem;

import io.storydoc.fabric.core.ComponentDescriptor;
import lombok.Builder;
import lombok.Getter;

import java.nio.file.Path;

@Getter
public class FileSystemComponentDescriptor extends ComponentDescriptor {

    private final Path destinationPath;
    private final Path sourcePath;

    @Builder
    public FileSystemComponentDescriptor(String name, Path destinationPath, Path sourcePath) {
        super(FileSystemComponent.class, name);
        this.destinationPath = destinationPath;
        this.sourcePath = sourcePath;
    }

}
