package io.storydoc.server.folder.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FolderURN {
    private List<String> path;
}
