package io.storydoc.server.ui.app.screendesign;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComponentAttributeDescriptorDTO {
    private String name;
    private ComponentAttributeType type;
}
