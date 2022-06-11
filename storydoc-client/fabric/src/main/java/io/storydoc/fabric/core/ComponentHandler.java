package io.storydoc.fabric.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@AllArgsConstructor
@Slf4j
public class ComponentHandler {
    private Class<? extends Component> componentType;

    public void createBundle(ComponentDescriptor componentDescriptor) {
        log.debug(String.format("adding '%s' (%s) ",  componentDescriptor.getName(), componentDescriptor.getComponentType().getSimpleName()));
    }

}