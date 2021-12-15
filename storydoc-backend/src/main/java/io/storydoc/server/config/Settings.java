package io.storydoc.server.config;

import java.nio.file.Path;

public class Settings {

    private Path workFolder;

    public Path getWorkFolder() {
        return workFolder;
    }

    public void setWorkFolder(Path workFolder) {
        this.workFolder = workFolder;
    }
}
