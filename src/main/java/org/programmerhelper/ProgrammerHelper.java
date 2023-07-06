/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package org.programmerhelper;

/*
 *
 * @author ahmed
 */
import org.programmerhelper.paradigm.OOPLanguage;
import org.programmerhelper.paradigm.language.C_Plus_Plus;
import org.programmerhelper.paradigm.language.Java;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.UIManager;

import org.programmerhelper.snippets.paradigm.PanelListener;
import org.programmerhelper.snippets.paradigm.language.C_Plus_PlusSnippets;
import org.programmerhelper.snippets.paradigm.language.JavaSnippets;

import java.io.File;
import java.util.ArrayList;


public class ProgrammerHelper extends JFrame implements ActionListener, PanelListener {
    ActionEvent OOPLastAction;
    ActionEvent LastAction;
    OOPLanguage OOPLanguage;
    //OOPSnippets OOPSnip;
    EventHandler eventHandler;
    JavaSnippets javaSnip;
    C_Plus_PlusSnippets CPlusPlusSnip;
    JMenuBar menuBar;
    JMenu snippetsMenu, languageMenu;
    JMenu edits;
    JMenuItem showCheckboxes;
    JMenuItem getSetItem, classItem, mainClass;
    JMenuItem javaItem, cPlusPlusItem;
    boolean flagFirstActivity;
    private final JSONFileHandler fileHandle;
    private final String filePath;
    Language language,prevLanguage;
    private String  snippet,input,output;
    private boolean multi,livePrev;
    boolean isSnip,isOOP;
    ArrayList<JMenuItem> snipList;

    JMenuItem undoMenuItem = new JMenuItem("Undo");
    JMenuItem redoMenuItem = new JMenuItem("Redo");
    JMenuItem saveMenuItem = new JMenuItem("Save");

    OOPSnip oopSnip;
    private OutputManager <String>outputManager,inputManager;

    public ProgrammerHelper() {
        // Set up the frame
        setTitle("Programmer Little Helper");



        setSize(600, 700);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        // Add a window listener to capture the close event


        outputManager = new OutputManager(150,50);
        inputManager=new OutputManager(150,50);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                performLastAction();
                // Close the application
                System.exit(0);
            }
        });

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {
            System.out.println("error initializing look and feel ");
        }

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);


        eventHandler=new EventHandler();
        menuBar = new JMenuBar();

         edits = new JMenu("Edits");

        snippetsMenu = new JMenu("Snippets");
        languageMenu = new JMenu("Language");


        // Create the menu items
        ArrayList<JMenuItem> languageList=new ArrayList<>();

        javaItem = new JRadioButtonMenuItem("Java");
        cPlusPlusItem = new JRadioButtonMenuItem("C++");

        languageList.add(javaItem);
        languageList.add(cPlusPlusItem);
        //add languages
        snipList = new ArrayList<>();

        getSetItem = new JMenuItem("getters and setters");
        classItem = new JMenuItem("class");
        mainClass = new JMenuItem("main class");



////////////get the value from file////////
        File f = new File("commonPrefrences.json");
        filePath = f.getAbsolutePath(); ///get absolute path

        fileHandle = new JSONFileHandler();
        fileHandle.readJSONFile(filePath);

        String lan;

        lan = fileHandle.getLanguage();

        snippet = fileHandle.getSnippet();
        livePrev = fileHandle.getLivePrevBox(); //get value form file
        multi = fileHandle.getMultipleInputBox();
        input = fileHandle.getInputText();


        edits.add(saveMenuItem);
        edits.add(undoMenuItem);
        edits.add(redoMenuItem);


        //do redo save shortcuts
        int undoShortcutMask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
        int redoShortcutMask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
        int saveShortcutMask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, saveShortcutMask));
        undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, undoShortcutMask));
        redoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, redoShortcutMask));

    menuBar.add(edits);


        System.out.println("language: " + lan.equals("C++") + " real:" + language);

        isOOP = false;

        if (lan.equals(Language.JAVA.name())) {
            javaItem.setSelected(true);
            isOOP = true;

            this.language = Language.JAVA;
            prevLanguage= this.language;

        } else if (lan.equals(Language.CPLUSPLUS.name())) {
            cPlusPlusItem.setSelected(true);
            isOOP = true;

            this.language = Language.CPLUSPLUS;
            prevLanguage= this.language;

        }else {
            OOPLanguage = new Java();//intializing
            javaSnip = new JavaSnippets(OOPLanguage, this, input, multi, livePrev);
            javaItem.setSelected(false);
                }


        switch (snippet) { //to do
            case "getSetItem" -> {
                oopSnip=OOPSnip.GETSETSNIP;
                getSetItem.setSelected(true);
            }
            case "classItem" -> {
                oopSnip=OOPSnip.CLASSSNIP;
                classItem.setSelected(true);
            }
            case "mainClass" -> {
                oopSnip=OOPSnip.MAINCLASSSNIP;
                mainClass.setSelected(true);
            }
            default -> {
                classItem.setSelected(true);
            }
        }

    for (JMenuItem lanList:languageList) {
    languageMenu.add(lanList);
    }

       menuBar.add(languageMenu); //add to bar

        //if ooP language

        if (isOOP) {
            snipList.add(getSetItem);
            snipList.add(classItem);
            snipList.add(mainClass);

            for(JMenuItem snip:snipList)snippetsMenu.add(snip);
        }

        menuBar.add(snippetsMenu);
        //////set to jmenubar  frame//////
        setJMenuBar(menuBar);
        add(menuBar, BorderLayout.NORTH);
        //////////listners////////
        javaItem.addActionListener(this);
        cPlusPlusItem.addActionListener(this);

        undoMenuItem.addActionListener(this);
        redoMenuItem.addActionListener(this);
        saveMenuItem.addActionListener(this);

        ActionListener languageGroupListener = eventHandler.createLanguageGroupListener(languageList);
        javaItem.addActionListener(languageGroupListener);
        cPlusPlusItem.addActionListener(languageGroupListener);

        getSetItem.addActionListener(this);
        classItem.addActionListener(this);
        mainClass.addActionListener(this);


        ActionListener OOPSnipGroupListener = eventHandler.createSnipGroupListener(snipList);
        getSetItem.addActionListener(OOPSnipGroupListener);
        classItem.addActionListener(OOPSnipGroupListener);
        mainClass.addActionListener(OOPSnipGroupListener);

        flagFirstActivity = false;
        //////////activate first snip////////
        if (javaItem.isSelected() && javaSnip == null && !flagFirstActivity) { //one time activation if its saved in a file (extra &&flagFirstActivity==false)
            OOPLanguage = new Java();
            isSnip=false;

            outputManager.addToUndoStack(" ");
            inputManager.addToUndoStack(input);
            javaSnip = new JavaSnippets(OOPLanguage, this, input, multi, livePrev);

            createSnippet();

            addToFrame();

        } else if (cPlusPlusItem.isSelected() && CPlusPlusSnip == null && !flagFirstActivity) {
            outputManager.addToUndoStack(" ");
            inputManager.addToUndoStack(input);

            OOPLanguage = new C_Plus_Plus();
            isSnip=false;

            CPlusPlusSnip = new C_Plus_PlusSnippets(OOPLanguage, this, input, multi, livePrev);

            createSnippet();

            addToFrame();
        }


        setVisible(true);
    }



    //main

    public static void main(String[] args) {

        SwingUtilities.invokeLater(ProgrammerHelper::new);

    }


private String getCurrentOutput(Language language) {
    switch (language) {
        case JAVA -> {
            return javaSnip.getTextPaneText();
        }
        case CPLUSPLUS -> {
            return CPlusPlusSnip.getTextPaneText();
        }
        default -> {
            return javaSnip.getTextPaneText();
        }
    }
    //    output = javaSnip.getTextPaneText();
//    output = CPlusPlusSnip.getTextPaneText();

}

    private String getCurrentInput(Language language){
        switch (language) {
            case JAVA -> {
                return javaSnip.getInputText();
            }
            case CPLUSPLUS -> {
                return CPlusPlusSnip.getInputText();
            }
            default -> {
                return javaSnip.getInputText();
            }
        }
    }

boolean isNavigating;

    @Override

    public void actionPerformed(ActionEvent e) {

        if (LastAction != null) { //after last action is intialized
            flagFirstActivity = true;
        }


        if (e.getSource() == undoMenuItem) {
            outputManager.undo(); // Call the undo method of the outputManager
            inputManager.undo();
            isNavigating=true;
        }
        else if (e.getSource() == redoMenuItem) {
            outputManager.redo(); // Call the redo method of the outputManager
            inputManager.redo();
            isNavigating=true;
        }
        else if (e.getSource()==saveMenuItem) {
            outputManager.addToUndoStack(getCurrentOutput(language));
            inputManager.addToUndoStack(getCurrentInput(language));
        }


        //check language choice
        if (e.getSource() == javaItem) {
            isSnip=false;
            isOOP=true;

            prevLanguage= language;

            OOPLanguage = new Java();
            language = OOPLanguage.getLanguageType();

            if (!flagFirstActivity) {
                e = eventHandler.handleOOPFirstEvent(e, snipList, snippet);
            }


                System.out.println("language "+language+"prev language"+prevLanguage);
                if (OOPLastAction != null) {
                    if (OOPLastAction.getSource() == getSetItem || OOPLastAction.getSource() == classItem || OOPLastAction.getSource() == mainClass) {
                        e = OOPLastAction; //refrech when user alternate between the two language; the last action(getters setters would be saved)
                    }
                }


        } else if (e.getSource() == cPlusPlusItem) {
            isSnip=false;
            isOOP=true;

            prevLanguage= language;

            OOPLanguage = new C_Plus_Plus();
            language = OOPLanguage.getLanguageType();

            if (!flagFirstActivity) {
                e = eventHandler.handleOOPFirstEvent(e, snipList, snippet);
            }

                if (OOPLastAction != null) { //lastAction after is setted
                    if (OOPLastAction.getSource() == getSetItem || OOPLastAction.getSource() == classItem || OOPLastAction.getSource() == mainClass) { //set action (save)if another oop items are selected
                        e = OOPLastAction;
                    }
                }


        }

        if (isOOP) { //if language is OOP language

            if(e.getSource() == getSetItem || e.getSource() == classItem || e.getSource() == mainClass) {
                addCurrentAction();
            }

            if (e.getSource() == getSetItem) { //common between c++ and java
                snippet = "getSetItem";
oopSnip=OOPSnip.GETSETSNIP;

                removeFromFrame();
                isSnip=true;

                addOOPSnip(language); //create new panel (must do)

                createSnippet();
                addToFrame();

            } else if (e.getSource() == classItem) {
                snippet = "classItem";
                oopSnip=OOPSnip.CLASSSNIP;

                removeFromFrame();
                isSnip=true;

                addOOPSnip(language); // addOOPSnip(Language);//create new panel (must do)

                createSnippet();

                addToFrame();

            } else if (e.getSource() == mainClass) {
                snippet = "mainClass";

                oopSnip=OOPSnip.MAINCLASSSNIP;

                removeFromFrame();
                isSnip=true;

                addOOPSnip(language); //  addOOPSnip(Language);

                createSnippet();

                addToFrame();

            }

            if (e.getSource()==undoMenuItem||e.getSource()==redoMenuItem||e.getSource() == getSetItem || e.getSource() == classItem || e.getSource() == mainClass) { //set Text when entering from panel to panel

                sendOOPPrefrences(language);

                System.out.println("text :" + input);
            }

            OOPLastAction = e;
        }
        //end if
        LastAction=e;
    }

    public void sendOOPPrefrences(Language language1){

        switch (language1){
            case JAVA -> {
                javaSnip.setPrevBox(livePrev);
                javaSnip.setMultipleInputs(multi);

    javaSnip.setUserInput(inputManager.getUndoStack());
    javaSnip.setOutput(outputManager.getUndoStack());


                // System.out.println("output "+output);
            }
            case CPLUSPLUS -> {

                CPlusPlusSnip.setPrevBox(livePrev);
                CPlusPlusSnip.setMultipleInputs(multi);

    CPlusPlusSnip.setUserInput(inputManager.getUndoStack());
    CPlusPlusSnip.setOutput(outputManager.getUndoStack());

            }
        }

    }
    public void addOOPSnip(Language lan) {

        switch (lan) {
            case JAVA -> {System.out.println("java intializing");
                javaSnip = new JavaSnippets(OOPLanguage, this, input, multi, livePrev);
            }
            case CPLUSPLUS -> {System.out.println("c++ intializing");
                CPlusPlusSnip = new C_Plus_PlusSnippets(OOPLanguage, this, input, multi, livePrev);
            }
        }

    }
    public void addToFrame() {
        switch (language){
            case JAVA -> {  getContentPane().add(javaSnip);}
            case CPLUSPLUS -> { getContentPane().add(CPlusPlusSnip);}
        }

        revalidate();
        repaint();
    }
    public void removeFromFrame() {

        System.out.println("\n\n");
        System.out.println("isSnip "+isSnip+"\n");
        System.out.println("prevLanguage "+prevLanguage);
        System.out.println("Language "+ language);

Language lan;

        if(!isSnip){
            lan=prevLanguage;
            System.out.println("prev language executed");
        }else {
            System.out.println("language executed");
            lan= language;
        }

        switch (lan){
            case JAVA -> {
                if(javaSnip!=null)remove(javaSnip);
            }
            case CPLUSPLUS -> {
                if(CPlusPlusSnip!=null)remove(CPlusPlusSnip);
            }
        }


        System.out.println("\n\n");

        revalidate();
        repaint();
    }

    @Override
    public void onTextSubmitted(String text) {
        this.input = text;

    }

    @Override
    public void onLivePreview(boolean live) {
        this.livePrev = live;
    }

    @Override
    public void onMultipleInputs(boolean multi) {

        this.multi = multi;
    }

    @Override
    public void onTextOutput(String output) {
        this.output=output;
        synchroniseSaving();
    }

    private void synchroniseSaving(){

        inputManager.addToUndoStack(input);

        outputManager.addToUndoStack(output);
        outputManager.getSize();

    }

    public void createSnippet() {
        switch (language) {
            case JAVA -> {handleJavaSnippet();}
            case CPLUSPLUS -> {handleCPlusPlusSnippet();}
        }
    }


    private void handleJavaSnippet() {
        switch (oopSnip) {
            case GETSETSNIP -> javaSnip.gettersSetters();
            case MAINCLASSSNIP -> javaSnip.createMainClass();
            default -> {javaSnip.createClass();}
        }
    }

    private void handleCPlusPlusSnippet() {
        switch (oopSnip) {
            case GETSETSNIP -> CPlusPlusSnip.gettersSetters();
            case MAINCLASSSNIP -> CPlusPlusSnip.createMainClass();
            default -> {CPlusPlusSnip.createClass();}
        }
    }

    private void performLastAction() {//saving properties
        fileHandle.updateJSONFile(filePath, language, snippet, input, livePrev, multi);
    }


private void addCurrentAction(){
    outputManager.addToUndoStack(getCurrentOutput(language));
    inputManager.addToUndoStack(getCurrentInput(language));
}

}