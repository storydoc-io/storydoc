package io.storydoc.blueprint.classification;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", include = JsonTypeInfo.As.EXISTING_PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(ByPackageNameClassification.class),
        @JsonSubTypes.Type(ByClassNameClassification.class),
})
@Getter
abstract public class Classification {

    private String type;

    public Classification(String type) {
        this.type = type;
    }

    abstract public boolean accepts(ClassInfo aClass);
}
