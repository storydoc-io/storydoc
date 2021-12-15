package io.storydoc.server.document.domain;

public class DocumentException extends RuntimeException {

    public DocumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public DocumentException(String message) {
        super(message);
    }
}
