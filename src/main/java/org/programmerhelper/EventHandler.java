package org.programmerhelper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import org.programmerhelper.ProgrammerHelper;
import org.programmerhelper.OOPSnip;
import org.programmerhelper.snippets.paradigm.language.C_Plus_PlusSnippets;
import org.programmerhelper.snippets.paradigm.language.JavaSnippets;

public class EventHandler {
    ActionListener createLanguageGroupListener(ArrayList<JMenuItem> languageList) {//set selected menu item to true while other menus false


        ButtonGroup group = new ButtonGroup();

        for (JMenuItem lang: languageList)group.add(lang);
        // Create and return the ActionListener
        return (ActionEvent event) -> {
            // Handle menu item selection
            JMenuItem selectedMenuItem = (JMenuItem) event.getSource();
            // Update the selected state of all menu items
            for (JMenuItem lang: languageList) {
                lang.setSelected(lang == selectedMenuItem);
            }
            // Add more menu items and update their selected states accordingly
        };
    }

    ActionListener createSnipGroupListener(ArrayList<JMenuItem> snipList) {

        ButtonGroup OOPGroup = new ButtonGroup();

        for (JMenuItem snips: snipList) {
            OOPGroup.add(snips);
        }

        return (ActionEvent event) -> {
            JMenuItem selectedMenuItem = (JMenuItem) event.getSource();
            // Update the selected state of all menu items
            for (JMenuItem snips: snipList) {
                snips.setSelected(snips == selectedMenuItem);
            }

        };
    }


    public void handleJavaSnippet(OOPSnip oopSnip, JavaSnippets javaSnip) {
        switch (oopSnip) {
            case GETSETSNIP -> javaSnip.gettersSetters();
            case MAINCLASSSNIP -> javaSnip.createMainClass();
            default -> {javaSnip.createClass();}
        }
    }

    void handleCPlusPlusSnippet(OOPSnip oopSnip, C_Plus_PlusSnippets CPlusPlusSnip) {
        switch (oopSnip) {
            case GETSETSNIP -> CPlusPlusSnip.gettersSetters();
            case MAINCLASSSNIP -> CPlusPlusSnip.createMainClass();
            default -> {CPlusPlusSnip.createClass();}
        }
    }

}
