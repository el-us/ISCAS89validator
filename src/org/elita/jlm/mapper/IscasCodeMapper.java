package org.elita.jlm.mapper;


import org.elita.jlm.*;

import static org.elita.jlm.IscasFileReader.readIscasFile;

public class IscasCodeMapper {

    private SystemModel systemModel = new SystemModel();
    private String charIndex;


    public void mapIscasCode(String fileName) {
        String iscasCode = readIscasFile(fileName);

        for (int i = 0; i < iscasCode.length(); i++) {
            i = skipToNextLineIfEOL(iscasCode, i);
            i = skipSpaces(iscasCode, i);
            i = skipHashedLines(iscasCode, i);
            i = skipSpaces(iscasCode, i);
            i = mapInputOutput("INPUT", iscasCode, i);
            i = mapInputOutput("OUTPUT", iscasCode, i);
        }
    }

    private static int skipToNextLineIfEOL(final String iscasCode, int charIndex) {
        return iscasCode.charAt(charIndex) == '\n' ? ++charIndex : charIndex;

    }

    private static int skipToNextLine(final String iscasCode, int charIndex) {
        do {
            charIndex++;
        } while (iscasCode.charAt(charIndex) != '\n');
        return ++charIndex;
    }

    private static int skipSpaces(final String iscasCode, int charIndex) {
        while (iscasCode.charAt(charIndex) == ' ') {
            charIndex++;
        }
        return charIndex;
    }

    private static int skipHashedLines(final String iscasCode, int charIndex) {
        if(iscasCode.charAt(charIndex) == '#') {
            charIndex = skipToNextLine(iscasCode, charIndex);
        }
        return charIndex;
    }

    private static StringWithBeginingAndEnd readString(final String iscasCode, int charIndex) {
        StringBuilder stringBuilder = new StringBuilder();
        char character = iscasCode.charAt(charIndex);
        while (character > 'A' && character < 'Z' || character > 'a' && character < 'z' || character > '0' && character < '9') {
            stringBuilder.append(character);
            character = iscasCode.charAt(++charIndex);
        }
        return new StringWithBeginingAndEnd(stringBuilder.toString(), charIndex);
    }

    private int mapInputOutput(final String inputOrOutput, final String iscasCode, int charIndex) {
        StringWithBeginingAndEnd stringWithBeginingAndEnd = readString(iscasCode, charIndex);
        while (stringWithBeginingAndEnd.getString().equals(inputOrOutput)) {
            if (iscasCode.charAt(charIndex) == '(') {
                stringWithBeginingAndEnd = mapInput(iscasCode, charIndex);
            }
        }
        return stringWithBeginingAndEnd.getBeginning();
    }

    private StringWithBeginingAndEnd mapInput(String iscasCode, int charIndex) {
        StringWithBeginingAndEnd stringWithBeginingAndEnd = readString(iscasCode, charIndex);
        systemModel.getInputList().add(stringWithBeginingAndEnd.getString());
        charIndex = skipToNextLine(iscasCode, stringWithBeginingAndEnd.getEndIndex());
        return readString(iscasCode, charIndex);
    }
}
