package org.elita.jlm.logicElements.impl;

import org.elita.jlm.logicElements.LogicElement;
import org.elita.jlm.logicElements.LogicElementsData;

import java.util.List;

public class Xor implements LogicElement {

    private final String label;
    private final List<LogicElement> inputs;

    public Xor(String label, List<LogicElement> inputs) {
        this.label = label;
        this.inputs = inputs;
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
        return LogicElementsData.XOR_TYPE;
    }
}
