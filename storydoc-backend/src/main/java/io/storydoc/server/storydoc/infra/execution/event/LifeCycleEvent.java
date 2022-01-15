package io.storydoc.server.storydoc.infra.execution.event;

import io.storydoc.server.storydoc.infra.execution.ExecutionContext;

public class LifeCycleEvent extends ExecutionEvent  {

    private final LifeCycleType type;

    private final ExecutionContext ctx;

    public LifeCycleEvent(LifeCycleType type, ExecutionContext ctx) {
        this.type = type;
        this.ctx = ctx;
    }

    public LifeCycleType getType() {
        return type;
    }

    public ExecutionContext getCtx() {
        return ctx;
    }

    @Override
    public String toString() {
        return "LifeCycleEvent{" +
                "type=" + type +
                '}';
    }
}
