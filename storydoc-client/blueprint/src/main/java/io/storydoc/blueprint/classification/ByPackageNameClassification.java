package io.storydoc.blueprint.classification;

import lombok.Getter;

@Getter
public class ByPackageNameClassification extends Classification {

    private String packageName;

    public ByPackageNameClassification(String packageName) {
        super(ByPackageNameClassification.class.getSimpleName());
        this.packageName = packageName;
    }

    @Override
    public boolean accepts(Class aClass) {
        return aClass.getPackageName().startsWith(packageName);
    }

    static public ByPackageNameClassification packageNameStartsWith(String packageName) {
        return new ByPackageNameClassification(packageName);
    }

}
