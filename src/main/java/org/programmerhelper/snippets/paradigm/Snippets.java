package org.programmerhelper.snippets.paradigm;

import org.programmerhelper.snippets.paradigm.components.CheckComboBox;
import org.programmerhelper.snippets.paradigm.language.JavaSnippets;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

public abstract class Snippets extends JPanel  {

    protected String originalInput;
    protected String output;

    protected String[] multipleInput;

    protected JScrollPane scrollPane;
    protected JTextArea textArea;
    protected JTextField textField;

    protected JButton submitButton, copyButton;

    protected GridBagLayout layout;
    protected GridBagConstraints c;

    protected PanelListener listener;

    protected String text;
    protected boolean livePrev;
    protected boolean multiInputs;

    protected JCheckBox multiInputBox, livePrevBox;
    protected CheckComboBox checkComboBox, commonComboBox;

    protected boolean flagSubmitted;
    


    public Snippets(PanelListener listener, String text, boolean multi, boolean live) {
        super();
        this.listener = listener;
        this.text = text;
        this.multiInputs = multi;
        this.livePrev = live;

    }
     
       
    
  protected  int posx = 0, posy = 0;

    protected abstract void setInterface();
    
    protected abstract void languageInterface();
    
  protected String setOutput(){return null;};///////////virtual method////////////
  protected void commonListener() {};/////////////////virtual method////////////; (overriden) for livePrevBox
    
  
    protected void commonSubmitLisnter() {
       
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

    protected void firstListen() {
 SwingUtilities.invokeLater(textField::requestFocusInWindow);//in case
     
        if (textField.getText().isEmpty() == false) {//set input if there was
            setOriginalInput(textField.getText());
        } else {
            return;
        }

        if ((textArea.getText().isEmpty()) && (!(textField.getText().isEmpty()))) {//make an ouput
            commonListener();
            textArea.setText(output);
        }
    }

    public void setText(String t) {
        textField.setText(t);
    }

    public void setPrevBox(boolean l) {
        livePrevBox.setSelected(l);
    }

    public void setMultipleInputs(boolean m) {
        multiInputBox.setSelected(m);
    }

    static class CustomTextArea extends JTextArea {//scroll pane be veticle not horizontal

        @Override
        public void scrollRectToVisible(java.awt.Rectangle aRect) {
            // Override scrollRectToVisible() to prevent automatic scrolling
            // when typing at a specific line
            if (getParent() instanceof JScrollPane scrollPane) {
                scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getValue());
            }
        }
    }

    public final void componentInit() {
         layout = new GridBagLayout();
        super.setLayout(layout);// super sayian layout
        
        c = new GridBagConstraints();
        
        // Create the text area and text field
        textArea = new CustomTextArea();// display input
        textArea.setEditable(true);

        if (text == null)text = "";
      
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        scrollPane = new JScrollPane(textArea);// scrollpane

//////////////////////intialization///////////////////////////////////

        textField = new JTextField(text, 25);// take input
        
        submitButton = new JButton("Submit");
        copyButton = new JButton("Copy");// copy button
        multiInputBox = new JCheckBox("Multiple Inputs");
        livePrevBox = new JCheckBox("Live Preview");

        livePrevBox.setSelected(livePrev);
        multiInputBox.setSelected(multiInputs);
        
//////////////////////// components listner//////////////////////////

        textField.getDocument().addDocumentListener(new DocumentListener() {//to make time from input to input (for no runtime error)

            private Timer timer;

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateTextInput();

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateTextInput();

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateTextInput();

            }

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
                    text = textField.getDocument().getText(0, textField.getDocument().getLength());

                    listener.onTextSubmitted(text);

                } catch (BadLocationException ex) {
                    Logger.getLogger(JavaSnippets.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });

        livePrevBox.addItemListener((ItemEvent e) -> {
        SwingUtilities.invokeLater(textField::requestFocusInWindow);
        
            if (e.getStateChange() == ItemEvent.SELECTED) {
                livePrev = true;
                listener.onLivePreview(livePrev);
                //no need to update output commonListener();
            } else if(e.getStateChange() == ItemEvent.DESELECTED){
                // Checkbox is deselected
                livePrev = false;
                listener.onLivePreview(livePrev);
                
            }
        });
        

        multiInputBox.addItemListener((ItemEvent e) -> {
        SwingUtilities.invokeLater(textField::requestFocusInWindow);
       
            if (e.getStateChange() == ItemEvent.SELECTED) {
                multiInputs = true;
                listener.onMultipleInputs(multiInputs);
                commonListener();//update output
            } else if(e.getStateChange() == ItemEvent.DESELECTED) {
                // Checkbox is deselected
                multiInputs = false;
                listener.onMultipleInputs(multiInputs);
                commonListener();
            }
        });


        copyButton.addActionListener(e -> {
            
        SwingUtilities.invokeLater(textField::requestFocusInWindow);
        commonListener();
        
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            if (output != null) {
                StringSelection selection = new StringSelection(textArea.getText());//copy text in textarea
                clipboard.setContents(selection, null);
            }

        });
        
        
        SwingUtilities.invokeLater(textField::requestFocusInWindow);// Set focus to the text field
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
    
    public void showError(String errorWords) {
        JOptionPane.showMessageDialog(this, "error invalid input "+errorWords);
    }

}
