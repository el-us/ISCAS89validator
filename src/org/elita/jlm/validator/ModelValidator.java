package org.elita.jlm.validator;

import org.elita.jlm.systemModel.SystemModel;
import org.elita.jlm.systemModel.logicElements.LogicElement;
import org.elita.jlm.systemModel.logicElements.LogicElementsType;
import org.elita.jlm.systemModel.logicElements.impl.Output;

public class ModelValidator {

    SystemModel systemModel;

    public ModelValidator(final SystemModel systemModel) {
        this.systemModel = systemModel;
    }


    public boolean validateModel() {
        if (AnyInputMapped()
                && anyOutputMapped()
                && anyInputMapped()
                && checkIfAllOutputLinked()
                && checkIfAllElementInputsLinked()) {
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

    private boolean anyOutputMapped() {
        return systemModel.getLogicElementsByType(LogicElementsType.OUTPUT).size() > 0;
    }

    private boolean anyInputMapped() {
        return systemModel.getLogicElementsByType(LogicElementsType.INPUT).size() > 0;
    }

    private boolean checkIfAllOutputLinked() {
        return systemModel.getLogicElementsByType(LogicElementsType.OUTPUT).stream()
                .map(logicElement -> (Output) logicElement)
                .allMatch(this::checkIfOutputLinked);
    }

    private boolean checkIfOutputLinked(final Output output) {
        if(output.getInputs().size() > 0) {
        return systemModel.getLogicElements().stream()
                        .anyMatch(logicElement -> output.getInputs().get(0).equals(logicElement));
        } else return false;
    }

    private boolean checkIfAllElementInputsLinked() {
        return systemModel.getLogicElements().stream()
                .filter(logicElement -> !logicElement.getType().equals(LogicElementsType.INPUT))
                .allMatch(this::checkIfElementInputsLinked);
    }

    private boolean checkIfElementInputsLinked(LogicElement logicElement) {
        return logicElement.getInputs().stream()
                .allMatch(this::checkIfElementGivenInputLinked);
    }

    private boolean checkIfElementGivenInputLinked(LogicElement logicElementGivenInput) {
        return systemModel.getLogicElements().stream()
                .anyMatch(logicElement -> logicElement.equals(logicElementGivenInput));
    }
}
