package org.elita.jlm.validator;

import org.elita.jlm.systemModel.SystemModel;
import org.elita.jlm.systemModel.logicElements.LogicElementsType;
import org.elita.jlm.systemModel.logicElements.impl.Output;

import java.util.Objects;

public class modelValidator {

    public static boolean checkIfAnyInputMapped(final SystemModel systemModel) {
        return systemModel.getLogicElementsByType(LogicElementsType.INPUT).size() > 0;
    }

    public static boolean checkIfAnyOutputMapped(final SystemModel systemModel) {
        return systemModel.getLogicElementsByType(LogicElementsType.INPUT).size() > 0;
    }

    public static boolean checkIfAllOutputLinked(final SystemModel systemModel) {
        return systemModel.getLogicElementsByType(LogicElementsType.OUTPUT).stream()
                .map(logicElement -> (Output) logicElement)
                .filter(output -> checkIfOutputLinked(output, systemModel))
                .anyMatch(Objects::nonNull);
    }

    private static boolean checkIfOutputLinked(final Output output, final SystemModel systemModel) {
       return systemModel.getLogicElements().stream()
                .filter(logicElement -> output.getInputs().get(0).equals(logicElement))
                .anyMatch(Objects::nonNull);
    }
}
