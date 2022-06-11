package io.storydoc.blueprint.element;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.storydoc.blueprint.BluePrint;
import io.storydoc.blueprint.classification.Classification;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property="type", include = JsonTypeInfo.As.EXISTING_PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(BluePrint.class),
        @JsonSubTypes.Type(CompositeBluePrintElement.class),
        @JsonSubTypes.Type(Role.class),
})
@AllArgsConstructor
@Getter
abstract public class BluePrintElement {

    private String type;

    private String name;

    private Classification classification;

}
