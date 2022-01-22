package io.storydoc.server.storydoc.infra.store.model;

import io.storydoc.server.workspace.domain.WorkspaceResource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoryDocs implements WorkspaceResource {

    private List<StoryDocMetaData> storyDocs;

}
