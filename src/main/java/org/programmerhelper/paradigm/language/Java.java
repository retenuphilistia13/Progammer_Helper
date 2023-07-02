package org.programmerhelper.paradigm.language;

import org.programmerhelper.Language;
import org.programmerhelper.paradigm.OOPLanguage;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;
import javax.swing.text.Highlighter;

public class Java extends OOPLanguage {

    private final static String reservedKeywords[] = {"abstract", "assert", "boolean", "break", "byte", "case", "catch",
        "char", "class", "const", "continue", "default", "do", "double", "else", "extends", "false", "final",
        "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long",
        "native", "new", "null", "package", "private", "protected", "public", "return", "short", "static",
        "strictfp", "super", "switch", "synchronized", "this", "throw", "throws", "transient", "true", "try",
        "void", "volatile", "while","String"};

    public String[] popularRadios = {
            "String", "boolean", "int", "float", "double", "char"
    };

    public String[] Primitive = {
            "boolean", "byte", "short", "int", "long", "float", "double", "char"
    };

    public String[] Wrapper = {
            "Boolean", "Byte", "Short", "Integer", "Long", "Float", "Double", "Character"
    };

    public String[] getDataType(){

        String[] combined = Stream.concat(Stream.concat(Arrays.stream(popularRadios), Arrays.stream(Primitive)), Arrays.stream(Wrapper))
                .toArray(String[]::new);

    return combined;
    }
    public String[] classRadios = {
            "class", "interface", "abstract", "record"
    };
    public String[] accessModifierRadios = {
            "public", "private", "protected", "package"
    };

    static final char[] illegalChar = {'~', '!', '@', '#', '%', '^', '&', '*', '(', ')', '-', '+', '=', '{', '}', '[',
        ']', '|', '\\', ':', ';', '"', '\'', '<', '>', ',', '.', '?', '/'};

    public Java() {

super(Language.JAVA);

    }

    /**
     *
     * @param userInput
     * @param dataType
     * @param accessModifier
     * @return
     */


    @Override
    public String gettersSetters(String userInput, String dataType, String accessModifier) {

        String modifiedInput;

        modifiedInput = capitalizeFirstChar(userInput);

        if ("package".equals(accessModifier)) {
            accessModifier = "";
        }

                String begin="\n\n    " + accessModifier + " void set" + modifiedInput + "(" + dataType + " " + userInput + ")"
                        + "{\n\n    this." + userInput + " = " + userInput + ";\n\n\t}";
        String end="\n\n    " + accessModifier + " " + dataType + " get" + modifiedInput + "()" + "{\n\n    return "
                + userInput + "; \n\n\t } \n         ";


       // endOutput.append(begin+end);

        return begin+end;


    }

    @Override
    public String createClass(String userInput, String type, String accessModifier) {
        if ("package".equals(accessModifier)) {
            accessModifier = "";
        }
        // add public private protected
        String modifiedInput = capitalizeFirstChar(userInput);
        if (!"public".equals(accessModifier) && !"class".equals(type))// ABTRACT CLASS INTERFACE AND RECORD COULDNT BE ANYTHING
        // OTHER THAN PUBLIC
        {

            return " \n\n";
        }


String begin="";
         switch (type) {
            case "abstract" ->
                begin="\n" + "    " + accessModifier + "  " + type + " class " + modifiedInput + " {\n\n\n   \n";
            case "class" ->
                    begin= "\n" + "    " + accessModifier + "  " + type + " " + modifiedInput + " {\n\n\t"
                + "public" + " " + modifiedInput + "(){\n\n\t}" + "\n   \n";
            default ->
                    begin="\n" + "    " + accessModifier + "  " + type + " " + modifiedInput + " {\n\n\n    \n";
        };
String end="\n     }";
beginOutput.append(begin);
endOutput.append(end);

        return (begin+end);
    }// type ->abstract class interface etc


    public String createVariable(String userInput, String dataType) {
        String output = "    " + "private " + dataType + " " + userInput + ";\n";
       // beginOutput.append(output);
        return output;

    }

    @Override
    public String createVariable(String userInput, String dataType, String accessModifier) {
        String output = "    " + accessModifier+" " + dataType + " " + userInput + ";\n";
        return output;

    }

    @Override
    public String createMainClass(String userInput, String type, String accessModifier) {

        String begin,end;
begin="\n  public class " + userInput + " { "+
        "\n\n public static void main(String args[])"+"{ \n";
end= "     System.out.println(\"\"); \n"+
        "     }\n\n   } \n";
       // System.out.println("\n\n"+"before main here" +beginOutput.toString()+endOutput.toString()+"\n\n");
        beginOutput.append(begin);

        endOutput.append(end);
       // System.out.println("\n\n"+"after main here" +beginOutput.toString()+endOutput.toString()+"\n\n");
        return begin+end;

    }

    @Override
    public Boolean isVariableValid(String userInput) {

        char firstChar = userInput.charAt(0);
        int inputLength = userInput.length();

        // false sector
        if (inputLength < 1) {
            return false;
        } else if (userInput.contains(" ")) {
            return false;
        } else if (Character.isDigit(firstChar)) {
            return false;
        } else if (firstChar == '_' && inputLength < 2)// _ not allowed while $ is fine
        {
            return false;
        } else if (isReserved(userInput) == true) {
            return false;
        } else {
            return isIllegalCharacter(userInput) != true; // true sector
        }                // if(userInput.contains("_")||userInput.contains("$"))
        // return true;

    }

    @Override
    public  boolean isIllegalCharacter(String userInput) {

        Arrays.sort(illegalChar);

        for (char ch : userInput.toCharArray()) {
            if (Arrays.binarySearch(illegalChar, ch) >= 0) {
                return true;
            }
        }

        return false;

    }
 
    @Override
    public  boolean isReserved(String userInput) {

        Arrays.sort(reservedKeywords);

        return Arrays.binarySearch(reservedKeywords, userInput) >= 0;

    }

    @Override
    public Set<String> getReservedWords() {
        // Define your array of reserved words

        // Create a HashSet and add the reserved words from the array
        Set<String> reservedWords = new HashSet<>();
        Collections.addAll(reservedWords, reservedKeywords);

        return reservedWords;
    }
}
