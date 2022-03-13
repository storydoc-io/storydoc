package io.storydoc.server.ui.infra.json.screendesign;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(Container.class),
        @JsonSubTypes.Type(Component.class),
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbstractComponent {
    private String name;
    private String id;
    private int x;
    private int y;
}
