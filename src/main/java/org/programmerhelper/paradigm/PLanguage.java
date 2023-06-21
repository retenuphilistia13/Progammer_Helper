package org.programmerhelper.paradigm;

public abstract class PLanguage {//Programing Languages

    public abstract Boolean isVariableValid(String userInput);

    private String language;

    public PLanguage(String language){
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
        String[] str = input.split("\\s+");
        return str;
    }

    protected void setLanguageType(String languageType) {

        this.language = languageType;

    }

    public String getLanguageType() {

        return language;

    }
    
     public  abstract  boolean isIllegalCharacter(String userInput);
    public abstract  boolean isReserved(String userInput);

}
