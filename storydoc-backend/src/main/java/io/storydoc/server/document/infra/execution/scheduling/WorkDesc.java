package io.storydoc.server.document.infra.execution.scheduling;

import io.storydoc.server.document.infra.store.model.Block;
import io.storydoc.server.document.infra.execution.ExecutionContext;

public class WorkDesc {

    private Block block;
    private ExecutionContext ctx;

    public WorkDesc(Block block, ExecutionContext ctx) {
        this.block = block;
        this.ctx = ctx;
    }

    public Block getBlock() {
        return block;
    }

    public ExecutionContext getCtx() {
        return ctx;
    }
}
