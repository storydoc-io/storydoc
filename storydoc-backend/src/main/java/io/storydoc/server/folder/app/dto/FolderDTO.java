package io.storydoc.server.folder.app.dto;

import io.storydoc.server.folder.domain.FolderURN;
import lombok.Data;

@Data
public class FolderDTO {

    private String name;

    private FolderURN urn;

}
