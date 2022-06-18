package io.storydoc.blueprint.classification;

import lombok.Getter;

@Getter
public class AnyClassNameClassification extends Classification {

    public AnyClassNameClassification() {
        super(AnyClassNameClassification.class.getSimpleName());
    }

    @Override
    public boolean accepts(ClassInfo aClass) {
        return true;
    }

    static public AnyClassNameClassification anyClassName() {
        return new AnyClassNameClassification();
    }

}
