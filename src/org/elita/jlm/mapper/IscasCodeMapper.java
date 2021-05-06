package org.elita.jlm.mapper;


import org.elita.jlm.SystemModel;
import org.elita.jlm.logicElements.LogicElement;
import org.elita.jlm.logicElements.LogicElementsData;
import org.elita.jlm.logicElements.impl.And;
import org.elita.jlm.logicElements.impl.Input;
import org.elita.jlm.logicElements.impl.Output;

import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.elita.jlm.mapper.IscasFileReader.readIscasFile;

public class IscasCodeMapper {

    private static final String INPUT = "INPUT";
    private static final String OUTPUT = "OUTPUT";
    private static final String SPACE = " ";
    private static final String HASH = "#";
    private static final String END_OF_LINE = "\n";
    private final SystemModel systemModel = new SystemModel();


    public SystemModel mapIscasCode(String fileName) {
        List<String> iscasCodelineList = readIscasFile(fileName);

        long numberOfParsedLines = iscasCodelineList.stream()
                .map(this::removeSpaces)
                .filter(this::skipEmptyLines)
                .filter(this::ignoreHashedLines)
                .map(this::mapInputs)
                .map(this::mapOutputs)
                .count();

        System.out.println("Successfully parsed " + numberOfParsedLines + " logic elements from file: " + fileName);

        return systemModel;
    }


    private String removeSpaces(final String line) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) != ' ') {
                stringBuilder.append(line.charAt(i));
            }
        }
        return stringBuilder.toString();
    }

    private Boolean skipEmptyLines(final String line) {
        return !line.isBlank();
    }

    private Boolean ignoreHashedLines(final String line) {
        return !line.startsWith(HASH);
    }

    private String mapInputs(final String line) {
        List<String> splittedInputDeclaration = Arrays.asList(line.split("[(]|[)]"));
        if (splittedInputDeclaration.get(0).equals(INPUT)) {
            Input input = new Input(splittedInputDeclaration.get(1));
            systemModel.getLogicElements().add(input);
        }
        return line;
    }

    private String mapOutputs(final String line) {
        List<String> splittedOutputDeclaration = Arrays.asList(line.split("[(]|[)]"));
        if (splittedOutputDeclaration.get(0).equals(OUTPUT)) {
            Output output = new Output(splittedOutputDeclaration.get(1));
            systemModel.getLogicElements().add(output);
        }
        return line;
    }

    private String mapLogicGates(final String line) {
        List<String> splittedGatesDeclaration = Arrays.asList(line.split("[(]|[)]|[=]|[,]"));
        if (isNotInputAndOutput(splittedGatesDeclaration)) {

        }
    }

    private boolean isNotInputAndOutput(List<String> splittedGatesDeclaration) {
        return !splittedGatesDeclaration.get(0).equals(INPUT) || !splittedGatesDeclaration.get(0).equals(OUTPUT);
    }

    private static String mapLogicGates(final List<String> splittedGatesDeclaration) {
        String gateLabel = splittedGatesDeclaration.get(0);
        String gateType = splittedGatesDeclaration.get(1);
        List<LogicElement> =

        switch (gateType) {
            case LogicElementsData.AND_TYPE:
                And and = new And(gateLabel, );
        }
    }

    private List<LogicElement> getInputElements(List<String> splittedGatesDeclaration) {
        List<String> gatesInputStrings = extractGateInputsStrings(splittedGatesDeclaration);
        List<LogicElement> gateInputs;
        gatesInputStrings.stream()
                .map(gateInputString -> systemModel.getLogicElements().stream()
                        .filter(logicElement -> logicElement.getLabel().equals(gateInputString)))

    }

    private List<String> extractGateInputsStrings(List<String> splittedGatesDeclaration) {
        List<String> gateInputStrings = new ArrayList<>(splittedGatesDeclaration);
        gateInputStrings.remove(0);
        gateInputStrings.remove(1);
        return gateInputStrings;
    }
}
