package org.elita.jlm.mapper;


import org.elita.jlm.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.elita.jlm.IscasFileReader.readIscasFile;

public class IscasCodeMapper {

    private static final String INPUT = "INPUT";
    private static final String OUTPUT = "OUTPUT";
    private final SystemModel systemModel = new SystemModel();
    private List<String> iscasCodelineList;
    private int currentlineLine;


    public void mapIscasCode(String fileName) {
        iscasCodelineList = readIscasFile(fileName);
        iscasCodelineList.stream()
                .map(this::skipEmptyLines)
                .filter(Objects::nonNull)
                .map(this::removeInnerSpaces)
                .filter(this::ignoreHashedLines)
                .map(this::mapInputs)
                .map(this::mapOutputs)
                .map(this::removeInnerSpaces);
    }


    private String skipEmptyLines(final String line) {
        int charIndex = 0;
        while (line.charAt(charIndex) == ' ') {
            charIndex++;
        }
        return charIndex < line.length() - 1 ? line.substring(charIndex) : null;
    }

    private Boolean ignoreHashedLines(final String line) {
        return !line.startsWith("#");

    }

    private String mapInputs(final String line) {
        List<String> splitedInputDeclaration = Arrays.asList(line.split("[(]|[)]"));
        splitedInputDeclaration.get(0);
        if(splitedInputDeclaration.get(0).equals(INPUT)) {
            systemModel.getInputList().add(splitedInputDeclaration.get(1));
        } else {
            systemModel.getErrorFlags().setNoInputs(true);
        }
        return line;
    }

    private String mapOutputs(final String line) {
        List<String> splitedInputDeclaration = Arrays.asList(line.split("[(]|[)]"));
        splitedInputDeclaration.get(0);
        if(splitedInputDeclaration.get(0).equals(OUTPUT)) {
            systemModel.getOutputList().add(splitedInputDeclaration.get(1));
        } else {
            systemModel.getErrorFlags().setNoOutputs(true);
        }
        return line;
    }

    private String removeInnerSpaces(final String line) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            if(line.charAt(i) != ' ') {
                stringBuilder.append(line.charAt(i));
            }
        }
        return stringBuilder.toString();
    }
}
