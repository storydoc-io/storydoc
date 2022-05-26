package io.storydoc.blueprint.element;

import io.storydoc.blueprint.classification.Classification;
import lombok.Builder;
import lombok.Data;

@Data
public class Role extends BluePrintElement{

    @Builder
    public Role(String name, Classification classifier) {
        super("Role", name, classifier);
    }

}
