package io.storydoc.server.storydoc.infra.store.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoryDocMetaData {
    private String id;
    private String name;
}
