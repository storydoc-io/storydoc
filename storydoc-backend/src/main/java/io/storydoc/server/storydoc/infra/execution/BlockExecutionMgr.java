package io.storydoc.server.storydoc.infra.execution;

import io.storydoc.server.storydoc.domain.StoryDocException;
import io.storydoc.server.storydoc.infra.store.model.Block;
import io.storydoc.server.storydoc.infra.store.model.CmdBlock;
import io.storydoc.server.storydoc.infra.store.model.TextBlock;

public class BlockExecutionMgr {

    public AbstractBlockExecutor createExecutor(ExecutionContext ctx)  {
        Block block = ctx.getCurrentBlock();
        if (block instanceof TextBlock) {
            return new TextBlockExecution((TextBlock) block, ctx);
        }
        if (block instanceof CmdBlock) {
            return new CmdBlockExecution((CmdBlock) block, ctx);
        }
        throw new StoryDocException("no execution found for block ");

    }
}
