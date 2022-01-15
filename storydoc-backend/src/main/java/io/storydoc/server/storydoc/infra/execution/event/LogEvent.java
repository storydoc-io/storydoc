package io.storydoc.server.storydoc.infra.execution.event;

public class LogEvent extends ExecutionEvent {

    private final String txt;

    public LogEvent(String txt) {
        this.txt = txt;
    }

    public String getTxt() {
        return txt;
    }

    @Override
    public String toString() {
        return "LogEvent{" +
                "txt='" + txt + '\'' +
                '}';
    }
}
