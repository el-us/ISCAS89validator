package org.elita.jlm.logicElements.impl;

import org.elita.jlm.logicElements.LogicElement;
import org.elita.jlm.logicElements.LogicElementsData;

import java.util.List;

public class Not implements LogicElement {

    private final String label;
    private String  inputLabel;
    private LogicElement input;

    public Not(String label, String inputLabel) {
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
        return LogicElementsData.NOT;
    }

    @Override
    public List<String> getInputLabels() {
        return List.of(inputLabel);
    }
}
