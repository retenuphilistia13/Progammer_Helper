package org.programmerhelper.snippets.paradigm.language;

import org.programmerhelper.Language;
import org.programmerhelper.paradigm.OOPLanguage;
import org.programmerhelper.paradigm.language.Java;
import org.programmerhelper.snippets.paradigm.OOPSnippets;
import org.programmerhelper.snippets.paradigm.PanelListener;
import org.programmerhelper.snippets.paradigm.components.CheckComboBox;
import org.programmerhelper.snippets.paradigm.components.RadioPanel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;


public class JavaSnippets extends OOPSnippets {
    Java java;
    SNIPS snip;
    public JavaSnippets(OOPLanguage l, PanelListener listener, String text, boolean multi, boolean live) {
        super(l, listener, text, multi, live);
        java=new Java();
        language=Language.JAVA;
    }
    @Override
    protected void languageInterface() {
        livePrevListener(); //live Preview Listener


        ////////////////row 2/////////////////
        c.anchor = GridBagConstraints.WEST; // Set anchor to the left
        c.insets = new Insets(10, 10, 0, 0);
        c.fill = GridBagConstraints.NONE;

        if (snip == SNIPS.GETTERSETTERS) {
            String[] comboList = {
                    "Popular", "Primitive", "Wrapper"
            };

            checkComboBox = new CheckComboBox(comboList);
            setComponentProperty(3 + posx, 1, 1, 1, 0, 0);

            add(checkComboBox, c);
        }

        if (snip == SNIPS.CLASS || snip == SNIPS.GETTERSETTERS) { //intialize Radio and adding them to panel
            if (snip == SNIPS.CLASS) //intialization
                radioInit(java.classRadios, java.accessModifierRadios);

            else if (snip == SNIPS.GETTERSETTERS)
                radioInit(java.popularRadios, java.accessModifierRadios);

            radioListener();

            radioVarPanel = new RadioPanel(radioButtons1, false); //add radios to panel
            radioAccessModifier = new RadioPanel(radioAccessButtons, true);
        }

    }

    @Override
    protected void setInterface() {

        componentInit();

        languageInterface();

        OOPInterface(snip);

        commonListener();

    }

    @Override
    protected void commonListener() {

        oppSubmitInput(snip);

        if (checkComboBox != null) {

            checkComboBox.addActionListener((ActionEvent e) -> {
                SwingUtilities.invokeLater(textField::requestFocusInWindow);

                switch (checkComboBox.getSelectedItem()) {
                    case "Primitive" -> {
                        System.out.println("Primitive");

                        removeRadio1();
                        radioInit1(java.Primitive);
                        radioListener();
                        radioVarPanel = new RadioPanel(radioButtons1, false); //add to panel
                        add(radioVarPanel, c);
                    }

                    case "Popular" -> {
                        System.out.println("Popular");
                        removeRadio1();
                        radioInit1(java.popularRadios);
                        radioListener();
                        radioVarPanel = new RadioPanel(radioButtons1, false); //add to panel
                        add(radioVarPanel, c);
                    }

                    case "Wrapper" -> {
                        removeRadio1();

                        radioInit1(java.Wrapper);
                        radioListener();
                        radioVarPanel = new RadioPanel(radioButtons1, false); //add to panel
                        add(radioVarPanel, c);
                    }

                    default -> {}
                }
            });
        }


    }

    @Override
    public void gettersSetters() {
        snip = SNIPS.GETTERSETTERS;
        setInterface();
    }

    @Override
    public void createClass() {
        snip = SNIPS.CLASS;
        setInterface();
    }

    @Override
    public void createMainClass() {
        snip = SNIPS.MAINCLASS;
        setInterface();
    }

    @Override
    protected String setOutput() {
        if (snip != null && getOriginalInput() != null) {
            return switch (snip) {
                case GETTERSETTERS ->
                        OOPlanguage.gettersSetters(getOriginalInput(), radioType, accessType); // call gettersetter
                case CLASS ->
                        OOPlanguage.createClass(getOriginalInput(), radioType, accessType); // call gettersetter
                case MAINCLASS ->
                        OOPlanguage.createMainClass(getOriginalInput(), "", "");

                default ->
                        "";

            };
        } else {
            return " ";
        }
    }

}