package io.storydoc.server.ui.app.screendesign;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComponentDescriptorDTO {
    private String label;
    private String type;
    List<ComponentAttributeDescriptorDTO> attributes;
}
