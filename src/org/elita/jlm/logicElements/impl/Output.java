package org.elita.jlm.logicElements.impl;

import org.elita.jlm.logicElements.LogicElement;
import org.elita.jlm.logicElements.LogicElementsType;

import java.util.ArrayList;
import java.util.List;

public class Output implements LogicElement {

    private final String label;
    private List<LogicElement> inputs;

    public Output(String label) {
        this.label = label;
        this.inputs = new ArrayList<>();
    }

    public void setInput(LogicElement logicElement) {
        this.inputs = List.of(logicElement);
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
        return null;
    }
}
