package org.elita.jlm;

import org.elita.jlm.logicElements.LogicElement;
import org.elita.jlm.mapper.IscasCodeMapper;

public class Main {

    public static void main(String ... args) {

        IscasCodeMapper iscasCodeMapper = new IscasCodeMapper();
        SystemModel systemModel = iscasCodeMapper.mapIscasCode("s27.bench");

        systemModel.getLogicElements().stream()
                .map(LogicElement::getLabel)
                .forEach(System.out::println);

    }
}
