package io.storydoc.canon;

import java.util.HashMap;
import java.util.Map;

public class ExecutionContext {

    private Map<String, Object> variables = new HashMap<>();

    public void setVariable(String variableName, Object value) {
        System.out.println("setting " + variableName + " to " + value);
        variables.put(variableName, value);

    }

     public Object getVariable(String variableName) {
        return variables.get(variableName);
    }
}
