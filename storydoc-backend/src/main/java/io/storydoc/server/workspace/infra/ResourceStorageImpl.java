package io.storydoc.server.workspace.infra;

import io.storydoc.server.workspace.app.WorkspaceSettings;
import io.storydoc.server.workspace.app.dto.ResourceDTO;
import io.storydoc.server.workspace.domain.*;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ResourceStorageImpl implements ResourceStorage {

    private final WorkspaceSettings folderSettings;

    public ResourceStorageImpl(WorkspaceSettings folderSettings) {
        this.folderSettings = folderSettings;
    }

    @Override
    public void store(ResourceSaveContext context) throws WorkspaceException {
        try (OutputStream outputStream = getOutputStream(context.getResourceUrn())) {
            context.write(outputStream);
        } catch(IOException ioe) {
            throw new WorkspaceException("could not store resource " + context.getResourceUrn(), ioe);
        }
    }

    @Override
    public <R extends WorkspaceResource> R load(ResourceLoadContext context) throws WorkspaceException {
        try (InputStream inputStream = getInputStream(context.getResourceUrn())) {
            return (R) context.read(inputStream);
        } catch(IOException ioe) {
            throw new WorkspaceException("could not load resource " + context.getResourceUrn(), ioe);
        }

    }

    @Override
    public void delete(ResourceUrn resourceUrn) throws WorkspaceException {
        try {
            getFile(resourceUrn).delete();
        } catch(Exception e) {
            throw new WorkspaceException("could not delete resource " + resourceUrn, e);
        }
    }

    @Override
    public InputStream getInputStream(ResourceUrn urn) throws WorkspaceException {
        try {
            return new FileInputStream(getFile(urn));
        } catch (FileNotFoundException e) {
            throw new WorkspaceException("could not open inputstream ", e);
        }
    }

    private OutputStream getOutputStream(ResourceUrn resourceUrn) throws FileNotFoundException {
        return new FileOutputStream(getFile(resourceUrn));
    }

    private Path resolveFolderPath(FolderURN folderURN) {
        Path path = folderSettings.getWorkspaceFolder();
        for (String subDirName: folderURN.getPath()) {
            path= path.resolve(subDirName);
        }
        return path;
    }

    private File getFile(ResourceUrn urn) {
        Path path = resolveFolderPath(urn.getFolder()).resolve(urn.getFileName());
        return path.toFile();
    }

    @Override
    public List<ResourceDTO> listResources(FolderURN folderURN) {
        Path parentFolder = resolveFolderPath(folderURN);
        File file = parentFolder.toFile();

        FilenameFilter onlyFiles = new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isFile();
            }
        };
        String[] fileNames = file.list(onlyFiles);
        if (fileNames==null) {
            return new ArrayList<>();
        }
        return Arrays.stream(fileNames)
                .sorted()
                .map(name -> {
                    ResourceDTO dto = ResourceDTO.builder()
                        .name(name)
                        .urn(new ResourceUrn(folderURN, name))
                        .build();
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void saveResource(ResourceUrn resourceUrn, WorkspaceResourceSerializer serializer)  throws WorkspaceException {
        try (OutputStream outputStream = getOutputStream(resourceUrn)) {
            serializer.write(outputStream);
        } catch(IOException ioe) {
            throw new WorkspaceException("could not store resource " + resourceUrn, ioe);
        }

    }

    @Override
    public <R extends WorkspaceResource> R loadResource(ResourceUrn resourceUrn, WorkspaceResourceDeserializer<R> deserializer) throws WorkspaceException{
        try (InputStream inputStream = getInputStream(resourceUrn)) {
            return deserializer.read(inputStream);
        } catch(IOException ioe) {
            throw new WorkspaceException("could not load resource " + resourceUrn, ioe);
        }
    }
}
