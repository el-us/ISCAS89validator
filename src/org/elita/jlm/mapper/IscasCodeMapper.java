package org.elita.jlm.mapper;


import org.elita.jlm.*;
import org.elita.jlm.logicElements.impl.Input;
import org.elita.jlm.logicElements.impl.Output;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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


    private Boolean skipEmptyLines(final String line) {
        return !line.isBlank();
    }

    private Boolean ignoreHashedLines(final String line) {
        return !line.startsWith(HASH);
    }

    private String mapInputs(final String line) {
        List<String> splitedInputDeclaration = Arrays.asList(line.split("[(]|[)]"));
        if(splitedInputDeclaration.get(0).equals(INPUT)) {
            Input input = new Input(splitedInputDeclaration.get(1));
            systemModel.getLogicElements().add(input);
        }
        return line;
    }

    private String mapOutputs(final String line) {
        List<String> splitedOutputDeclaration = Arrays.asList(line.split("[(]|[)]"));
        if(splitedOutputDeclaration.get(0).equals(OUTPUT)) {
            Output output = new Output(splitedOutputDeclaration.get(1));
            systemModel.getLogicElements().add(output);
        }
        return line;
    }

    private String removeSpaces(final String line) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            if(line.charAt(i) != ' ') {
                stringBuilder.append(line.charAt(i));
            }
        }
        return stringBuilder.toString();
    }
}
