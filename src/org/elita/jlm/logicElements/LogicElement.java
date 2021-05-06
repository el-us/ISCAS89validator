package org.elita.jlm.logicElements;

import java.util.List;

public interface LogicElement {

    String getType();
    String getLabel();
    List<LogicElement> getInputs();
}
