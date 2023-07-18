package org.programmerhelper.paradigm.language;

import org.programmerhelper.Language;
import org.programmerhelper.paradigm.PLanguage;

import java.util.Set;

public class Html extends PLanguage {
    public Html(Language language) {
        super(language);
    }

    @Override
    public Boolean isVariableValid(String userInput) {
        return true;
    }

    @Override
    public boolean isIllegalCharacter(String userInput) {
        return false;
    }

    @Override
    public boolean isReserved(String userInput) {
        return false;
    }

    @Override
    public Set<String> getReservedWords() {
        return null;
    }
}
