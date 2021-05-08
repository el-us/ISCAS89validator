package org.elita.jlm.logicElements.impl;

import org.elita.jlm.logicElements.LogicElement;
import org.elita.jlm.logicElements.LogicElementsType;

import java.util.ArrayList;
import java.util.List;

public class Dff implements LogicElement {

    private final String label;
    private List<String>  inputLabels;
    private List<LogicElement> inputs;

    public Dff(String label, List<String> inputLabels) {
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
        return LogicElementsType.DFF;
    }

    @Override
    public List<String> getInputLabels() {
        return inputLabels;
    }
}
