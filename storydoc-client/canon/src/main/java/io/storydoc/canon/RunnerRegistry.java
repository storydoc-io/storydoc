package io.storydoc.canon;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RunnerRegistry {

    private Map<Class<? extends  StepDef>, Runner> runnersByClass = new HashMap<>();

    public Runner get(Class<? extends StepDef> aClass) {
        return runnersByClass.get(aClass);
    }

    public void register(Class<? extends StepDef> aClass, Runner runner) {
        runnersByClass.put(aClass, runner);
    }
}
