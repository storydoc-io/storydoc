package io.storydoc.fabric.elastic;

import io.storydoc.fabric.core.ComponentDescriptor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ElasticComponentDescriptor extends ComponentDescriptor {

    @Builder
    public ElasticComponentDescriptor(String name) {
        super(ElasticComponent.class, name);
    }

}

