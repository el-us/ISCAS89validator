package org.elita.jlm.mapper;


import org.elita.jlm.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.elita.jlm.IscasFileReader.readIscasFile;

public class IscasCodeMapper {

    private static final String INPUT = "INPUT";
    private static final String OUTPUT = "OUTPUT";
    private static final String SPACE = " ";
    private static final String HASH = "#";
    private static final String END_OF_LINE = "\n";
    private final SystemModel systemModel = new SystemModel();
    private List<String> iscasCodelineList;
    private int currentlineLine;


    public void mapIscasCode(String fileName) {
        iscasCodelineList = readIscasFile(fileName);
        iscasCodelineList.stream()
                .map(this::removeSpaces)
                .filter(this::skipEmptyLines)
                .filter(this::ignoreHashedLines)
                .map(this::mapInputs)
                .map(this::mapOutputs)
                .map(this::removeSpaces);
    }


    private Boolean skipEmptyLines(final String line) {
        return !line.isBlank();
    }

    private Boolean ignoreHashedLines(final String line) {
        return !line.startsWith(HASH);
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
        List<String> splitedOutputDeclaration = Arrays.asList(line.split("[(]|[)]"));
        splitedOutputDeclaration.get(0);
        if(splitedOutputDeclaration.get(0).equals(OUTPUT)) {
            systemModel.getOutputList().add(splitedOutputDeclaration.get(1));
        } else {
            systemModel.getErrorFlags().setNoOutputs(true);
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
