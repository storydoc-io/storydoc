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

    public void add(String line) {
        log.info(line);
    }
}
