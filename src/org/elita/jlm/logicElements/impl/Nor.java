package org.elita.jlm.logicElements.impl;

import org.elita.jlm.logicElements.LogicElement;
import org.elita.jlm.logicElements.LogicElementsData;

import java.util.ArrayList;
import java.util.List;

public class Nor implements LogicElement {

    private final String label;
    private  List<String> inputLabels;
    private  List<LogicElement> inputs;

    public Nor(String label, List<String> inputsLabels) {
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
        return LogicElementsData.NOR;
    }

    @Override
    public List<String> getInputLabels() {
        return inputLabels;
    }
}
