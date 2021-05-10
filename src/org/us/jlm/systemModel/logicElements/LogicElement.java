package org.us.jlm.systemModel.logicElements;

import java.util.List;

public interface LogicElement {
    String getType();
    String getLabel();
    List<LogicElement> getInputs();
    List<String> getInputLabels();
}
