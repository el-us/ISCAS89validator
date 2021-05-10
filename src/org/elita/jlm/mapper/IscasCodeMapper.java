package org.elita.jlm.mapper;


import org.elita.jlm.systemModel.SystemModel;
import org.elita.jlm.systemModel.logicElements.LogicElement;
import org.elita.jlm.systemModel.logicElements.LogicElementsType;
import org.elita.jlm.systemModel.logicElements.impl.*;

import java.util.*;

import static org.elita.jlm.mapper.IscasFileReader.readIscasFile;

public class IscasCodeMapper {

    private static final String HASH = "#";
    private static final char SPACE = ' ';
    private int parsedElements;

    private final SystemModel systemModel;


    public IscasCodeMapper() {
        this.parsedElements = 0;
        this.systemModel = new SystemModel();
    }

    public SystemModel mapIscasCode(String fileName) {
        List<String> iscasCodelineList = readIscasFile(fileName);

        long parsedGates = iscasCodelineList.stream()
                .map(this::removeSpaces)
                .filter(this::skipEmptyLines)
                .filter(this::ignoreHashedLines)
                .map(this::mapInputs)
                .filter(Objects::nonNull)
                .map(this::mapOutputs)
                .filter(Objects::nonNull)
                .map(this::mapLogicGate)
                .count();

        System.out.println("\nSuccessfully parsed " + parsedElements + " elements\n" +
                "including: " + parsedGates + " logic gates from file: " + fileName);

        boolean allLinked = linkLogicElements();

        if(allLinked) {
            System.out.println("\nAll element from file linked\n");
        } else {
            System.out.println("\nSome errors during linking occurred\n");
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
        if (splittedInputDeclaration.get(0).equals(LogicElementsType.INPUT)) {
            Input input = new Input(splittedInputDeclaration.get(1));
            systemModel.getLogicElements().add(input);
            parsedElements++;
            return null;
        }
        return line;
    }

    private String mapOutputs(final String line) {
        List<String> splittedOutputDeclaration = Arrays.asList(line.split("[(]|[)]"));
        if (splittedOutputDeclaration.get(0).equals(LogicElementsType.OUTPUT)) {
            Output output = new Output(splittedOutputDeclaration.get(1));
            systemModel.getLogicElements().add(output);
            parsedElements++;
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
        return !splittedGatesDeclaration.get(0).equals(LogicElementsType.INPUT) || !splittedGatesDeclaration.get(0).equals(LogicElementsType.OUTPUT);
    }

    private Boolean mapLogicGate(final List<String> splittedGateDeclaration) {
        String gateLabel = splittedGateDeclaration.get(0);
        String gateType = splittedGateDeclaration.get(1);
        List<String> inputLabels = extractGateInputsStrings(splittedGateDeclaration);
        parsedElements++;
        return mapLogicGatesForType(gateLabel, gateType, inputLabels)
                && mapOutputToLogicElement(gateLabel);
    }

    private Boolean mapLogicGatesForType(String gateLabel, String gateType, List<String> inputLabels) {
        switch (gateType) {
            case LogicElementsType.AND:
                return systemModel.getLogicElements().add(new And(gateLabel, inputLabels));
            case LogicElementsType.OR:
                return systemModel.getLogicElements().add(new Or(gateLabel, inputLabels));
            case LogicElementsType.NOT:
                return systemModel.getLogicElements().add(new Not(gateLabel, inputLabels));
            case LogicElementsType.XOR:
                return systemModel.getLogicElements().add(new Xor(gateLabel, inputLabels));
            case LogicElementsType.NAND:
                return systemModel.getLogicElements().add(new Nand(gateLabel, inputLabels));
            case LogicElementsType.NOR:
                return systemModel.getLogicElements().add(new Nor(gateLabel, inputLabels));
            case LogicElementsType.DFF:
                return systemModel.getLogicElements().add(new Dff(gateLabel, inputLabels));
            default:
                return false;
        }
    }

    private boolean mapOutputToLogicElement(String readGateLabel) {
        if(systemModel.getOutputByLabel(readGateLabel) != null) {
            systemModel.getOutputByLabel(readGateLabel).setInputLabel(readGateLabel);
            return systemModel.getOutputByLabel(readGateLabel).getInputLabels().size() == 1;
        } else return true;
    }

    private Boolean linkLogicElements() {
        return systemModel.getLogicElements().stream()
                .filter(this::isNotInput)
                .allMatch(this::linkLogicElement);
    }

    private boolean isNotInput(LogicElement logicElement) {
        return !logicElement.getType().equals(LogicElementsType.INPUT);
    }

    private boolean linkLogicElement(LogicElement logicElement) {
        return Optional.ofNullable(logicElement.getInputLabels())
                .map(inputLabels -> inputLabels.stream()
                        .allMatch(inputLabel -> linkElementInput(logicElement, inputLabel)))
                .orElse(handleInputLabelsNullPointer(logicElement));
    }

    private boolean isNotOutput(LogicElement logicElement) {
        return !logicElement.getType().equals(LogicElementsType.OUTPUT);
    }

    private boolean linkElementInput(LogicElement logicElement, String inputLabel) {
        return systemModel.getLogicElements().stream()
                .filter(this::isNotOutput)
                .filter(systemLogicElement -> inputLabel.equals(systemLogicElement.getLabel()))
                .allMatch(logicElementToAssign -> assignLogicElementToInput(logicElement, logicElementToAssign));
    }

    private boolean assignLogicElementToInput(LogicElement logicElement, LogicElement logicElementToAssign) {
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

    private boolean handleInputLabelsNullPointer(LogicElement logicElement) {
        if(logicElement.getInputLabels() == null) {
            System.out.println("Logic element: " + logicElement.getType() + " with label: " + logicElement.getLabel() + " has no inputLabels: inputLabels = " + logicElement.getInputLabels());
            return false;
        } else return true;
    }

    private boolean handleInputNullPointer(LogicElement logicElement) {
        if(logicElement.getInputs() == null) {
            System.out.println("Logic element: " + logicElement.getType() + " with label: " + logicElement.getLabel() + " has no inputs: inputs = " + logicElement.getInputs());
            return false;
        } else return true;
    }
}
