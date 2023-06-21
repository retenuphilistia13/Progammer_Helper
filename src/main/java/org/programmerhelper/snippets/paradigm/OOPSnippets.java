package org.programmerhelper.snippets.paradigm;

import org.programmerhelper.paradigm.OOPLanguage;
import org.programmerhelper.snippets.paradigm.components.RadioPanel;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;


public abstract class  OOPSnippets extends Snippets {//interface (gui)


protected RadioPanel radioVarPanel, radioAccessModifier;

    
protected OOPLanguage OOPlanguage;
    

public abstract void gettersSetters();
public abstract void createClass();
public abstract void createMainClass();



protected  String radioType,accessType;
     
protected ArrayList<JRadioButton> radioButtons1  , radioAccessButtons ;

protected enum SNIPS {
	GETTERSETTERS, CLASS, MAINCLASS
}

    public OOPSnippets(OOPLanguage language,PanelListener listener, String text, boolean multi, boolean live) {
        super(listener, text, multi, live);
        this.OOPlanguage = language;
       this.flagSubmitted=false;
    }
    
    protected void radioListener()
    {
               
          	for (JRadioButton radio: radioButtons1) {//lisnter
				radio.addActionListener((ActionEvent e) -> {
                                     SwingUtilities.invokeLater(textField::requestFocusInWindow);
     
                                    JRadioButton selectedButton = (JRadioButton) e.getSource();
                                    radioType = selectedButton.getText();
                                    
                                  
                                    if(e.getSource()==radio)commonListener();//if any radio in that group it will update the ouput
                                });
				
			}
                        
        
        for (JRadioButton radio: radioAccessButtons) {//lisnter
				radio.addActionListener((ActionEvent e) -> {
                                     SwingUtilities.invokeLater(textField::requestFocusInWindow);
     
                                    JRadioButton selectedButton = (JRadioButton) e.getSource();
                                    accessType = selectedButton.getText();
                                    
                                    if(e.getSource()==radio)commonListener();
                                });
				
			}
        
        	        accessType = radioAccessButtons.get(0).getText();//default option
                        radioType = radioButtons1.get(0).getText();
    }
    
    protected void gettersSettersMultiVariable(SNIPS snip){
        boolean flag;
           if(snip==SNIPS.GETTERSETTERS)//create variable to output if getters setterts are selected
                          {
                        for(int i=0;i<multipleInput.length;i++){
                            setOriginalInput(multipleInput[i]);
                            flag=OOPlanguage.isVariableValid(getOriginalInput());
                            
                            if(flag){
                                
                                if(i>=1)
                              output+="\n"+OOPlanguage.createVariable(getOriginalInput(), radioType, accessType);
                                else
                              output=OOPlanguage.createVariable(getOriginalInput(), radioType, accessType);
                                
                            }
                        
                        }
                    }//end if getterssetter
                          
    }
     

        
        
        protected void oppSubmitInput(SNIPS snip){
                     
       String errorWords = "";
    output="";
            if(listener != null&&textField.getText()!=null){
		listener.onTextSubmitted(textField.getText());//assign textFeild using an interface
            }
            
        
            if(submitButton.isSelected())output=""; //clear old output
            
		if(multiInputBox.isSelected()&&multiInputBox!=null&&textField.getText().isEmpty()==false) {//multiple Inputs
			setOriginalInput(textField.getText());
			
			multipleInput=OOPlanguage.splitInput(getOriginalInput());
			boolean flag;
                        
                      gettersSettersMultiVariable(snip);
                          
			for(int i=0;i<multipleInput.length;i++) {//multiple inputs
				setOriginalInput(multipleInput[i]);
			flag=OOPlanguage.isVariableValid(getOriginalInput());
                        
                        
				if(flag) {
                                    
					if(snip!=SNIPS.GETTERSETTERS)//for other snips
					if(i>=1) {
						output+=setOutput();
					}else {
						
						output=setOutput();
					}
                                        else if(snip==SNIPS.GETTERSETTERS){//for getters and setters
                                            output+=setOutput();
                                        }
					
					
				}else {
                                    if(flagSubmitted){//to not make live preview sent error (just textfeild, submit button)
                                        errorWords+=getOriginalInput();
					errorWords+=" ";
                                    }
				}
			}
			textArea.setText(output);
			
                        if(!(errorWords.isBlank())){
                         showError(errorWords);
                         flagSubmitted=false;
                        }
			
		}else {//single input
			if(textField.getText().isEmpty()==false) //assign input if not empty
			{
			setOriginalInput(textField.getText());

			if (OOPlanguage.isVariableValid(getOriginalInput()) == true) {
				
				
				if(snip==SNIPS.GETTERSETTERS){output=OOPlanguage.createVariable(getOriginalInput(), radioType, accessType);}
				output+=setOutput();
				textArea.setText("");
				textArea.setText(output);
			}else {
                             if(flagSubmitted){
				showError(getOriginalInput());
                              flagSubmitted=false;
                             }
			   }
			}
			
			
		}
        }
        
        

protected void OOPInterface(SNIPS snip){
    
       
commonSubmitLisnter();

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
     
    if(snip==SNIPS.CLASS||snip==SNIPS.GETTERSETTERS){
        c.insets = new Insets(1, 20, 1, 3);
        setComponentProperty(0 + posx, posy, 1, 1, 0, 0);
        add(radioAccessModifier, c);
    }
         
///////////////////// Row 4 ///////////////////////////////////////////
        posy++;
        
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(0, 22, 5, 11); 
        
        setComponentProperty(0 + posx, posy, 3, 1, 1, 0.5);
        add(scrollPane, c);

        c.anchor = GridBagConstraints.NORTH; // Set anchor to the top
        
        if(snip==SNIPS.CLASS||snip==SNIPS.GETTERSETTERS){
        setComponentProperty(3 + posx, posy, 1, 2, 0, 1);
        add(radioVarPanel, c);
        }
        
        
        
    }


    protected void radioInit(String radioString1[],String accessModifier[]){
            radioButtons1 = new ArrayList<>();
            radioAccessButtons = new ArrayList<>();
            
                for (String gettersSettersRadio1 : radioString1) {
                    radioButtons1.add(new JRadioButton(gettersSettersRadio1));
                }

                for (String accessModifier1 : accessModifier) {
                    radioAccessButtons.add(new JRadioButton(accessModifier1));
                }
        }
    
    
    protected void radioInit1(String radioString1[]){
        
      setComponentProperty(3+posx , 3, 1, 2, 0, 1);
      
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(0, 22, 5, 11); 
        c.anchor = GridBagConstraints.NORTH; // Set anchor to the top
        
            radioButtons1 = new ArrayList<>();
          
                for (String gettersSettersRadio1 : radioString1) {
                    radioButtons1.add(new JRadioButton(gettersSettersRadio1));
                }
 
    }
    protected void removeRadio1(){
        this.remove(radioVarPanel);
          repaint();
          revalidate();         
    }
    

}

