package io.storydoc.stitch;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StitchEngine {

    boolean active;

    private StitchConfig config;

    public StitchEngine(StitchConfig config) {
        this.config = config;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void add(String modelName, String eventName, String jsonValue) {
        log.info(String.format("%s|%s|%s", modelName, eventName, jsonValue));
    }
}
