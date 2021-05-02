package org.elita.jlm;

import java.util.Iterator;
import java.util.stream.IntStream;

import static org.elita.jlm.IscasFileReader.readIscasFile;

public class IscasCodeMapper {

    public static void mapIscasCode(String fileName) {
        String iscasCode = readIscasFile(fileName);

        for (int i = 0; i < iscasCode.length(); i++) {
            if(iscasCode.charAt(i) == ' ') continue;
            if(iscasCode.charAt(i) == '#') i = skipToNextLine(iscasCode, i);
        }
    }

    private static int skipToNextLine(final String iscasCode, int charIndex) {
        do {
            charIndex++;
        } while (iscasCode.charAt(charIndex) != '\n');
        return charIndex;
    }
}
