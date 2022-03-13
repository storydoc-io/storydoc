package io.storydoc.server.ui.infra.json.screendesign;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Component extends AbstractComponent {
    private String type;
}
