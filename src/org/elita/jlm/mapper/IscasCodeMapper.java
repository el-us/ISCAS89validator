package org.elita.jlm.mapper;


import org.elita.jlm.SystemModel;
import org.elita.jlm.logicElements.LogicElement;
import org.elita.jlm.logicElements.LogicElementsData;
import org.elita.jlm.logicElements.impl.*;

import java.util.*;

import static org.elita.jlm.mapper.IscasFileReader.readIscasFile;

public class IscasCodeMapper {

    private static final String INPUT = "INPUT";
    private static final String OUTPUT = "OUTPUT";
    private static final String HASH = "#";
    private static final char SPACE = ' ';

    private final SystemModel systemModel = new SystemModel();


    public SystemModel mapIscasCode(String fileName) {
        List<String> iscasCodelineList = readIscasFile(fileName);

        long numberOfParsedLines = iscasCodelineList.stream()
                .map(this::removeSpaces)
                .filter(this::skipEmptyLines)
                .filter(this::ignoreHashedLines)
                .map(this::mapInputs)
                .filter(Objects::nonNull)
                .map(this::mapOutputs)
                .filter(Objects::nonNull)
                .map(this::mapLogicGate)
                .filter(aBoolean -> aBoolean)
                .count();

        System.out.println("Successfully parsed " + numberOfParsedLines + " logic elements from file: " + fileName);

        boolean allLinked = linkLogicElements();

        if(allLinked) {
            System.out.println("All element successfully linked");
        } else {
            System.out.println("Some errors during linking occurred");
        }


        return systemModel;
    }

    private String removeSpaces(final String line) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) != SPACE) {
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
            return null;
        }
        return line;
    }

    private String mapOutputs(final String line) {
        List<String> splittedOutputDeclaration = Arrays.asList(line.split("[(]|[)]"));
        if (splittedOutputDeclaration.get(0).equals(OUTPUT)) {
            Output output = new Output(splittedOutputDeclaration.get(1));
            systemModel.getLogicElements().add(output);
            return null;
        }
        return line;
    }

    private Boolean mapLogicGate(final String line) {
        List<String> splittedGateDeclaration = Arrays.asList(line.split("[(]|[)]|[=]|[,]"));
        if (isNotInputAndOutput(splittedGateDeclaration)) {
            return mapLogicGate(splittedGateDeclaration);
        }
        return null;
    }

    private boolean isNotInputAndOutput(List<String> splittedGatesDeclaration) {
        return !splittedGatesDeclaration.get(0).equals(INPUT) || !splittedGatesDeclaration.get(0).equals(OUTPUT);
    }

    private Boolean mapLogicGate(final List<String> splittedGateDeclaration) {
        String gateLabel = splittedGateDeclaration.get(0);
        String gateType = splittedGateDeclaration.get(1);
        List<String> inputLabels = extractGateInputsStrings(splittedGateDeclaration);

        switch (gateType) {
            case LogicElementsData.AND:
                systemModel.getLogicElements().add(new And(gateLabel, inputLabels));
                return true;
            case LogicElementsData.OR:
                systemModel.getLogicElements().add(new Or(gateLabel, inputLabels));
                return true;
            case LogicElementsData.NOT:
                systemModel.getLogicElements().add(new Not(gateLabel, inputLabels));
                return true;
            case LogicElementsData.XOR:
                systemModel.getLogicElements().add(new Xor(gateLabel, inputLabels));
                return true;
            case LogicElementsData.NAND:
                systemModel.getLogicElements().add(new Nand(gateLabel, inputLabels));
                return true;
            case LogicElementsData.NOR:
                systemModel.getLogicElements().add(new Nor(gateLabel, inputLabels));
                return true;
            case LogicElementsData.DFF:
                systemModel.getLogicElements().add(new Dff(gateLabel, inputLabels));
                return true;
            default:
                return false;
        }
    }

    private Boolean linkLogicElements() {
        return systemModel.getLogicElements().stream()
                .filter(this::isNotInput)
                .allMatch(this::linkLogicElement);
    }

    private boolean isNotInput(LogicElement logicElement) {
        return !logicElement.getType().equals(INPUT);
    }

    private Boolean linkLogicElement(LogicElement logicElement) {
        return Optional.ofNullable(logicElement.getInputLabels())
                .map(inputLabels -> inputLabels.stream()
                .allMatch(inputLabel -> linkElementInput(logicElement, inputLabel)))
                .orElse(handleInputLabelsNullPointer(logicElement));
    }

    private boolean isNotOutput(LogicElement logicElement) {
        return !logicElement.getType().equals(OUTPUT);
    }

    private Boolean linkElementInput(LogicElement logicElement, String inputLabel) {
        return systemModel.getLogicElements().stream()
                .filter(this::isNotOutput)
                .filter(systemLogicElement -> inputLabel.equals(systemLogicElement.getLabel()))
                .allMatch(logicElementToAssign -> assignLogicElementToInput(logicElement, logicElementToAssign));
    }

    private Boolean assignLogicElementToInput(LogicElement logicElement, LogicElement logicElementToAssign) {
        return Optional.ofNullable(logicElement.getInputs())
                .map(logicElements -> logicElements.add(logicElementToAssign))
                .orElse(handleInputNullPointer(logicElement));
    }

    static private List<String> extractGateInputsStrings(List<String> splittedGatesDeclaration) {
        List<String> gateInputStrings = new ArrayList<>(splittedGatesDeclaration);
        gateInputStrings.remove(0);
        gateInputStrings.remove(0);
        return gateInputStrings;
    }

    private Boolean handleInputLabelsNullPointer(LogicElement logicElement) {
        System.out.println("Logic element with label: " + logicElement.getLabel() + " has no inputLabels: inputLabels = " + logicElement.getInputLabels());
        return false;
    }

    private Boolean handleInputNullPointer(LogicElement logicElement) {
        System.out.println("Logic element with label: " + logicElement.getLabel() + " has no inputs: inputs = " + logicElement.getInputLabels());
        return false;
    }
}
