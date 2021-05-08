package org.elita.jlm.logicElements.impl;

import org.elita.jlm.logicElements.LogicElement;
import org.elita.jlm.logicElements.LogicElementsType;

import java.util.List;

public class Input implements LogicElement {

    private final String label;

    public Input(String label) {
        this.label = label;
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
        return LogicElementsType.INPUT;
    }

    @Override
    public List<String> getInputLabels() {
        return null;
    }
}
