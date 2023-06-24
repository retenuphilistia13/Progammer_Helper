/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.programmerhelper.paradigm.language;

import org.programmerhelper.Language;
import org.programmerhelper.paradigm.PLanguage;

import java.util.Set;

/**
 *
 * @author ahmed
 */
public class C extends PLanguage {

    public C(String language) {
        super(Language.CPLUSPLUS);
    }

    @Override
    public Boolean isVariableValid(String userInput) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isIllegalCharacter(String userInput) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isReserved(String userInput) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Set<String> getReservedWords() {
        return null;
    }
}
