package io.storydoc.server.storydoc.infra.execution;

import io.storydoc.server.storydoc.infra.execution.event.LifeCycleEvent;
import io.storydoc.server.storydoc.infra.execution.event.LifeCycleType;
import io.storydoc.server.storydoc.infra.execution.event.LogEvent;

abstract public class AbstractBlockExecutor {

    private final ExecutionContext ctx;

    public AbstractBlockExecutor(ExecutionContext ctx) {
        this.ctx = ctx;
    }

    abstract public void run();

    protected void log(String text) {
        ctx.emit(new LogEvent(text));
    }

    public void onRunning() {
        ctx.emit(new LifeCycleEvent(LifeCycleType.BLOCK_RUNNING, ctx));
    }


    public void onFinished() {
        ctx.emit(new LifeCycleEvent(LifeCycleType.BLOCK_FINISHED, ctx));
    }

}
