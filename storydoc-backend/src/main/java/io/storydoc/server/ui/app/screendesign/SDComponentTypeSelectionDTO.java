package io.storydoc.server.ui.app.screendesign;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SDComponentTypeSelectionDTO {
    private SDContainerDTO container;
    private SDComponentDTO component;
}
