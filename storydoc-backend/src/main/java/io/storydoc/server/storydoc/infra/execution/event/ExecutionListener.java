package io.storydoc.server.storydoc.infra.execution.event;

public interface ExecutionListener {
    void handle(ExecutionEvent event);
}
