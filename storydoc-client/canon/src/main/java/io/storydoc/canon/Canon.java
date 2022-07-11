package io.storydoc.canon;

import java.util.ArrayList;
import java.util.List;

public class Canon {

    private List<StepDef> steps = new ArrayList<>();

    public void add(StepDef stepDef) {
        steps.add(stepDef);
    }

    public List<StepDef> getSteps() {
        return steps;
    }
}
