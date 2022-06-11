package io.storydoc.blueprint.classification;

import lombok.Getter;

@Getter
public class ByClassNameClassification extends Classification {

    private String[] classNames;

    public ByClassNameClassification(String[] classNames) {
        super(ByClassNameClassification.class.getSimpleName());
        this.classNames = classNames;
    }

    @Override
    public boolean accepts(Class aClass) {

        for(String className: classNames) {
            if (aClass.getName().endsWith(className)) return true;
        }
        return false;
    }

    static public ByClassNameClassification hasClassNameEndingWith(String ... classNames) {
        return new ByClassNameClassification(classNames);
    }

}
