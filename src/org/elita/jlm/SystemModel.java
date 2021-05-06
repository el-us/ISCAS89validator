package org.elita.jlm;

import org.elita.jlm.logicElements.LogicElement;

import java.util.ArrayList;
import java.util.List;

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
}
