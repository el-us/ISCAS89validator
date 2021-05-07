package org.elita.jlm.logicElements.impl;

import org.elita.jlm.logicElements.LogicElement;
import org.elita.jlm.logicElements.LogicElementsData;

import java.util.List;

public class Or implements LogicElement {

    private final String label;
    private  List<String> inputLabels;
    private  List<LogicElement> inputs;

    public Or(String label, List<String> inputsLabels) {
        this.label = label;
        this.inputLabels = inputsLabels;
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
        return LogicElementsData.OR;
    }

    @Override
    public List<String> getInputLabels() {
        return inputLabels;
    }
}
