package org.programmerhelper.snippets.paradigm.language;

import org.programmerhelper.Language;
import org.programmerhelper.paradigm.OOPLanguage;
import org.programmerhelper.snippets.paradigm.OOPSnippets;
import org.programmerhelper.snippets.paradigm.PanelListener;
import org.programmerhelper.snippets.paradigm.components.RadioPanel;



public class C_Plus_PlusSnippets extends OOPSnippets {

    public C_Plus_PlusSnippets(OOPLanguage l, PanelListener listener, String text, boolean multi, boolean live) {
        super(l, listener, text, multi, live);
        language= Language.CPLUSPLUS;
        componentInit();
    }
    SNIPS snip;


    String[] getterSettersRadios = {"string", "int", "float", "double"};
    String[] classRadios = {"class", "abstract", "freind"};

    String[] accessModifier = {"public", "private", "protected", "package"};

   
    
       @Override
    protected void languageInterface() {
        OOPInterface(snip);
    }

    @Override
    protected void setInterface() {


        
        livePrevListener();//live Preview Listener
        
     if(snip == SNIPS.CLASS||snip == SNIPS.GETTERSETTERS) {
         if (snip == SNIPS.CLASS) 
    radioInit(classRadios,accessModifier);
         else if(snip == SNIPS.GETTERSETTERS )
     radioInit(getterSettersRadios,accessModifier);   

      
            radioListener();

            radioVarPanel = new RadioPanel(radioButtons1, false);
            radioAccessModifier = new RadioPanel(radioAccessButtons, true);
     }

        languageInterface();

        firstListen();

    }

    @Override
    protected void commonListener() {
        oppSubmitInput(snip);
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
                    OOPlanguage.gettersSetters(getOriginalInput(), radioType, accessType);// call gettersetter
                case CLASS ->
                    OOPlanguage.createClass(getOriginalInput(), radioType, accessType);// call gettersetter
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
