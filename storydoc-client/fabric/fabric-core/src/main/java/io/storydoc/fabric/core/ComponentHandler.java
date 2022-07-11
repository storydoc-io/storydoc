package io.storydoc.fabric.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@AllArgsConstructor
@Slf4j
public class ComponentHandler<COMPONENT_TYPE extends SystemComponent, COMPONENT_DESCRIPTOR extends ComponentDescriptor> {
    private Class<? extends SystemComponent> componentType;

    public void createBundle(COMPONENT_DESCRIPTOR componentDescriptor) {
        log.debug(String.format("adding '%s' (%s) ",  componentDescriptor.getName(), componentDescriptor.getComponentType().getSimpleName()));
    }

}