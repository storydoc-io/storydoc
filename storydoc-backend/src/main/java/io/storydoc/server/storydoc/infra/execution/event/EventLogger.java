package io.storydoc.server.storydoc.infra.execution.event;

public class EventLogger implements ExecutionListener {
    @Override
    public void handle(ExecutionEvent event) {
        System.out.println(event);
    }
}
