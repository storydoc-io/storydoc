package io.storydoc.server.ui.app.screendesign;

import io.storydoc.server.infra.IDGenerator;
import io.storydoc.server.storydoc.domain.BlockCoordinate;
import io.storydoc.server.ui.domain.screendesign.*;
import org.springframework.stereotype.Service;

@Service
public class ScreenDesignServiceImpl implements ScreenDesignService {

    private final IDGenerator idGenerator;

    private final ScreenDesignDomainService domainService;

    private final ScreenDesignQueryService screenDesignQueryService;

    public ScreenDesignServiceImpl(IDGenerator idGenerator, ScreenDesignDomainService domainService, ScreenDesignQueryService screenDesignQueryService) {
        this.idGenerator = idGenerator;
        this.domainService = domainService;
        this.screenDesignQueryService = screenDesignQueryService;
    }

    @Override
    public ScreenDesignCoordinate createScreenDesign(BlockCoordinate blockCoordinate, String name) {
        ScreenDesignId screenDesignId = new ScreenDesignId(idGenerator.generateID(ScreenDesignId.ID_PREFIX));
        ScreenDesignCoordinate screenDesignCoordinate = ScreenDesignCoordinate.of(blockCoordinate, screenDesignId);
        domainService.createScreenDesign(screenDesignCoordinate, name);
        return screenDesignCoordinate;
    }

    @Override
    public SDComponentId addComponent(ScreenDesignCoordinate screenDesignCoordinate, SDComponentId parentContainerId, String type, int x, int y) {
        ComponentDescriptorDTO descriptorDTO = screenDesignQueryService.getDescriptor(type);
        ScreenDesign screenDesign = domainService.getScreenDesign(screenDesignCoordinate.getScreenDesignId());
        return screenDesign.addComponent(screenDesignCoordinate, parentContainerId, type, descriptorDTO, x, y);
    }
}
