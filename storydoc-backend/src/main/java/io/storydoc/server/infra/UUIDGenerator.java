package io.storydoc.server.infra;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDGenerator implements IDGenerator{

    @Override
    public String generateID(String category) {
        return category.toLowerCase() + "-" + UUID.randomUUID().toString();
    }
}
