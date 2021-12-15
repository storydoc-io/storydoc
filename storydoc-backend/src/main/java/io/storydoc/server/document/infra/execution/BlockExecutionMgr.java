package io.storydoc.server.document.infra.execution;

import io.storydoc.server.document.domain.DocumentException;
import io.storydoc.server.document.infra.store.model.Block;
import io.storydoc.server.document.infra.store.model.CmdBlock;
import io.storydoc.server.document.infra.store.model.TextBlock;

public class BlockExecutionMgr {

    public AbstractBlockExecutor createExecutor(ExecutionContext ctx)  {
        Block block = ctx.getCurrentBlock();
        if (block instanceof TextBlock) {
            return new TextBlockExecution((TextBlock) block, ctx);
        }
        if (block instanceof CmdBlock) {
            return new CmdBlockExecution((CmdBlock) block, ctx);
        }
        throw new DocumentException("no execution found for block ");

    }
}
