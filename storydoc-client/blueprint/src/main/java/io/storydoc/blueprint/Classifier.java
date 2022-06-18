package io.storydoc.blueprint;

import io.storydoc.blueprint.classification.ClassInfo;
import io.storydoc.blueprint.element.BluePrintElement;
import io.storydoc.blueprint.element.CompositeBluePrintElement;

import java.util.ArrayList;
import java.util.List;

public class Classifier {

    private BluePrint bluePrint;

    public Classifier(BluePrint bluePrint) {
        this.bluePrint = bluePrint;
    }

    public List<String> classify(ClassInfo aClass) {
        List<String> classificationNames = new ArrayList<>();
        accepts(bluePrint, aClass, classificationNames);
        return classificationNames;

    }

    private boolean accepts(BluePrintElement bluePrintElement, ClassInfo aClass, List<String> classifications) {
        if (bluePrintElement.getClassification()==null) {
            throw new IllegalArgumentException("bluePrintElement has no classification: " + bluePrintElement.getName());
        }
        boolean accepted = bluePrintElement.getClassification().accepts(aClass);
        if (accepted) {
            classifications.add(bluePrintElement.getName());
            if (bluePrintElement instanceof CompositeBluePrintElement) {
                CompositeBluePrintElement compositeBluePrintElement = (CompositeBluePrintElement) bluePrintElement;
                for(BluePrintElement subElem: compositeBluePrintElement.getSubElements()) {
                    if (accepts(subElem, aClass, classifications)) break;
                }
            }
        }
        return accepted;
    }

}
