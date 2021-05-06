package org.elita.jlm.logicElements.impl;

import org.elita.jlm.logicElements.LogicElement;
import org.elita.jlm.logicElements.LogicElementsData;

import java.util.List;

public class Not implements LogicElement {

    private final String label;
    private final LogicElement input;

    public Not(String label, LogicElement input) {
        this.label = label;
        this.input = input;
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
        return LogicElementsData.NOT_TYPE;
    }
}
