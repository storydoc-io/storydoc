package io.storydoc.stitch;

import lombok.Value;

import java.util.Stack;

public class JSONWriter {

    private StringBuffer stringBuffer = new StringBuffer();

    private enum Type {
        ARRAY,
        OBJECT,
    }

    @Value
    private class StackElem {
        Type type;
        int children;
    }

    Stack<StackElem> stack = new Stack<>();

    public void obj() {
        ifInArrayAddCommaIfNotFirstChild();
        stringBuffer.append("{ ");
        stack.push(new StackElem(Type.OBJECT, 0));
    }

    public void end() {
        StackElem stackElem = stack.pop();
        if (stackElem.type==Type.ARRAY) {
            stringBuffer.append(" ]");
        } else {
            stringBuffer.append(" }");
        }
    }

    public void att(String attKey) {
        addCommaIfNotFirstChild();
        stringBuffer.append("\"" + attKey + "\": ");
    }

    public void array() {
        stringBuffer.append("[ ");
        stack.push(new StackElem(Type.ARRAY, 0));
    }

    public String getValue() {
        return stringBuffer.toString();
    }

    public void string(String string) {
        ifInArrayAddCommaIfNotFirstChild();
        stringBuffer.append(String.format("\"%s\"", string));
    }

    private void ifInArrayAddCommaIfNotFirstChild() {
        if (!stack.empty() && stack.peek().type==Type.ARRAY) {
            addCommaIfNotFirstChild();
        }
    }

    private void addCommaIfNotFirstChild() {
        StackElem stackElem = stack.pop();
        if (stackElem.children>0) {
            stringBuffer.append(", ");
        }
        stack.push(new StackElem(stackElem.type, stackElem.children+1));
    }

    public void number(int number) {
        ifInArrayAddCommaIfNotFirstChild();
        stringBuffer.append(number);
    }

}
