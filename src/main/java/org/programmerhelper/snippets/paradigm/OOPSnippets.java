package org.programmerhelper.snippets.paradigm;

import org.programmerhelper.paradigm.OOPLanguage;
import org.programmerhelper.snippets.paradigm.components.RadioPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.*;


public abstract class OOPSnippets extends Snippets { //interface (gui)
    protected RadioPanel radioVarPanel, radioAccessModifier;

    protected OOPLanguage OOPlanguage;

    public abstract void gettersSetters();

    public abstract void createClass();

    public abstract void createMainClass();

    protected String radioType, accessType;

    protected ArrayList<JRadioButton> radioButtons1, radioAccessButtons;

    public OOPSnippets(OOPLanguage language, PanelListener listener, String text, boolean multi, boolean live) {
        super(listener, text, multi, live);
        this.OOPlanguage = language;
        this.flagSubmitted = false;

        appendButton.addActionListener(e-> {
            OOPlanguage.beginOutput.delete(0, OOPlanguage.beginOutput.length());
            OOPlanguage.endOutput.delete(0, OOPlanguage.endOutput.length());

        commonListener();
        insertLinesAtBeginningAndEnd(textPane, getSelectedLines(textPane), OOPlanguage.beginOutput.toString(), OOPlanguage.endOutput.toString());


        });//one time should be activated

    }


    protected enum SNIPS {
        GETTERSETTERS, CLASS, MAINCLASS
    }



    protected void gettersSettersMultiVariable(SNIPS snip) {
        boolean flag;

        if (snip == SNIPS.GETTERSETTERS) //create variable to output if getters setterts are selected
        {
            for (int i = 0; i < multipleInput.length; i++) {
                setOriginalInput(multipleInput[i]);
                flag = OOPlanguage.isVariableValid(getOriginalInput());

                if (flag) {

                    if (i >= 1) {

                        output += "\n" + OOPlanguage.createVariable(getOriginalInput(), radioType);
                        OOPlanguage.beginOutput.append(OOPlanguage.createVariable(getOriginalInput(), radioType));
                    }
                    else {
                        output = OOPlanguage.createVariable(getOriginalInput(), radioType);
                        OOPlanguage.beginOutput.append(OOPlanguage.createVariable(getOriginalInput(), radioType));
                    }
                }

            }
        } //end if getterssetter

    }

    protected void oppSubmitInput(SNIPS snip) {

StringBuilder errorWords=new StringBuilder();

        output = "";

        if (listener != null && textField.getText() != null) {
            listener.onTextSubmitted(textField.getText()); //assign textFeild using an interface
        }

        if (submitButton.isSelected()) output = ""; //clear old output

        if (multiInputBox.isSelected() && multiInputBox != null && !textField.getText().isEmpty()) { //multiple Inputs

            setOriginalInput(textField.getText());

            multipleInput = OOPlanguage.splitInput(getOriginalInput());//splitting words

            multipleInput = OOPlanguage.getUnique(multipleInput);

            boolean flag;

            gettersSettersMultiVariable(snip);

            for (int i = 0; i < multipleInput.length; i++) { //multiple inputs
                setOriginalInput(multipleInput[i]);
                flag = OOPlanguage.isVariableValid(getOriginalInput());

                if (flag) {

                    if (snip != SNIPS.GETTERSETTERS) //for other snips
                        if (i >= 1) {
                            output += setOutput();

                        } else {
                            output = setOutput();
                        }

                    else if (snip == SNIPS.GETTERSETTERS) { //for getters and setters
                        output += setOutput();
                        OOPlanguage.endOutput.append(setOutput());
                    }

                } else {
                    if (flagSubmitted) { //to not make live preview sent error (just textfeild, submit button)
                        errorWords.append("(");
                        errorWords.append(getOriginalInput());
                        errorWords.append(")");

                    }
                }
            }

//textPane.setText(output);


//            if(!livePrev)
//                textField.setText("");
//            else if(livePrev){
//                textPane.setText("");
//
//            }


            if (!(errorWords.isEmpty())) {
                showError(errorWords);
                flagSubmitted = false;
            }

        } else { //single input
            if (!textField.getText().isEmpty()) //assign input if not empty
            {
                setOriginalInput(textField.getText());

                if (OOPlanguage.isVariableValid(getOriginalInput())) {
                    if (snip == SNIPS.GETTERSETTERS) output = OOPlanguage.createVariable(getOriginalInput(), radioType);

                    output += setOutput();

                } else {
                    if (flagSubmitted) {
                        errorWords.append(" \"");
                        errorWords.append(getOriginalInput());
                        errorWords.append(" \"");
                        showError(errorWords);
                        flagSubmitted = false;
                    }
                }
            }

        }


        if(livePrev) {
            textPane.setText(output);
            listener.onTextOutput(textPane.getText());

        }else if(flagSubmitted&&isAppend==false){
                textPane.setText(output);
                listener.onTextOutput(textPane.getText());

            OOPlanguage.beginOutput.delete(0, OOPlanguage.beginOutput.length());
            OOPlanguage.endOutput.delete(0, OOPlanguage.endOutput.length());
        }
        else if(isAppend ){//appending mode
            if(textPane.getSelectedText()==null) {

                currentWritingOutput(output);
                listener.onTextOutput(textPane.getText());

                OOPlanguage.beginOutput.delete(0, OOPlanguage.beginOutput.length());
                OOPlanguage.endOutput.delete(0, OOPlanguage.endOutput.length());


            }else if(textPane.getSelectedText()!=null){
//tried the logic doesnt work properly
//                insertLinesAtBeginningAndEnd(textPane, getSelectedLines(textPane), OOPlanguage.beginOutput.toString(), OOPlanguage.endOutput.toString());
//
//                OOPlanguage.beginOutput.delete(0, OOPlanguage.beginOutput.length());
//                OOPlanguage.endOutput.delete(0, OOPlanguage.endOutput.length());
//
               // listener.onTextOutput(textPane.getText());
            }
        }
        //sendOutputListener();



    }

    protected void OOPInterface(SNIPS snip) {
        //sendOutputListener();

        commonSubmitListener();

        ///////////////////// ROW 1 ///////////////////////

        // Create a container for multiInputBox using FlowLayout

        c.insets = new Insets(0, 18, 0, 0);

        setComponentProperty(0, posy, 1, 1, 0, 0);

        panelCheckBoxContainerInit();
        add(checkBoxContainer, c);

        ////////////////////// Row 2 ////////////////////////////////////////////
        posy++;

        c.insets = new Insets(0, 22, 5, 11);
        c.anchor = GridBagConstraints.WEST; // Set anchor to the left
        setComponentProperty(0 + posx, posy, 1, 1, 0.5, 0);
        c.fill = GridBagConstraints.HORIZONTAL; // Ensure horizontal expansion

        add(textField, c);

        setComponentProperty(1 + posx, posy, 1, 1, 0, 0);
        c.insets = new Insets(5, 10, 10, 0);
        panelButtonInit(false);
//row 2 button container initialization is in snippet class above row2ButtonInit method


        add(panelButtonContainer, c);




        ///////////////////// Row 3 ////////////////////////////////////////////
        posy++;


        c.insets = new Insets(0, 15, 0, 0);
        setComponentProperty(1 + posx, posy, 1, 1, 0, 0);
        add(appendButton,c);


        if (snip == SNIPS.CLASS || snip == SNIPS.GETTERSETTERS) {
            c.insets = new Insets(1, 20, 1, 3);
            setComponentProperty(0 + posx, posy, 1, 1, 0, 0);
            add(radioAccessModifier, c);
        }


        ///////////////////// Row 4 ///////////////////////////////////////////
        posy++;

        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(0, 22, 5, 11);

        if (snip == SNIPS.MAINCLASS) setComponentProperty(0 + posx, posy, 1, 1, 1, 0.5);
        else {
            setComponentProperty(0 + posx, posy, 3, 1, 1, 0.5);
        }

        add(scrollPane, c);

        radio1Pos();

        if (snip == SNIPS.CLASS || snip == SNIPS.GETTERSETTERS) {
            setComponentProperty(3 + posx, posy, 1, 2, 0, 1);
            add(radioVarPanel, c);
        }





    }

    private  void radio1Pos(){
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(0, 2, 5, 11);
        c.anchor = GridBagConstraints.EAST; // Set anchor to the top
    }

    protected void radioInit(String[] radioString1, String[] accessModifier) {
        radioButtons1 = new ArrayList<>();
        radioAccessButtons = new ArrayList<>();

        for (String gettersSettersRadio1: radioString1) {
            radioButtons1.add(new JRadioButton(gettersSettersRadio1));
        }

        for (String accessModifier1: accessModifier) {
            radioAccessButtons.add(new JRadioButton(accessModifier1));
        }
    }

    protected void radioInit1(String[] radioString1) {
        //setComponentProperty(3 + posx, 3, 1, 2, 0, 1);
        radio1Pos();
        radioButtons1 = new ArrayList<>();

        for (String gettersSettersRadio1: radioString1)radioButtons1.add(new JRadioButton(gettersSettersRadio1));


    }

    protected void removeRadio1() {
        this.remove(radioVarPanel);
        repaint();
        revalidate();
    }

    protected void radioListener() {

        for (JRadioButton radio: radioButtons1) { //lisnter
            radio.addActionListener((ActionEvent e) -> {
                SwingUtilities.invokeLater(textField::requestFocusInWindow);

                JRadioButton selectedButton = (JRadioButton) e.getSource();
                radioType = selectedButton.getText();

                if (e.getSource() == radio&&livePrev) commonListener(); //if any radio in that group it will update the ouput
            });

        }

        for (JRadioButton radio: radioAccessButtons) { //lisnter
            radio.addActionListener((ActionEvent e) -> {
                SwingUtilities.invokeLater(textField::requestFocusInWindow);

                JRadioButton selectedButton = (JRadioButton) e.getSource();
                accessType = selectedButton.getText();

                if (e.getSource() == radio&&livePrev) commonListener();
            });

        }

        accessType = radioAccessButtons.get(0).getText(); //default option
        radioType = radioButtons1.get(0).getText();
    }


}