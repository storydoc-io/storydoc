package io.storydoc.server.storydoc.infra.execution.scheduling;

import io.storydoc.server.storydoc.domain.StoryDocException;
import io.storydoc.server.storydoc.infra.execution.AbstractBlockExecutor;
import io.storydoc.server.storydoc.infra.execution.BlockExecutionMgr;
import io.storydoc.server.storydoc.infra.execution.ExecutionContext;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class WorkMgr {

    private final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

    private final BlockExecutionMgr blockExecutionMgr;

    public WorkMgr(BlockExecutionMgr blockExecutionMgr) {
        this.blockExecutionMgr = blockExecutionMgr;
    }

    public void add(ExecutionContext ctx) throws StoryDocException {
        AbstractBlockExecutor executor = blockExecutionMgr.createExecutor(ctx);
        this.executor.execute(() -> {
            executor.onRunning();
            executor.run();
            executor.onFinished();
        });
    }


}
