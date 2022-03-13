package io.storydoc.server.ui.infra.json.screendesign;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Container extends AbstractComponent {
    private List<AbstractComponent> children;
}
