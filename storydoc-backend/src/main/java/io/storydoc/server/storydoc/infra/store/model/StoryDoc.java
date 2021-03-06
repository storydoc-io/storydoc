package io.storydoc.server.storydoc.infra.store.model;

import io.storydoc.server.workspace.domain.WorkspaceResource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoryDoc extends CompositeBlock implements WorkspaceResource {

    String id;

    String name;


}
