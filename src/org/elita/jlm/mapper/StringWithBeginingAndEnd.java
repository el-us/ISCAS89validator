package org.elita.jlm.mapper;

public class StringWithBeginingAndEnd {
    private final String string;
    private final int beginning;
    private final int endIndex;

    StringWithBeginingAndEnd(String string, int endIndex) {
        this.string = string;
        this.beginning = endIndex - string.length();
        this.endIndex = endIndex;
    }

    String getString() {
        return string;
    }

    int getEndIndex() {
        return endIndex;
    }

    public int getBeginning() {
        return beginning;
    }
}
