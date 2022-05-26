package io.storydoc.blueprint;

import io.storydoc.blueprint.classification.Classification;
import io.storydoc.blueprint.element.BluePrintElement;
import io.storydoc.blueprint.element.CompositeBluePrintElement;
import io.storydoc.blueprint.layout.Layout;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class BluePrint extends CompositeBluePrintElement {

    private List<BluePrintElement> elements;

    @Builder()
    public BluePrint(String name, Layout layout, List<BluePrintElement> elements, Classification classifier) {
        super("BluePrint", name, layout, elements, classifier);
        this.elements = elements;
    }
}
