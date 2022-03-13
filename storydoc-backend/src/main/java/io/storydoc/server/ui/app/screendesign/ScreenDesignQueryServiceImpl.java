package io.storydoc.server.ui.app.screendesign;

import io.storydoc.server.storydoc.app.StoryDocQueryService;
import io.storydoc.server.storydoc.app.dto.StoryDocSummaryDTO;
import io.storydoc.server.storydoc.domain.ArtifactMetaData;
import io.storydoc.server.storydoc.domain.StoryDocId;
import io.storydoc.server.ui.domain.screendesign.SDComponentId;
import io.storydoc.server.ui.domain.screendesign.ScreenDesignCoordinate;
import io.storydoc.server.ui.domain.screendesign.ScreenDesignStorage;
import io.storydoc.server.ui.infra.json.screendesign.AbstractComponent;
import io.storydoc.server.ui.infra.json.screendesign.Component;
import io.storydoc.server.ui.infra.json.screendesign.Container;
import io.storydoc.server.ui.infra.json.screendesign.ScreenDesign;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static io.storydoc.server.ui.app.screendesign.ComponentAttributeType.*;

@Service
public class ScreenDesignQueryServiceImpl implements ScreenDesignQueryService {

    private final StoryDocQueryService storyDocQueryService;
    
    private final ScreenDesignStorage storage;

    public ScreenDesignQueryServiceImpl(StoryDocQueryService storyDocQueryService, ScreenDesignStorage storage) {
        this.storyDocQueryService = storyDocQueryService;
        this.storage = storage;
    }

    public ScreenDesignDTO getScreenDesign(ScreenDesignCoordinate coordinate) {
        ArtifactMetaData metaData = getMetaData(coordinate);
        return toDto(storage.loadScreenDesign(coordinate),metaData, coordinate);

    }

    private ScreenDesignDTO toDto(ScreenDesign screenDesign, ArtifactMetaData metaData, ScreenDesignCoordinate coordinate) {
        return ScreenDesignDTO.builder()
                .name(metaData.getName())
                .coordinate(coordinate)
                .storyDocSummaryDTO(getStoryDocSummary(coordinate.getBlockCoordinate().getStoryDocId()))
                .rootContainer(toDto(screenDesign.getRootContainer()).getContainer())
                .build();
    }

    private SDComponentTypeSelectionDTO toDto(AbstractComponent abstractComponent) {
        SDComponentTypeSelectionDTO dto = new SDComponentTypeSelectionDTO();
        if (abstractComponent instanceof Container) {
            Container container = (Container)abstractComponent;
            dto.setContainer(SDContainerDTO.builder()
                .id(SDComponentId.fromString(container.getId()))
                .children(container.getChildren().stream()
                    .map(child -> toDto(child))
                    .collect(Collectors.toList())
                )
                .build());
        } else if (abstractComponent instanceof Component) {
            Component component = (Component) abstractComponent;
            dto.setComponent(SDComponentDTO.builder()
                .id(SDComponentId.fromString(component.getId()))
                .name(component.getName())
                .x(component.getX())
                .y(component.getY())
                .type(component.getType())
                .attributes(List.of())
                .build());
        }
        return dto;
    }

    private ArtifactMetaData getMetaData(ScreenDesignCoordinate coordinate) {
        return storyDocQueryService.getArtifactMetaData(coordinate.asArtifactCoordinate());
    }

    private StoryDocSummaryDTO getStoryDocSummary(StoryDocId storyDocId) {
        return storyDocQueryService.getStoryDocSummary(storyDocId);
    }

    private List<ComponentDescriptorDTO> palette;

    @Override
    public List<ComponentDescriptorDTO> getComponentPalette() {
        if (palette == null) {
            palette = new ArrayList<>();
            palette.add(ComponentDescriptorDTO.builder()
                .type("BUTTON")
                .label("Button")
                .attributes(List.of(
                    xPos(),
                    yPos(),
                    height(),
                    width(),
                    value()
                ))
                .build());
            palette.add(ComponentDescriptorDTO.builder()
                .type("TEXT")
                .label("Text")
                .attributes(List.of(
                    xPos(),
                    yPos(),
                    height(),
                    width(),
                    value()
                ))
                .build());
            palette.add(ComponentDescriptorDTO.builder()
                .type("IMAGE")
                .label("Image")
                .attributes(List.of(
                    xPos(),
                    yPos(),
                    height(),
                    width()
                ))
                .build());
        }
        return palette;
    }

    private ComponentAttributeDescriptorDTO xPos() {
        return ComponentAttributeDescriptorDTO.builder()
            .name("x")
            .type(NUMBER)
            .build();
    }

    private ComponentAttributeDescriptorDTO yPos() {
        return ComponentAttributeDescriptorDTO.builder()
                .name("y")
                .type(NUMBER)
                .build();
    }

    private ComponentAttributeDescriptorDTO width() {
        return ComponentAttributeDescriptorDTO.builder()
                .name("width")
                .type(NUMBER)
                .build();
    }

    private ComponentAttributeDescriptorDTO height() {
        return ComponentAttributeDescriptorDTO.builder()
                .name("height")
                .type(NUMBER)
                .build();
    }

    private ComponentAttributeDescriptorDTO value() {
        return ComponentAttributeDescriptorDTO.builder()
                .name("value")
                .type(STRING)
                .build();
    }

    @Override
    public ComponentDescriptorDTO getDescriptor(String type) {
        return getComponentPalette().stream()
            .filter(desc -> desc.getType().equals(type))
            .findFirst()
            .get();
    }
}
