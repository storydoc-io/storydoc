package io.storydoc.server.document.infra.store.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Section extends Block {

    List<Block> blocks;

}
