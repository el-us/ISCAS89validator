package org.elita.jlm.logicElements.impl;

import org.elita.jlm.logicElements.LogicElement;
import org.elita.jlm.logicElements.LogicElementsData;

import java.util.List;

public class Dff implements LogicElement {

    private final String label;
    private List<String>  inputLabels;
    private List<LogicElement> inputs;

    public Dff(String label, List<String> inputLabels) {
        this.label = label;
        this.inputLabels = inputLabels;
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
        return LogicElementsData.DFF;
    }

    @Override
    public List<String> getInputLabels() {
        return inputLabels;
    }
}
