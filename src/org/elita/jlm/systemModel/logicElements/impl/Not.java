package org.elita.jlm.systemModel.logicElements.impl;

import org.elita.jlm.systemModel.logicElements.LogicElement;
import org.elita.jlm.systemModel.logicElements.LogicElementsType;

import java.util.ArrayList;
import java.util.List;

public class Not implements LogicElement {

    private final String label;
    private List<String> inputLabels;
    private List<LogicElement> inputs;

    public Not(String label, List<String> inputLabels) {
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
