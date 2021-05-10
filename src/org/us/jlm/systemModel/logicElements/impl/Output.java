package org.us.jlm.systemModel.logicElements.impl;

import org.us.jlm.systemModel.logicElements.LogicElement;
import org.us.jlm.systemModel.logicElements.LogicElementsType;

import java.util.ArrayList;
import java.util.List;

public class Output implements LogicElement {

    private final String label;
    private List<String> inputLabels;
    private List<LogicElement> inputs;

    public Output(String label) {
        this.label = label;
        this.inputs = new ArrayList<>();
        this.inputLabels = new ArrayList<>();
    }

    public void setInput(LogicElement logicElement) {
        this.inputs = List.of(logicElement);
    }

    public void setInputLabel(String inputLabel) {
        this.inputLabels = List.of(inputLabel);
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
        return LogicElementsType.OUTPUT;
    }

    @Override
    public List<String> getInputLabels() {
        return inputLabels;
    }
}
