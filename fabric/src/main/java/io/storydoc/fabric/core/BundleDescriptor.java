package io.storydoc.fabric.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
public class BundleDescriptor {

    private Map<String, ComponentDescriptor> components;

    static public class Builder {

        private Map<String, ComponentDescriptor> componentDescriptorMap = new HashMap<>();

        public Builder component(ComponentDescriptor componentDescriptor) {
            componentDescriptorMap.put(componentDescriptor.getName(), componentDescriptor);
            return this;
        }

        public BundleDescriptor build() {
            return new BundleDescriptor(componentDescriptorMap);
        }
    }

    static public Builder builder() {
        return new Builder();
    }


}
