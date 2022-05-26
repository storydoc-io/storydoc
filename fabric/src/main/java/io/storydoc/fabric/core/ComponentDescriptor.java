package io.storydoc.fabric.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ComponentDescriptor {

    private Class<? extends Component> componentType;
    private String name;

}
