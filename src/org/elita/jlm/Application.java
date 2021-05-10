package org.elita.jlm;

import org.elita.jlm.mapper.IscasCodeMapper;
import org.elita.jlm.systemModel.SystemModel;
import org.elita.jlm.validator.*;

public class Application {

    public static void main(String ... args) {

        IscasCodeMapper iscasCodeMapper = new IscasCodeMapper();
        SystemModel systemModel = iscasCodeMapper.mapIscasCode("s27error.bench");

        systemModel.getLogicElements()
                .forEach(element -> System.out.println(element.getLabel() + ": " + element.getType() + " inputs: " + element.getInputLabels()));

        ModelValidator modelValidator = new ModelValidator(systemModel);
        modelValidator.validateModel();

    }
}
