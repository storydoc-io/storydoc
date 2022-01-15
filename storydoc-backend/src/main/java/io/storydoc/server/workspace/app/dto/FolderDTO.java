package io.storydoc.server.workspace.app.dto;

import io.storydoc.server.workspace.domain.FolderURN;
import lombok.Data;

@Data
public class FolderDTO {

    private String name;

    private FolderURN urn;

}
