package io.storydoc.fabric.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ComponentDescriptor {

    private Class<? extends SystemComponent> componentType;
    private String name;

}
