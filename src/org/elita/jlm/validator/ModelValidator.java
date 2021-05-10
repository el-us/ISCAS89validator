package org.elita.jlm.validator;

import org.elita.jlm.systemModel.SystemModel;
import org.elita.jlm.systemModel.logicElements.LogicElement;
import org.elita.jlm.systemModel.logicElements.LogicElementsType;
import org.elita.jlm.systemModel.logicElements.impl.Output;

import java.util.List;

public class ModelValidator {

    SystemModel systemModel;

    public ModelValidator(final SystemModel systemModel) {
        this.systemModel = systemModel;
    }


    public boolean validateModel() {

        System.out.println("\nValidation started...\n");

        if(anyInputMapped()) {
            System.out.println("System has at least one input");
            systemModel.getErrorFlags().setNoInputs(false);
        } else {
            System.out.println("Error. No input mapped!");
            systemModel.getErrorFlags().setNoInputs(true);
        }

        if(anyOutputMapped()) {
            System.out.println("System has at least one output");
            systemModel.getErrorFlags().setNoOutputs(false);

            if(checkIfAllOutputLinked()) {
                System.out.println("All mapped output are linked");
                systemModel.getErrorFlags().setNotAllOutputLinked(false);
            } else {
                System.out.println("Error. Not all output linked!");
                systemModel.getErrorFlags().setNotAllOutputLinked(true);
            }

        } else {
            System.out.println("Error. No output mapped!");
            systemModel.getErrorFlags().setNoOutputs(true);
        }

        if(checkIfAllElementInputsLinked()) {
            System.out.println("All mapped logic gates inputs linked");
            systemModel.getErrorFlags().setNotElementInputsLinked(false);
        } else {
            System.out.println("Error. Not all logic gates inputs linked");
            systemModel.getErrorFlags().setNotElementInputsLinked(true);
        }

        if(checkIfAllGatesLinked()) {
            System.out.println("All mapped logic gates are linked");
            systemModel.getErrorFlags().setNotAllGatesLinked(false);
        } else {
            System.out.println("Error. Not all logic gates linked");
            systemModel.getErrorFlags().setNotAllGatesLinked(true);
        }

        if (systemModel.getErrorFlags().isSystemValid()) {
            System.out.println("\nSystem is valid");
            return true;
        } else {
            System.out.println();
            System.out.println("*******************************************************");
            System.out.println("Error. Some errors during mapping and linking occurred." +
                    "\nSystem is not valid. Please check your ISCAS'89 file");
            System.out.println("*******************************************************");
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

    private boolean checkIfElementInputsLinked(final LogicElement logicElement) {
        return logicElement.getInputs().stream()
                .allMatch(this::checkIfElementGivenInputLinked);
    }

    private boolean checkIfElementGivenInputLinked(final LogicElement logicElementGivenInput) {
        return systemModel.getLogicElements().stream()
                .anyMatch(logicElement -> logicElement.equals(logicElementGivenInput));
    }

    // FIXME not working properly
    private boolean checkIfAllGatesLinked() {
        return systemModel.getLogicElements().stream()
                .filter(element -> !element.getType().equals(LogicElementsType.OUTPUT))
                .allMatch(this::checkIfElementLinkedToAnyInput);
    }

    private boolean checkIfElementLinkedToAnyInput(final LogicElement logicElement) {
        return systemModel.getLogicElements().stream()
                .filter(element -> !element.getType().equals(LogicElementsType.INPUT))
                .map(LogicElement::getInputs)
                .anyMatch(inputs -> checkIfElementLinkedToGivenElementInput(inputs, logicElement));
    }

    private boolean checkIfElementLinkedToGivenElementInput(final List<LogicElement> inputs, LogicElement logicElement) {
        if(inputs.size() > 0) {
            return inputs.stream()
                    .anyMatch(input -> input.equals(logicElement));
        } else return false;
    }
}
