package org.programmerhelper.snippets.paradigm.language;

import org.programmerhelper.Language;
import org.programmerhelper.paradigm.PLanguage;
import org.programmerhelper.paradigm.language.Html;
import org.programmerhelper.snippets.paradigm.OOPSnippets;
import org.programmerhelper.snippets.paradigm.PanelListener;
import org.programmerhelper.snippets.paradigm.Snippets;

import javax.swing.*;
import java.awt.*;

public class HtmlSnippet extends Snippets {
    public HtmlSnippet(PanelListener listener, String text, boolean multi, boolean live) {
        super(listener, text, multi, live);
        language=Language.HTML;
        componentInit();
    }

    @Override
    protected void setInterface() {
        //componentInit();

        commonSubmitListener();

        livePrevListener();//live Preview Listener

        languageInterface();

    }
public void htmlSnip(){
    setInterface();
}
    @Override
    protected void languageInterface() {

///////////////////// ROW 1 ///////////////////////
        c.anchor = GridBagConstraints.WEST; // Set anchor to the left
        c.fill = GridBagConstraints.WEST;
        c.insets = new Insets(0, 18, 0, 0);
// Create a container for multiInputBox using FlowLayout
        JPanel checkBoxContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));

        checkBoxContainer.add(multiInputBox);
        checkBoxContainer.add(livePrevBox);

        setComponentProperty(0, posy, 1, 1, 0, 0);
        add(checkBoxContainer, c);

////////////////////// Row 2 ////////////////////////////////////////////
        posy++;
        c.insets = new Insets(0, 22, 5, 11);
        c.anchor = GridBagConstraints.WEST; // Set anchor to the left

        setComponentProperty(0 + posx, posy, 1, 1, 0.5, 0);
        c.fill = GridBagConstraints.HORIZONTAL; // Ensure horizontal expansion

        add(textField, c);

        setComponentProperty(2 + posx, posy, 1, 1, 0, 0);
        c.insets = new Insets(10, 10, 0, 0);
        c.fill = GridBagConstraints.NONE;

        add(copyButton, c);

        setComponentProperty(1 + posx, posy, 1, 1, 0, 0);

        add(submitButton, c);

///////////////////// Row 3 ////////////////////////////////////////////
        posy++;


///////////////////// Row 4 ///////////////////////////////////////////
        posy++;

        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(0, 22, 5, 11);

        setComponentProperty(0 + posx, posy, 3, 1, 1, 0.5);
        add(scrollPane, c);

        c.anchor = GridBagConstraints.NORTH; // Set anchor to the top



    }
    PLanguage currentLanguage;

    @Override
    protected void commonListener() {
        //add listenr
        currentLanguage= new Html(Language.HTML);

        StringBuilder errorWords=new StringBuilder();

        output = "";

        if (listener != null && textField.getText() != null)listener.onTextSubmitted(textField.getText()); //send (save) textFeild value using an interface


        if (submitButton.isSelected()) output = ""; //clear old output

        if (multiInputBox.isSelected() && multiInputBox != null && !textField.getText().isEmpty()) { //multiple Inputs

            setOriginalInput(textField.getText());

            multipleInput = currentLanguage.splitInput(getOriginalInput());//splitting words
            multipleInput = currentLanguage.getUnique(multipleInput);//extract unique value only


            boolean flag;
            for (int i = 0; i < multipleInput.length; i++) { //multiple inputs
                setOriginalInput(multipleInput[i]);
                flag = currentLanguage.isVariableValid(getOriginalInput());

                if (flag) {


                        if (i >= 1) {
                            output += setOutput();

                        } else {
                            output = setOutput();
                        }



                } else {
                    if (flagSubmitted) { //to not make live preview sent error (just textfeild, submit button)
                        errorWords.append("(");
                        errorWords.append(getOriginalInput());
                        errorWords.append(")");

                    }
                }
            }


            if (!(errorWords.isEmpty())) {
                showError(errorWords);
                flagSubmitted = false;
            }

        } else { //single input
            if (!textField.getText().isEmpty()) //assign input if not empty
            {
                setOriginalInput(textField.getText());

                if (currentLanguage.isVariableValid(getOriginalInput())) {

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
        }else if(flagSubmitted&&isAppend==false){
            textPane.setText(output);
            listener.onTextOutput(textPane.getText());
        }
        else if(isAppend ){//appending mode
            if(textPane.getSelectedText()==null) {

                currentWritingOutput(output);
                listener.onTextOutput(textPane.getText());

            }else if(textPane.getSelectedText()!=null){

            }
        }


    }

    @Override
    protected String setOutput(){
       return "output";

    }
}
