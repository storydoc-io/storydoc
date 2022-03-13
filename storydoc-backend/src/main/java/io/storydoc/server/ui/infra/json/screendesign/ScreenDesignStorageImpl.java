package io.storydoc.server.ui.infra.json.screendesign;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.storydoc.server.storydoc.app.StoryDocService;
import io.storydoc.server.ui.app.screendesign.ComponentDescriptorDTO;
import io.storydoc.server.ui.domain.screendesign.SDComponentId;
import io.storydoc.server.ui.domain.screendesign.ScreenDesignCoordinate;
import io.storydoc.server.ui.domain.screendesign.ScreenDesignStorage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@org.springframework.stereotype.Component
public class ScreenDesignStorageImpl implements ScreenDesignStorage {

    private ObjectMapper objectMapper;

    private final StoryDocService storyDocService;

    public ScreenDesignStorageImpl(StoryDocService storyDocService) {
        this.storyDocService = storyDocService;
        initObjectMapper();
    }

    private void initObjectMapper() {
        objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @Override
    public void createScreenDesign(ScreenDesignCoordinate coordinate, String name) {
        ScreenDesign screenDesign = new ScreenDesign();
        screenDesign.setId(screenDesign.getId());

        Container rootContainer = new Container();
        rootContainer.setChildren(List.of());
        SDComponentId containerId = createComponentId();
        rootContainer.setId(containerId.getId());
        screenDesign.setRootContainer(rootContainer);

        storyDocService.addArtifact(coordinate.getBlockCoordinate(), coordinate.getScreenDesignId().asArtifactId(),
                io.storydoc.server.ui.domain.screendesign.ScreenDesign.ARTIFACT_TYPE, name) ;
        save(coordinate, screenDesign);
    }

    @Override
    public SDComponentId addComponent(ScreenDesignCoordinate screenDesignCoordinate, SDComponentId parentContainerId, String type, ComponentDescriptorDTO descriptorDTO, int x, int y) {
        SDComponentId componentId = createComponentId();
        ScreenDesign screenDesign = loadScreenDesign(screenDesignCoordinate);
        Container container  = locateContainer(screenDesign.getRootContainer(), parentContainerId);

        Component component =  new Component();
        component.setType(type);
        component.setId(componentId.getId());
        component.setName(descriptorDTO.getLabel() + (getCount(screenDesign.getRootContainer(), descriptorDTO.getType()) + 1));
        component.setX(x);
        component.setY(y);


        container.getChildren().add(component);
        save(screenDesignCoordinate, screenDesign);

        return componentId;
    }

    private int getCount(AbstractComponent aComponent, String type) {
        if (aComponent instanceof Component)  {
            Component component = (Component)aComponent;
            return component.getType().equals(type) ?  1: 0;
        } else {
            Container container = (Container) aComponent;
            return container.getChildren().stream()
                .map(child -> getCount(child, type))
                .reduce(0, Integer::sum);
        }

    }

    private Container locateContainer(Container container, SDComponentId containerId) {
        if (containerId.getId().equals(container.getId())) {
            return container;
        }
        List<Container> subContainers = container.getChildren().stream()
                .filter(child -> child instanceof Container)
                .map(child -> (Container) child)
                .collect(Collectors.toList());
        for(Container subContainer: subContainers) {
            Container found = locateContainer(subContainer, containerId);
            if (found!=null) return found;
        }
        return null;
    }

    private SDComponentId createComponentId() {
        return new SDComponentId(UUID.randomUUID().toString());
    }

    private void save(ScreenDesignCoordinate coordinate, ScreenDesign screenDesign) {
        storyDocService.saveArtifact(coordinate.asArtifactCoordinate(), (OutputStream os) -> { write(screenDesign, os);});
    }

    private void write(ScreenDesign screenDesign, OutputStream outputStream) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputStream, screenDesign);
    }

    @Override
    public ScreenDesign loadScreenDesign(ScreenDesignCoordinate coordinate) {
        return storyDocService.loadArtifact(coordinate.asArtifactCoordinate(), this::read);
    }

    private ScreenDesign read(InputStream inputStream) throws IOException {
        return objectMapper.readValue(inputStream, ScreenDesign.class);
    }
}
