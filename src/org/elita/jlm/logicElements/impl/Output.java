package org.elita.jlm.logicElements.impl;

import org.elita.jlm.logicElements.LogicElement;
import org.elita.jlm.logicElements.LogicElementsData;

import java.util.ArrayList;
import java.util.List;

public class Output implements LogicElement {

    private final String label;
    private List<String> inputLabels;
    private LogicElement input;

    public Output(String label) {
        this.label = label;
        this.inputLabels = new ArrayList<>();
    }

    public void setInput(LogicElement input) {
        this.input = input;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public List<LogicElement> getInputs() {
        return null;
    }

    @Override
    public String getType() {
        return LogicElementsData.OUTPUT;
    }
    @Override
    public List<String> getInputLabels() {
        return inputLabels;
    }
}
