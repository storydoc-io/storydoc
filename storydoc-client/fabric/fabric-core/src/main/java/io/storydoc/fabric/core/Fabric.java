package io.storydoc.fabric.core;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@SuppressWarnings({"rawtypes", "unchecked"})
@Slf4j
public class Fabric {

    private final HashMap<Class<? extends SystemComponent>, ComponentHandler> handlers = new HashMap<>();

    public Fabric() {
    }

    public void createBundle(BundleDescriptor bundleDesc) {
        bundleDesc.getComponents().forEach((name, componentDescriptor) -> {
            ComponentHandler componentHandler = getComponentHandler(componentDescriptor.getComponentType());
            componentHandler.createBundle(componentDescriptor);
        });
    }

    private ComponentHandler getComponentHandler(Class<? extends SystemComponent> componentType) {
        return handlers.get(componentType);
    }


    public void addHandler(ComponentHandler componentHandler) {
        handlers.put(componentHandler.getComponentType(), componentHandler);
    }

}
