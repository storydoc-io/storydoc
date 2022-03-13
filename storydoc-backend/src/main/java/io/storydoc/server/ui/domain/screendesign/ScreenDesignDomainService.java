package io.storydoc.server.ui.domain.screendesign;

import org.springframework.stereotype.Service;

@Service
public class ScreenDesignDomainService {

    private final ScreenDesignStorage storage;

    public ScreenDesignDomainService(ScreenDesignStorage storage) {
        this.storage = storage;
    }

    public void createScreenDesign(ScreenDesignCoordinate coordinate, String name) {
        storage.createScreenDesign(coordinate, name);
    }

    public ScreenDesign getScreenDesign(ScreenDesignId screenDesignId) {
        return new ScreenDesign(screenDesignId, storage);
    }
}
