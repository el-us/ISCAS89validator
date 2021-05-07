package org.elita.jlm.logicElements.impl;

import org.elita.jlm.logicElements.LogicElement;
import org.elita.jlm.logicElements.LogicElementsData;

import java.util.List;

public class Dff implements LogicElement {

    private final String label;
    private String  inputLabel;
    private LogicElement input;

    public Dff(String label, String inputLabel) {
        this.label = label;
        this.inputLabel = inputLabel;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public List<LogicElement> getInputs() {
        return List.of(input);
    }

    @Override
    public String getType() {
        return LogicElementsData.DFF;
    }

    @Override
    public List<String> getInputLabels() {
        return List.of(inputLabel);
    }
}
