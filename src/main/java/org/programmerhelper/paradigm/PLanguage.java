package org.programmerhelper.paradigm;

import org.programmerhelper.Language;
import org.programmerhelper.snippets.paradigm.ReservedWordsProvider;

import java.util.Arrays;

public abstract class PLanguage implements ReservedWordsProvider {//Programing Languages
    Language language;
    public abstract Boolean isVariableValid(String userInput);

//    private String language;

    public PLanguage(Language language){
        this.language=language;
    }
//public abstract Boolean isVariableValid(String userInput);
    public String capitalizeFirstChar(String str) {

        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);

    }

    public String[] splitInput(String input) {
        // Split the input string using the regular expression \s+

        return input.split("\\s+");
    }

    public String[] getUnique(String[] input) {
        // Split the input string using the regular expression \s+
        return Arrays.stream(input)//get unique
                .distinct()
                .toArray(String[]::new);
    }



    public Language getLanguageType() {

        return language;

    }
    
     public  abstract  boolean isIllegalCharacter(String userInput);
    public abstract  boolean isReserved(String userInput);

}
