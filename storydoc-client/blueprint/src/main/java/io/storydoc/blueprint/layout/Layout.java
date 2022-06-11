package io.storydoc.blueprint.layout;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", include = JsonTypeInfo.As.EXISTING_PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(FlexLayout.class),
})
@Getter
abstract public class Layout {
    protected String type;

    public Layout(String type) {
        this.type = type;
    }


}
