package org.us.jlm;

import org.us.jlm.mapper.IscasCodeMapper;
import org.us.jlm.systemModel.SystemModel;
import org.us.jlm.validator.*;

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
