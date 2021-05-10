package org.us.jlm.systemModel.logicElements.impl;

import org.us.jlm.systemModel.logicElements.LogicElement;
import org.us.jlm.systemModel.logicElements.LogicElementsType;

import java.util.ArrayList;
import java.util.List;

public class And implements LogicElement {

    private final String label;
    private List<String> inputLabels;
    private List<LogicElement> inputs;

    public And(String label, List<String> inputLabels) {
        this.label = label;
        this.inputLabels = inputLabels;
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
        return LogicElementsType.AND;
    }

    @Override
    public List<String> getInputLabels() {
        return inputLabels;
    }
}
