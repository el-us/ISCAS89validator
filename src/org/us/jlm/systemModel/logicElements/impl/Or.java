package org.us.jlm.systemModel.logicElements.impl;

import org.us.jlm.systemModel.logicElements.LogicElement;
import org.us.jlm.systemModel.logicElements.LogicElementsType;

import java.util.ArrayList;
import java.util.List;

public class Or implements LogicElement {

    private final String label;
    private  List<String> inputLabels;
    private  List<LogicElement> inputs;

    public Or(String label, List<String> inputsLabels) {
        this.label = label;
        this.inputLabels = inputsLabels;
        this.inputs = new ArrayList<>();
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public List<LogicElement> getInputs() {
        return inputs;
    }

    @Override
    public String getType() {
        return LogicElementsType.OR;
    }

    @Override
    public List<String> getInputLabels() {
        return inputLabels;
    }

    @Override
    public String toString() {
        return "Or{" +
                "label='" + label + '\'' +
                ", inputs=" + inputs +
                '}';
    }
}
