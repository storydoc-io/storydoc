package io.storydoc.server.workspace.domain;

import lombok.ToString;
import lombok.Value;

import java.util.Arrays;

@Value
@ToString
public class ResourceUrn {
    FolderURN folder;
    String fileName;
    static public ResourceUrn of(String...segments) {
        FolderURN folderURN = FolderURN.of(Arrays.copyOf(segments, segments.length - 1));
        return new ResourceUrn(folderURN, segments[segments.length-1]);
    }
}
