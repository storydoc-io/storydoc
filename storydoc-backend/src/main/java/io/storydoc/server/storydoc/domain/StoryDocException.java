package io.storydoc.server.storydoc.domain;

public class StoryDocException extends RuntimeException {

    public StoryDocException(String message, Throwable cause) {
        super(message, cause);
    }

    public StoryDocException(String message) {
        super(message);
    }
}
