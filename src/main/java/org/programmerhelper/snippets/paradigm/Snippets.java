package org.programmerhelper.snippets.paradigm;


import org.programmerhelper.Language;
import org.programmerhelper.paradigm.language.Java;
import org.programmerhelper.snippets.paradigm.components.CheckComboBox;
import org.programmerhelper.snippets.paradigm.components.JPane;
import org.programmerhelper.snippets.paradigm.language.JavaSnippets;

import java.awt.*;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

public abstract class Snippets extends JPanel implements ReservedWordsProvider {

    protected String originalInput;
    protected String output;
    protected String[] multipleInput;
    protected JScrollPane scrollPane;
    protected JTextPane textPane;
    protected JTextField textField;
    protected JButton submitButton, copyButton;
    protected GridBagLayout layout;
    protected GridBagConstraints c;
    protected PanelListener listener;
    protected String userInput;
    protected boolean livePrev;
    protected boolean multiInputs;

    protected JCheckBox multiInputBox, livePrevBox;
    protected CheckComboBox checkComboBox, commonComboBox;
    protected boolean flagSubmitted;

    protected Language language;

    private Java java;
    protected  int posx = 0, posy = 0;
    public Snippets(PanelListener listener, String text, boolean multi, boolean live) {
        super();
        this.listener = listener;
        this.userInput = text;
        this.multiInputs = multi;
        this.livePrev = live;


    }



    protected abstract void setInterface();

    protected abstract void languageInterface();

  protected String setOutput(){return null;};///////////virtual method////////////
  protected void commonListener() {};/////////////////virtual method////////////; (overriden) for livePrevBox



    @Override
    public Set<String> getReservedWords() {
        ReservedWordsProvider reservedWordsProvider;

        switch (language){
            case JAVA -> {reservedWordsProvider = new Java();}
            default -> {reservedWordsProvider = new Java();}
        }
        return reservedWordsProvider.getReservedWords();
    }

    protected void currentWritingOutput(String Text){

        try {//get current poition
            StyledDocument document = textPane.getStyledDocument();
            int caretPosition = textPane.getCaretPosition();
            document.insertString(caretPosition, Text, null);
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }

    }

    protected void sendOutputListner(){

        SwingUtilities.invokeLater(() -> {//send output to programmer Helper
            textPane.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    listener.onTextOutput(textPane.getText());
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    listener.onTextOutput(textPane.getText());
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    //not good idea if you wanna delete line on text pane
                    //listener.onTextOutput(textPane.getText());
                }
            });

        });

    }
/*


                */


    protected void firstListen() {
 SwingUtilities.invokeLater(textField::requestFocusInWindow);//in case
//
//        if (textField.getText().isEmpty() == false) {//set input if there was
//            setOriginalInput(textField.getText());
//        } else {
//            return;
//        }
//
//        if ((textPane.getText().isEmpty()) && (!(textField.getText().isEmpty()))) {//make an ouput
//            commonListener();
//            textPane.setText(output);
//        }


    }

    public void setOutput(String output){

        this.output=output;
        textPane.setText(output);

    }

    public void setUserInput(String t) {
        textField.setText(t);
    }

    public void setPrevBox(boolean l) {
        livePrevBox.setSelected(l);
    }

    public void setMultipleInputs(boolean m) {
        multiInputBox.setSelected(m);
    }
    public void setMultipleInputs(String output) {
        textPane.setText(output);
    }



    public final void componentInit() {
        layout = new GridBagLayout();
        super.setLayout(layout);

        c = new GridBagConstraints();
        // Create the text Pane and text field
        Set<String>a=getReservedWords();
        String[] Default = a.toArray(new String[a.size()]);

        switch (language){
            case JAVA -> {
                 java=new Java();
                textPane = new JPane(java.accessModifierRadios,java.getDataType(),java.classRadios,Default);// display input
            }
            default -> {
                textPane = new JPane();
            }
        }



        textPane.setEditable(true);

        if (userInput == null) userInput = "";
        
        
        scrollPane = new JScrollPane(textPane);// scrollpane
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

//////////////////////intialization///////////////////////////////////

        textField = new JTextField(userInput, 25);// take input

        submitButton = new JButton("Submit");
        copyButton = new JButton(" Copy ");// copy button
        multiInputBox = new JCheckBox("Multiple Inputs");
        livePrevBox = new JCheckBox("Live Preview");

        livePrevBox.setSelected(livePrev);
        multiInputBox.setSelected(multiInputs);

//////////////////////// components listner//////////////////////////

        textField.getDocument().addDocumentListener(new DocumentListener() {//to make time from input to input (for no runtime error)
            private Timer timer;
            @Override
            public void insertUpdate(DocumentEvent e) {updateTextInput();}
            @Override
            public void removeUpdate(DocumentEvent e) {updateTextInput();}
            @Override
            public void changedUpdate(DocumentEvent e) {updateTextInput();}

            protected void updateTextInput() {
                if (timer != null && timer.isRunning()) {
                    timer.restart();
                } else {
                    timer = new Timer(5, (ActionEvent actionEvent) -> {//timer for handling to many inputs

                    });
                    timer.setRepeats(false);
                    timer.start();
                }
                try {
                    userInput = textField.getDocument().getText(0, textField.getDocument().getLength());

                    listener.onTextSubmitted(userInput);

                } catch (BadLocationException ex) {
                    Logger.getLogger(JavaSnippets.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        livePrevBox.addItemListener((ItemEvent e) -> {
        SwingUtilities.invokeLater(textField::requestFocusInWindow);

            if (e.getStateChange() == ItemEvent.SELECTED) {
                livePrev = true;
                //commonListener();
            } else if(e.getStateChange() == ItemEvent.DESELECTED){
                livePrev = false;
                //commonListener();
            }
            listener.onLivePreview(livePrev);
        });


        multiInputBox.addItemListener((ItemEvent e) -> {
        SwingUtilities.invokeLater(textField::requestFocusInWindow);

            if (e.getStateChange() == ItemEvent.SELECTED) {
                multiInputs = true;
                commonListener();//update output
            } else if(e.getStateChange() == ItemEvent.DESELECTED) {
                // Checkbox is deselected
                multiInputs = false;
//                commonListener();
            }
            listener.onMultipleInputs(multiInputs);
        });

        copyButton.addActionListener(e -> {

        SwingUtilities.invokeLater(textField::requestFocusInWindow);
        
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            if (output != null) {
                StringSelection selection = new StringSelection(textPane.getText());//copy text in textarea
                clipboard.setContents(selection, null);
            }

        });

        SwingUtilities.invokeLater(textField::requestFocusInWindow);// Set focus to the text field
    }
    protected JPanel panelButtonContainer;
    protected void panelButtonInit( boolean swapSubmitButton){
        panelButtonContainer =new JPanel(new FlowLayout(FlowLayout.LEFT));
        //intialize row2 buttons

        c.fill = GridBagConstraints.NONE;

    if(swapSubmitButton) {
    panelButtonContainer.add(copyButton);
    panelButtonContainer.add(submitButton);
      }else if(!swapSubmitButton){
    panelButtonContainer.add(submitButton);
    panelButtonContainer.add(copyButton);

    }

    }
    JPanel checkBoxContainer ;
    protected void panelCheckBoxContainerInit(){
        checkBoxContainer =new JPanel(new FlowLayout(FlowLayout.LEFT));

        c.anchor = GridBagConstraints.WEST; // Set anchor to the left
        c.fill = GridBagConstraints.WEST;

        checkBoxContainer.add(multiInputBox);
        checkBoxContainer.add(livePrevBox);

    }

    public void setOriginalInput(String originalInput) {
        this.originalInput = originalInput;
    }

    public String getOriginalInput() {
        return this.originalInput;
    }

    protected void setComponentProperty(int gridx, int gridy, int gridWidth, int gridheight, double weightx, double weighty) {

        c.gridx = gridx;
        c.gridy = gridy;
        c.gridwidth = gridWidth;
        c.gridheight = gridheight;
        c.weightx = weightx;
        c.weighty = weighty;

    }

    public void showError(StringBuilder errorWords) {
        JOptionPane.showMessageDialog(this, "error invalid input "+errorWords.toString());
    }
    protected void commonSubmitListener() {

        textField.addActionListener(e -> {
            if (e.getSource() == textField) {
                flagSubmitted = true;
            }

            commonListener();

            SwingUtilities.invokeLater(textField::requestFocusInWindow);

            if (e.getSource() == textField)//just to reset (just in case)
            {
                flagSubmitted = false;
            }

        });

        submitButton.addActionListener(e -> {// submit button listener
            if (e.getSource() == submitButton) {
                flagSubmitted = true;
            }

            commonListener();

            SwingUtilities.invokeLater(textField::requestFocusInWindow);

            if (e.getSource() == submitButton) {
                flagSubmitted = false;
            }
        });
    }

    protected void livePrevListener() {//live listener

        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {

                if (livePrevBox.isSelected()) {
                    commonListener();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

                if (livePrevBox.isSelected()) {
                    commonListener();
                }

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

                if (livePrevBox.isSelected()) {
                    commonListener();
                }
            }

        });

    }

}
