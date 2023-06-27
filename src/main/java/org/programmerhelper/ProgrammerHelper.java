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
    JMenu setting;
    JMenuItem showCheckboxes;
    JMenuItem getSetItem, classItem, mainClass;
    JMenuItem javaItem, cPlusPlusItem;
    boolean flagFirstActivity;
    private final JSONFileHandler fileHandle;
    private final String filePath;
    Language language;
    Language prevLanguage;
    private String  snippet,input, output;
    private boolean multi,livePrev;
    boolean isSnip;
    boolean isOOP;
    ArrayList<JMenuItem> snipList;
    public ProgrammerHelper() {
        // Set up the frame
        setTitle("Programmer Little Helper");

        setSize(600, 700);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        // Add a window listener to capture the close event
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

         setting = new JMenu("Settings");

        showCheckboxes=new JCheckBoxMenuItem("show preferences");
        showCheckboxes.setSelected(true);

        setting.add(showCheckboxes);
        menuBar.add(setting);

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
            case "getSetItem" ->
                    getSetItem.setSelected(true);
            case "classItem" ->
                    classItem.setSelected(true);
            case "mainClass" ->
                    mainClass.setSelected(true);
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

            javaSnip = new JavaSnippets(OOPLanguage, this, input, multi, livePrev);
            firstSnippet();
            addToFrame();

        } else if (cPlusPlusItem.isSelected() && CPlusPlusSnip == null && !flagFirstActivity) {
            OOPLanguage = new C_Plus_Plus();
            isSnip=false;
            CPlusPlusSnip = new C_Plus_PlusSnippets(OOPLanguage, this, input, multi, livePrev);

            firstSnippet();
            addToFrame();
        }


        setVisible(true);
    }



    //main

    public static void main(String[] args) {

        SwingUtilities.invokeLater(ProgrammerHelper::new);

    }



    @Override

    public void actionPerformed(ActionEvent e) {

        if (LastAction != null) { //after last action is intialized
            flagFirstActivity = true;
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

            if (e.getSource() == getSetItem) { //common between c++ and java
                snippet = "getSetItem";


                removeFromFrame();
                isSnip=true;

                addOOPSnip(language); //create new panel (must do)
                firstSnippet(); //assign snippets Jpanel
                addToFrame();

            } else if (e.getSource() == classItem) {
                snippet = "classItem";


                removeFromFrame();
                isSnip=true;

                addOOPSnip(language); // addOOPSnip(Language);//create new panel (must do)
                firstSnippet();

                addToFrame();

            } else if (e.getSource() == mainClass) {
                snippet = "mainClass";


                removeFromFrame();
                isSnip=true;

                addOOPSnip(language); //  addOOPSnip(Language);
                firstSnippet();

                addToFrame();

            }

            if (e.getSource() == getSetItem || e.getSource() == classItem || e.getSource() == mainClass) { //set Text when entering from panel to panel
                switch (language){
                    case JAVA -> {
                        javaSnip.setUserInput(input);
                        javaSnip.setPrevBox(livePrev);
                        javaSnip.setMultipleInputs(multi);
                        javaSnip.setOutput(output);
                        System.out.println("output "+output);
                    }
                    case CPLUSPLUS -> {
                        CPlusPlusSnip.setUserInput(input);
                        CPlusPlusSnip.setPrevBox(livePrev);
                        CPlusPlusSnip.setMultipleInputs(multi);
                        CPlusPlusSnip.setOutput(output);
                        System.out.println("output "+output);

                    }
                }
                System.out.println("text :" + input);
            }

            OOPLastAction = e;
        }
        //end if
        LastAction=e;
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

        switch (lan){//need to fix later the logic(scale up for other language) suggetion take previous language instead of current
            case JAVA -> {System.out.println(" java Snip option");
                if(javaSnip!=null) {
                    remove(javaSnip);
                    System.out.println("removed java Snip");
                }
            }
            case CPLUSPLUS -> { System.out.println("CPlusPlusSnip called ");
                if(CPlusPlusSnip!=null) {
                System.out.println("removed CPlusPlusSnip ");
                remove(CPlusPlusSnip);
            }}
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
    }



    public void firstSnippet() {
        switch (language) {
            case JAVA -> {
                handleJavaSnippet();
            }

            case CPLUSPLUS -> {
                handleCPlusPlusSnippet();
                }
        }
    }

    private void handleJavaSnippet() {
        switch (snippet) {
            case "getSetItem" -> javaSnip.gettersSetters();
            case "mainClass" -> javaSnip.createMainClass();
            default -> {
                System.out.print("snippet default " + snippet);
                javaSnip.createClass();
            }
        }
    }

    private void handleCPlusPlusSnippet() {
        switch (snippet) {
            case "getSetItem" -> CPlusPlusSnip.gettersSetters();
            case "mainClass" -> CPlusPlusSnip.createMainClass();
            default -> {
                System.out.print("snippet default " + snippet);
                CPlusPlusSnip.createClass();
            }
        }
    }

    private void performLastAction() {//saving properties
        fileHandle.updateJSONFile(filePath, language, snippet, input, livePrev, multi);
    }




}