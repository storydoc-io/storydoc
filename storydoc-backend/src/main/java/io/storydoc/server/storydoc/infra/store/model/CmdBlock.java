package io.storydoc.server.storydoc.infra.store.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CmdBlock extends Block {

    private String cmd;

    @Builder
    public CmdBlock(String id, String name, String cmd) {
        super(id, "COMMAND", name, null);
        this.cmd = cmd;
    }

}
