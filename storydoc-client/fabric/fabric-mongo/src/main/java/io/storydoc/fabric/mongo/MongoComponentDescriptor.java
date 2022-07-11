package io.storydoc.fabric.mongo;

import io.storydoc.fabric.core.ComponentDescriptor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MongoComponentDescriptor extends ComponentDescriptor {

    private String connectionUrl;

    @Builder
    public MongoComponentDescriptor(String name, String connectionUrl) {
        super(MongoComponent.class, name);
        this.connectionUrl = connectionUrl;
    }

}
