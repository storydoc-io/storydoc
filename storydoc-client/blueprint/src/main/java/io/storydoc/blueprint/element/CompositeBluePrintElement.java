package io.storydoc.blueprint.element;

import io.storydoc.blueprint.classification.Classification;
import io.storydoc.blueprint.layout.FlexLayout;
import io.storydoc.blueprint.layout.Layout;
import lombok.Getter;

import java.util.List;

@Getter
abstract public class CompositeBluePrintElement extends BluePrintElement {

    public CompositeBluePrintElement(String type, String name, Layout layout, List<BluePrintElement> subElements, Classification classifier) {
        super(type, name, classifier);
        this.layout = layout;
        this.subElements = subElements;
    }

    private Layout layout = new FlexLayout();

    private List<BluePrintElement> subElements;

}
