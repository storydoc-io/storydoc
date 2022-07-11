package io.storydoc.canon;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Interpreter {

    private final RunnerRegistry runnerRegistry;

    public Interpreter(RunnerRegistry runnerRegistry) {
        this.runnerRegistry = runnerRegistry;
    }

    @SneakyThrows
    public void run(Canon canon) {
        ExecutionContext context = new ExecutionContext();
        for(StepDef step: canon.getSteps()) {
            Runner runner = runnerRegistry.get(step.getClass());
            if (runner == null) {
                log.info("no runenr for class: " + step.getClass());
            }
            runnerRegistry.get(step.getClass()).run(step, context);
        }
    }

}
