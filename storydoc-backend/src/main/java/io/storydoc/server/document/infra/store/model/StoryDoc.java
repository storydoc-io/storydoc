package io.storydoc.server.document.infra.store.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoryDoc {

    String id;

    String name;

    List<Block> blocks;

}
