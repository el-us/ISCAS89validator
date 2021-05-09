package org.elita.jlm.validator;

import org.elita.jlm.systemModel.SystemModel;
import org.elita.jlm.systemModel.logicElements.LogicElement;
import org.elita.jlm.systemModel.logicElements.LogicElementsType;
import org.elita.jlm.systemModel.logicElements.impl.Output;

import java.util.Objects;

public class ModelValidator {

    SystemModel systemModel;

    public ModelValidator(final SystemModel systemModel) {
        this.systemModel = systemModel;
    }


    public boolean validateModel() {
        if (AnyInputMapped()
                && AnyOutputMapped()
                && checkIfAllOutputLinked()) {
            System.out.println("System is valid");
            return true;
        } else {
            System.out.println("System is not valid");
            return false;
        }
    }

    private boolean AnyInputMapped() {
        return systemModel.getLogicElementsByType(LogicElementsType.INPUT).size() > 0;
    }

    private boolean AnyOutputMapped() {
        return systemModel.getLogicElementsByType(LogicElementsType.INPUT).size() > 0;
    }

    private boolean checkIfAllOutputLinked() {
        return systemModel.getLogicElementsByType(LogicElementsType.OUTPUT).stream()
                .map(logicElement -> (Output) logicElement)
                .filter(this::checkIfOutputLinked)
                .allMatch(Objects::nonNull);
    }

    @SuppressWarnings("ConstantConditions")
    private boolean checkIfOutputLinked(final Output output) {
       return systemModel.getLogicElements().stream()
                .filter(logicElement -> output.getInputs().get(0).equals(logicElement))
                .anyMatch(Objects::nonNull);
    }

    @SuppressWarnings("ConstantConditions")
    private boolean checkIfAllElementInputsLinked() {
        return systemModel.getLogicElements().stream()
                .filter(this::checkIfElementInputsLinked)
                .allMatch(Objects::nonNull);
    }

    private boolean checkIfElementInputsLinked(LogicElement logicElement) {
        return logicElement.getInputs().stream()
                .filter(this::checkIfElementGivenInputLinked)
                .allMatch(Objects::nonNull);
    }

    @SuppressWarnings("ConstantConditions")
    private boolean checkIfElementGivenInputLinked(LogicElement logicElementGivenInput) {
        return systemModel.getLogicElements().stream()
                .filter(logicElement -> logicElement.equals(logicElementGivenInput))
                .anyMatch(Objects::nonNull);
    }
}
