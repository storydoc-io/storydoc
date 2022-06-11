package io.storydoc.fabric;

import io.storydoc.fabric.core.BundleDescriptor;
import io.storydoc.fabric.core.Component;
import io.storydoc.fabric.core.ComponentHandler;
import io.storydoc.fabric.filesystem.FileSystemHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Fabric {

    private Map<Class<? extends Component>, ComponentHandler> handlers = new HashMap<>();

    public void createBundle(BundleDescriptor bundleDesc) {
        bundleDesc.getComponents().forEach((name, componentDescriptor) -> {
            ComponentHandler componentHandler = getComponentHandler(componentDescriptor.getComponentType());
            componentHandler.createBundle(componentDescriptor);
        });
    }

    private ComponentHandler getComponentHandler(Class<? extends Component> componentType) {
        return handlers.get(componentType);
    }

    Fabric() {
        addHandler(new FileSystemHandler());
    }

    public void addHandler(ComponentHandler componentHandler) {
        handlers.put(componentHandler.getComponentType(), componentHandler);
    }

}
