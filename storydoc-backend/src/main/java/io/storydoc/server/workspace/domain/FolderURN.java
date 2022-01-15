package io.storydoc.server.workspace.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
public class FolderURN {
    private List<String> path;

    public static FolderURN of(String...path) {
        return new FolderURN(Arrays.stream(path).collect(Collectors.toList()));
    }

    public ResourceUrn resolve(ResourceUrn relativeResourceUrn) {
        return new ResourceUrn(
                new FolderURN(Stream.of(path, relativeResourceUrn.getFolder().path)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList())),
                relativeResourceUrn.getFileName());
    }
}
