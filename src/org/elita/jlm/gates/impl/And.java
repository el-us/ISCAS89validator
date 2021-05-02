package org.elita.jlm.gates.impl;

import org.elita.jlm.gates.Gate;

import java.util.List;

public class And implements Gate {

    private final String label;
    private final List<String> inputLabels;

    public And(String label, List<String> inputLabels) {
        this.label = label;
        this.inputLabels = inputLabels;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public List<String> getInputLabels() {
        return inputLabels;
    }
}
