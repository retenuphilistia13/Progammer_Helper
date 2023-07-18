/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package org.programmerhelper;

/*
 *
 * @author ahmed
 */
import org.programmerhelper.paradigm.OOPLanguage;
import org.programmerhelper.paradigm.PLanguage;
import org.programmerhelper.paradigm.language.C_Plus_Plus;
import org.programmerhelper.paradigm.language.Html;
import org.programmerhelper.paradigm.language.Java;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.UIManager;

import org.programmerhelper.snippets.paradigm.PanelListener;
import org.programmerhelper.snippets.paradigm.language.C_Plus_PlusSnippets;
import org.programmerhelper.snippets.paradigm.language.HtmlSnippet;
import org.programmerhelper.snippets.paradigm.language.JavaSnippets;

import java.io.File;
import java.util.ArrayList;


public class ProgrammerHelper extends JFrame implements ActionListener, PanelListener {
    ActionEvent OOPLastAction;
    ActionEvent LastAction;
    OOPLanguage OOPLanguage;

    EventHandler eventHandler;
    JMenuBar menuBar;
    JMenu snippetsMenu, languageMenu;
    JMenu edits;
    JMenuItem getSetItem, classItem, mainClass;
    JMenuItem javaItem, cPlusPlusItem,htmlItem;
    boolean flagFirstActivity;
    private final JSONFileHandler fileHandle;
    private final String filePath;
    Language language,prevLanguage;
    private String  snippet,input,output;
    private boolean multi,livePrev;
    boolean isSnip,isOOP;
    ArrayList<JMenuItem> snipList;

    JMenuItem undoMenuItem, redoMenuItem,saveMenuItem;
    JMenuItem tabbedMenuItem;
    public OOPSnip oopSnip;
    private OutputManager <String>outputManager,inputManager;
    private OutputManager <Boolean>previewManager,multipleManager;
    public JavaSnippets javaSnip;
    public C_Plus_PlusSnippets CPlusPlusSnip;
    public HtmlSnippet htmlSnippet;

    PLanguage pLanguage;
    public ProgrammerHelper() {
        // Set up the frame
        setTitle("Programmer Little Helper");

         undoMenuItem = new JMenuItem("Undo");
         redoMenuItem = new JMenuItem("Redo");
         saveMenuItem = new JMenuItem("Save");
        tabbedMenuItem=new JMenuItem("add new tab");

        setSize(600, 700);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        // Add a window listener to capture the close event


        outputManager = new OutputManager(150,50);
        inputManager=new OutputManager(150,50);
        previewManager=new OutputManager<>(150,50);
        multipleManager=new OutputManager<>(150,50);


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
        htmlItem=new JRadioButtonMenuItem("Html");

        languageList.add(javaItem);
        languageList.add(cPlusPlusItem);
        languageList.add(htmlItem);
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
output=fileHandle.getOutput();

        outputManager.addToUndoStack(output);

        inputManager.addToUndoStack(input);
        previewManager.addToUndoStack(livePrev);
        multipleManager.addToUndoStack(multi);

        edits.add(saveMenuItem);
        edits.add(undoMenuItem);
        edits.add(redoMenuItem);
        edits.add(tabbedMenuItem);


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

        }else if(lan.equals(Language.HTML.name())) {
            htmlItem.setSelected(true);

            this.language = Language.HTML;
            prevLanguage= this.language;
        }
        else{
                OOPLanguage = new Java();//intializing
                javaSnip = new JavaSnippets(OOPLanguage, this, input, multi, livePrev);
                javaItem.setSelected(false);
            }



        switch (snippet) { //to do
            case "getSetItem" -> {oopSnip=OOPSnip.GETSETSNIP;
                getSetItem.setSelected(true);
            }
            case "classItem" -> {oopSnip=OOPSnip.CLASSSNIP;
                classItem.setSelected(true);
            }
            case "mainClass" -> {oopSnip=OOPSnip.MAINCLASSSNIP;
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
        htmlItem.addActionListener(this);

        undoMenuItem.addActionListener(this);
        redoMenuItem.addActionListener(this);
        saveMenuItem.addActionListener(this);

        ActionListener languageGroupListener = eventHandler.createLanguageGroupListener(languageList);
        javaItem.addActionListener(languageGroupListener);
        cPlusPlusItem.addActionListener(languageGroupListener);
        htmlItem.addActionListener(languageGroupListener);



        getSetItem.addActionListener(this);
        classItem.addActionListener(this);
        mainClass.addActionListener(this);


        ActionListener OOPSnipGroupListener = eventHandler.createSnipGroupListener(snipList);
        getSetItem.addActionListener(OOPSnipGroupListener);
        classItem.addActionListener(OOPSnipGroupListener);
        mainClass.addActionListener(OOPSnipGroupListener);

        // Create a JTabbedPane
        tabbedPane = new JTabbedPane();

        flagFirstActivity = false;
        //////////activate first snip////////

        //testing
//htmlItem.setSelected(true);
//        this.language = Language.HTML;
//        //testing

        if (javaItem.isSelected() && javaSnip == null && !flagFirstActivity) { //one time activation if its saved in a file (extra &&flagFirstActivity==false)
            OOPLanguage = new Java();
            isSnip=false;


            javaSnip = new JavaSnippets(OOPLanguage, this, input, multi, livePrev);

            createSnippet();
        javaSnip.setOutput(outputManager.getUndoStack());
            addNewTab(javaSnip);




        } else if (cPlusPlusItem.isSelected() && CPlusPlusSnip == null && !flagFirstActivity) {


            OOPLanguage = new C_Plus_Plus();
            isSnip=false;

            CPlusPlusSnip = new C_Plus_PlusSnippets(OOPLanguage, this, input, multi, livePrev);
            CPlusPlusSnip.setOutput(outputManager.getUndoStack());
            createSnippet();

           addNewTab(CPlusPlusSnip);


        } else if (htmlItem.isSelected()) {
            htmlSnippet=new HtmlSnippet(this,input,multi,livePrev);
            htmlSnippet.setOutput(outputManager.getUndoStack());
            htmlSnippet.htmlSnip();

            addNewTab(htmlSnippet);
        }


        // Add the JTabbedPane to the main frame
        getContentPane().add(tabbedPane, BorderLayout.CENTER);

        setVisible(true);
    }



    //main

    public static void main(String[] args) {

        SwingUtilities.invokeLater(ProgrammerHelper::new);

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
            multipleManager.undo();
            previewManager.undo();

            isNavigating=true;
        }
        else if (e.getSource() == redoMenuItem) {
            outputManager.redo(); // Call the redo method of the outputManager
            inputManager.redo();
            multipleManager.redo();
            previewManager.redo();

            isNavigating=true;
        }
        else if (e.getSource()==saveMenuItem) {
            addCurrentAction();
        }


        //check language choice
        if (e.getSource() == javaItem) {
            if (!isOOP) {
                snipList.add(getSetItem);
                snipList.add(classItem);
                snipList.add(mainClass);

                for(JMenuItem snip:snipList) {
                    snippetsMenu.add(snip);
                    repaint();
                    revalidate();
                }

            }
            isSnip=true;
            isOOP=true;

            prevLanguage= language;

            OOPLanguage = new Java();
            language = OOPLanguage.getLanguageType();

            addCurrentAction();

            updateTab();

            createSnippet();

            System.out.println("language "+language+"prev language"+prevLanguage);

            sendOOPPreferences(language);


        } else if (e.getSource() == cPlusPlusItem) {
            if (!isOOP) {
                snipList.add(getSetItem);
                snipList.add(classItem);
                snipList.add(mainClass);

                for(JMenuItem snip:snipList) {
                    snippetsMenu.add(snip);
                    repaint();
                    revalidate();
                }

            }
            isSnip=true;
            isOOP=true;

            prevLanguage= language;

            OOPLanguage = new C_Plus_Plus();
            language = OOPLanguage.getLanguageType();

            addCurrentAction();
            updateTab();

            createSnippet();

            sendOOPPreferences(language);

        } else if (e.getSource()==htmlItem) {
            if(isOOP){

                for(JMenuItem snip:snipList) {
                    snippetsMenu.remove(snip);
                    repaint();
                    revalidate();
                }
            }
isOOP=false;
            prevLanguage= language;

            pLanguage=new Html(Language.HTML);
            language= pLanguage.getLanguageType();

            addCurrentAction();
            updateTab();

htmlSnippet.htmlSnip();
            sendOOPPreferences(language);

        }


        if (isOOP) { //if language is OOP language

            if(e.getSource() == getSetItem || e.getSource() == classItem || e.getSource() == mainClass) {
                addCurrentAction();

            }

            if (e.getSource() == getSetItem) { //common between c++ and java
                snippet = "getSetItem";

            oopSnip=OOPSnip.GETSETSNIP;

                isSnip=true;
                updateTab();

                createSnippet();


            } else if (e.getSource() == classItem) {
                snippet = "classItem";
                oopSnip=OOPSnip.CLASSSNIP;
                isSnip=true;
                updateTab();

                createSnippet();


            } else if (e.getSource() == mainClass) {
                snippet = "mainClass";

                oopSnip=OOPSnip.MAINCLASSSNIP;
                isSnip=true;

                updateTab();

                createSnippet();


            }



            OOPLastAction = e;
        }
        if (e.getSource()==undoMenuItem||e.getSource()==redoMenuItem||e.getSource() == getSetItem || e.getSource() == classItem || e.getSource() == mainClass) { //set Text when entering from panel to panel

            sendOOPPreferences(language);

            System.out.println("text :" + input);
        }
isNavigating=false;
        //end if
        LastAction=e;
    }
    private void addCurrentAction(){
        outputManager.addToUndoStack(getCurrentOutput());
        inputManager.addToUndoStack(getCurrentInput());
        previewManager.addToUndoStack(getCurrentPreview());
        multipleManager.addToUndoStack(getCurrentMultiInput());
    }
    private JTabbedPane tabbedPane;
    private void addNewTab(JPanel panel) {


        tabbedPane.addTab("tabTitle", panel);


    }
private void updateTab(){
    //tabbedPane.remove(removeFromFrame());
    removeFromFrame();
    removeSelectedTab();
    tabbedPane.add("update tap "+ language,addOOPSnip(language));
}
    private void removeSelectedTab() {
        int selectedIndex = tabbedPane.getSelectedIndex();
        if (selectedIndex != -1) {
            tabbedPane.removeTabAt(selectedIndex);
        }
    }

    public void sendOOPPreferences(Language language1){

        switch (language1){

            case CPLUSPLUS -> {
                    CPlusPlusSnip.setUserInput(inputManager.getUndoStack());
                    CPlusPlusSnip.setOutput(outputManager.getUndoStack());
                if(previewManager.getUndoStack()!=null)CPlusPlusSnip.setPrevBox(previewManager.getUndoStack());
                if(multipleManager.getUndoStack()!=null)CPlusPlusSnip.setMultipleInputs(multipleManager.getUndoStack());

            }
            case HTML -> {
                htmlSnippet.setUserInput(inputManager.getUndoStack());
                htmlSnippet.setOutput(outputManager.getUndoStack());

                if(previewManager.getUndoStack()!=null)htmlSnippet.setPrevBox(previewManager.getUndoStack());
                if(multipleManager.getUndoStack()!=null)htmlSnippet.setMultipleInputs(multipleManager.getUndoStack());

            }
            default -> {
                javaSnip.setUserInput(inputManager.getUndoStack());
                javaSnip.setOutput(outputManager.getUndoStack());

                    if(previewManager.getUndoStack()!=null)javaSnip.setPrevBox(previewManager.getUndoStack());
                if(multipleManager.getUndoStack()!=null)javaSnip.setMultipleInputs(multipleManager.getUndoStack());

            }
        }

    }
    public JPanel addOOPSnip(Language lan) {

        switch (lan) {
            case JAVA -> {System.out.println("java intializing");
               return javaSnip = new JavaSnippets(OOPLanguage, this, input, multi, livePrev);
            }
            case CPLUSPLUS -> {System.out.println("c++ intializing");
               return CPlusPlusSnip = new C_Plus_PlusSnippets(OOPLanguage, this, input, multi, livePrev);
            }
            case HTML -> {return htmlSnippet = new HtmlSnippet(this, input, multi, livePrev);}

            default -> {return javaSnip = new JavaSnippets(OOPLanguage, this, input, multi, livePrev);}
        }

    }

    public JPanel removeFromFrame() {

        System.out.println("\n\n");
        System.out.println("isSnip "+isSnip+"\n");
        System.out.println("prevLanguage "+prevLanguage);
        System.out.println("Language "+ language);


        switch (language){
            case CPLUSPLUS -> {
                if(CPlusPlusSnip!=null)remove(CPlusPlusSnip);
                revalidate();
                repaint();
                return CPlusPlusSnip;
            }
            case HTML -> {
                if(htmlSnippet!=null)remove(htmlSnippet);
                revalidate();
                repaint();
                return htmlSnippet;
            }
            default -> {//java
                if(javaSnip!=null)remove(javaSnip);
                revalidate();
                repaint();
                return javaSnip;}
        }


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

        previewManager.addToUndoStack(livePrev);
        multipleManager.addToUndoStack(multi);

        outputManager.getSize();

    }



    private void performLastAction() {//saving properties
        fileHandle.updateJSONFile(filePath, language, snippet, input, output,livePrev, multi);
    }


    public void createSnippet() {

        switch (language) {
            case JAVA -> {eventHandler.handleJavaSnippet(oopSnip,javaSnip);}
            case CPLUSPLUS -> {eventHandler.handleCPlusPlusSnippet(oopSnip,CPlusPlusSnip);}
        }
    }

    private String getCurrentOutput() {
        switch (prevLanguage) {
            case CPLUSPLUS -> {
                return CPlusPlusSnip.getTextPaneText();
            }
            case HTML -> {
                return htmlSnippet.getTextPaneText();
            }
            default -> {
                return javaSnip.getTextPaneText();
            }
        }
        //    output = javaSnip.getTextPaneText();
//    output = CPlusPlusSnip.getTextPaneText();

    }

    private String getCurrentInput(){
        switch (prevLanguage) {
            case CPLUSPLUS -> {
                return CPlusPlusSnip.getInputText();
            }
            case HTML -> {
                return htmlSnippet.getInputText();
            }
            default -> {
                return javaSnip.getInputText();
            }
        }
    }

    private boolean getCurrentPreview(){
        switch (prevLanguage) {
            case CPLUSPLUS -> {
                return CPlusPlusSnip.isLivePrev();
            }
            case HTML -> {
                return htmlSnippet.isLivePrev();
            }
            default -> {
                 return javaSnip.isLivePrev();
            }
        }
    }

    private boolean getCurrentMultiInput(){
        switch (prevLanguage) {
            case CPLUSPLUS -> {
                return CPlusPlusSnip.isMultiInputs();
            }
            case HTML -> {
               return htmlSnippet.isMultiInputs();
            }
            default -> {
                return javaSnip.isMultiInputs();
            }
        }
    }

}