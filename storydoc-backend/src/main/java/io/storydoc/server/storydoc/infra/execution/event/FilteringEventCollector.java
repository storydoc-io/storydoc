package io.storydoc.server.storydoc.infra.execution.event;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class FilteringEventCollector implements ExecutionListener {

    private List<ExecutionEvent> events = new ArrayList<>();

    private final Function<ExecutionEvent, Boolean> filter;

    public FilteringEventCollector(Function<ExecutionEvent, Boolean> filter) {
        this.filter = filter;
    }

    @Override
    public void handle(ExecutionEvent event) {
        if (filter.apply(event)) {
            events.add(event);
        }
    }

    public List<ExecutionEvent> getEvents() {
        return events;
    }
}
