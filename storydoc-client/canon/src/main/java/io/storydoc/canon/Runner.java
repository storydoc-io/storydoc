package io.storydoc.canon;

@FunctionalInterface
public interface Runner<T extends StepDef> {

    public void run(T stepDef, ExecutionContext context);

}
