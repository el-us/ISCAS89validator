package org.us.jlm.systemModel;

import org.us.jlm.systemModel.logicElements.LogicElement;
import org.us.jlm.systemModel.logicElements.LogicElementsType;
import org.us.jlm.systemModel.logicElements.impl.Output;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SystemModel {

    private List<LogicElement> logicElements;
    private ErrorFlags errorFlags;

    public SystemModel() {
        this.logicElements = new ArrayList<>();
        this.errorFlags = new ErrorFlags();
    }

    public List<LogicElement> getLogicElements() {
        return logicElements;
    }

    public void setLogicElements(List<LogicElement> logicElements) {
        this.logicElements = logicElements;
    }

    public ErrorFlags getErrorFlags() {
        return errorFlags;
    }

    public void setErrorFlags(ErrorFlags errorFlags) {
        this.errorFlags = errorFlags;
    }

    public LogicElement getLogicElementByLabel(final String label) {
        return logicElements.stream()
                .filter(logicElement -> logicElement.getLabel().equals(label))
                .findFirst()
                .orElse(null);
    }

    public List<LogicElement> getLogicElementsByType(final String type) {
        return logicElements.stream()
                .filter(logicElement -> logicElement.getType().equals(type))
                .collect(Collectors.toList());
    }

    public Output getOutputByLabel(final String label) {
        List<LogicElement> inputs = getLogicElementsByType(LogicElementsType.OUTPUT);
        //noinspection SimplifyOptionalCallChains
        return inputs.stream()
                .filter(output -> output.getLabel().equals(label))
                .findFirst()
                .map(output -> (Output) output)
                .orElse(null);
    }

    public List<LogicElement> getLogicElementsWithExcludedType(final String excludedType) {
        return logicElements.stream()
                .filter(logicElement -> !logicElement.getType().equals(excludedType))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return logicElements.toString();
    }
}
