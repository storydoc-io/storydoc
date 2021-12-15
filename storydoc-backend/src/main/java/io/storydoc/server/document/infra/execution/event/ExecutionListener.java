package io.storydoc.server.document.infra.execution.event;

public interface ExecutionListener {
    void handle(ExecutionEvent event);
}
