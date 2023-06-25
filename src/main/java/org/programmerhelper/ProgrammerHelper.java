/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package org.programmerhelper;

/**
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

import org.programmerhelper.snippets.paradigm.OOPSnippets;
import org.programmerhelper.snippets.paradigm.PanelListener;
import org.programmerhelper.snippets.paradigm.language.C_Plus_PlusSnippets;
import org.programmerhelper.snippets.paradigm.language.JavaSnippets;

import java.io.File;
import java.util.ArrayList;


public class ProgrammerHelper extends JFrame implements ActionListener, PanelListener {
    ActionEvent OOPLastAction;
    OOPLanguage OOPLanguage;
    //OOPSnippets OOPSnip;

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
    Language Language;
    Language prevLanguage;
    private String  snippet,input;
    private boolean multi,livePrev;

    boolean isSnip;
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



        menuBar = new JMenuBar();

         setting = new JMenu("Settings");

        showCheckboxes=new JCheckBoxMenuItem("show preferences");
        showCheckboxes.setSelected(true);

        setting.add(showCheckboxes);
        menuBar.add(setting);

        snippetsMenu = new JMenu("Snippets");
        languageMenu = new JMenu("Language");


        // Create the menu items
        javaItem = new JRadioButtonMenuItem("Java");
        cPlusPlusItem = new JRadioButtonMenuItem("C++");

        //add languages
        getSetItem = new JMenuItem("getters and setters");
        classItem = new JMenuItem("class");
        mainClass = new JMenuItem("main class");

        // System.out.println(classItem.getText());

        ///////assigning, reading fro file////////
        File f = new File("commonPrefrences.json");
        filePath = f.getAbsolutePath(); ///get absolute path

        fileHandle = new JSONFileHandler();
        fileHandle.readJSONFile(filePath);

        String language;
        //to do make pardaigm in json
        language = fileHandle.getLanguage();

        snippet = fileHandle.getSnippet();
        livePrev = fileHandle.getLivePrevBox(); //get value form file
        multi = fileHandle.getMultipleInputBox();
        input = fileHandle.getInputText();

        System.out.println("language: " + language.equals("C++") + " real:" + language);
        String paradigm = ""; //temporary
        if (language.equals(Language.JAVA.name())) {
            javaItem.setSelected(true);
            Language= Language.JAVA;
            prevLanguage=Language;
            paradigm = "OOP";
        } else if (language.equals(Language.CPLUSPLUS.name())) {
            cPlusPlusItem.setSelected(true);
            Language= Language.CPLUSPLUS;
            prevLanguage=Language;
            paradigm = "OOP";
        }
                 else if (language.isBlank()) {
                    OOPLanguage = new Java();//intializing
            javaSnip = new JavaSnippets(OOPLanguage, this, input, multi, livePrev);
                    javaItem.setSelected(false);
                } else {
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

        languageMenu.add(javaItem);
        languageMenu.add(cPlusPlusItem);

        menuBar.add(languageMenu); //add to bar

        //if ooP language
        if (paradigm.equals("OOP")) {
            snippetsMenu.add(getSetItem);
            snippetsMenu.add(classItem);
            snippetsMenu.add(mainClass);

            menuBar.add(snippetsMenu);
        }

        //////set to jmenubar  frame//////
        setJMenuBar(menuBar);
        add(menuBar, BorderLayout.NORTH);
        //////////listners////////
        javaItem.addActionListener(this);
        cPlusPlusItem.addActionListener(this);

        ActionListener languageGroupListener = createLanguageGroupListener();
        javaItem.addActionListener(languageGroupListener);
        cPlusPlusItem.addActionListener(languageGroupListener);

        getSetItem.addActionListener(this);
        classItem.addActionListener(this);
        mainClass.addActionListener(this);

        ActionListener OOPSnipGroupListener = createSnipGroupListener();
        getSetItem.addActionListener(OOPSnipGroupListener);
        classItem.addActionListener(OOPSnipGroupListener);
        mainClass.addActionListener(OOPSnipGroupListener);

        flagFirstActivity = false;
        //////////activate first snip////////
        if (javaItem.isSelected() && javaSnip == null && flagFirstActivity == false) { //one time activation if its saved in a file (extra &&flagFirstActivity==false)
            OOPLanguage = new Java();
            isSnip=false;
            language = OOPLanguage.getLanguageType().name();
            javaSnip = new JavaSnippets(OOPLanguage, this, input, multi, livePrev);
            firstSnippet();
            addToFrame();

        } else if (cPlusPlusItem.isSelected() && CPlusPlusSnip == null && flagFirstActivity == false) {
            OOPLanguage = new C_Plus_Plus();
            isSnip=false;
            language = OOPLanguage.getLanguageType().name();
            CPlusPlusSnip = new C_Plus_PlusSnippets(OOPLanguage, this, input, multi, livePrev);

            firstSnippet();
            addToFrame();
        }



        setVisible(true);
    }



    //main

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new ProgrammerHelper();
        });

    }



    @Override

    public void actionPerformed(ActionEvent e) {

        if (javaItem.isSelected() || cPlusPlusItem.isSelected()) { //if one of OOP language
            if (OOPLastAction != null) { //after last action is intialized
                flagFirstActivity = true;
            }

            //check language choice
            if (e.getSource() == javaItem) {
                isSnip=false;
                prevLanguage=Language;
                OOPLanguage = new Java();

                Language = OOPLanguage.getLanguageType();

                if (!flagFirstActivity) e = handleOOPFirstEvent(e);

                if (OOPLastAction != null) {
                    if (OOPLastAction.getSource() == getSetItem || OOPLastAction.getSource() == classItem || OOPLastAction.getSource() == mainClass) {
                        e = OOPLastAction; //refrech when user alternate between the two language; the last action(getters setters would be saved)
                    }
                }

            } else if (e.getSource() == cPlusPlusItem) {
                isSnip=false;
                prevLanguage=Language;
                OOPLanguage = new C_Plus_Plus();

                Language = OOPLanguage.getLanguageType();

                if (!flagFirstActivity) e = handleOOPFirstEvent(e);

                if (OOPLastAction != null) { //lastAction after is setted
                    if (OOPLastAction.getSource() == getSetItem || OOPLastAction.getSource() == classItem || OOPLastAction.getSource() == mainClass) { //set action (save)if another oop items are selected
                         e = OOPLastAction;
                    }
                }

            }

            if (e.getSource() == getSetItem) { //common between c++ and java
                snippet = "getSetItem";


                removeFromFrame();
                isSnip=true;

                addOOPSnip(Language); //create new panel (must do)
                firstSnippet(); //assign snippets Jpanel
                addToFrame();

            } else if (e.getSource() == classItem) {
                snippet = "classItem";


                removeFromFrame();
                isSnip=true;

                addOOPSnip(Language); // addOOPSnip(Language);//create new panel (must do)
                firstSnippet();

                addToFrame();

            } else if (e.getSource() == mainClass) {
                snippet = "mainClass";


                removeFromFrame();
                isSnip=true;
                addOOPSnip(Language); //  addOOPSnip(Language);
                firstSnippet();

                addToFrame();

            }

            if (e.getSource() == getSetItem || e.getSource() == classItem || e.getSource() == mainClass) { //set Text when entering from panel to panel
                switch (Language){
                    case JAVA -> {
                        javaSnip.setText(input);
                        javaSnip.setPrevBox(livePrev);
                        javaSnip.setMultipleInputs(multi);}
                    case CPLUSPLUS -> {
                        CPlusPlusSnip.setText(input);
                        CPlusPlusSnip.setPrevBox(livePrev);
                        CPlusPlusSnip.setMultipleInputs(multi);
                    }
                }
                System.out.println("text :" + input);
            }

            OOPLastAction = e;
        }
        //end if
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
        switch (Language){
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
        System.out.println("Language "+Language);

Language lan;

        if(isSnip==false){
            lan=prevLanguage;
            System.out.println("prevlanguage excuted");
        }else if(isSnip==true){
            System.out.println("language excuted");
            lan=Language;
        }else {
            System.out.println("what language to remove?");
            lan=Language;
        }

        switch (lan){//need to fix later the logic(scale up for other language) suggetion take previous language instead of current
            case JAVA -> {System.out.println(" java Snip option");
                if(javaSnip!=null) {
                    remove(javaSnip);
                    System.out.println("removed java Snip");
                }
            }
            case CPLUSPLUS -> { System.out.println("CPlusPlusSnip called ");   if(CPlusPlusSnip!=null) {
                System.out.println("removed CPlusPlusSnip ");                remove(CPlusPlusSnip);
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



    public void firstSnippet() {
        switch (Language) {
            case JAVA -> {
                switch (snippet) {
                    case "getSetItem" -> {
                        javaSnip.gettersSetters();
                    }

                    case "mainClass" -> {
                        javaSnip.createMainClass();
                    }

                    case "classItem" -> {
                        javaSnip.createClass();
                    }

                    default -> {
                        System.out.print("snippet default " + snippet);
                        javaSnip.createClass();
                    }
                }


            }
            case CPLUSPLUS -> {
                switch (snippet) {
                    case "getSetItem" -> {
                        CPlusPlusSnip.gettersSetters();
                    }

                    case "mainClass" -> {
                        CPlusPlusSnip.createMainClass();
                    }

                    case "classItem" -> {
                        CPlusPlusSnip.createClass();
                    }

                    default -> {
                        System.out.print("snippet default " + snippet);
                        CPlusPlusSnip.createClass();
                    }
                }


            }

                }
            }





    public ActionEvent handleOOPFirstEvent(ActionEvent e) {
        switch (snippet) {
            case "getSetItem" -> {
                return new ActionEvent(getSetItem, e.getID(), e.getActionCommand(), e.getModifiers());
            }

            case "mainClass" -> {
                return new ActionEvent(mainClass, e.getID(), e.getActionCommand(), e.getModifiers());
            }

            case "classItem" -> {
                return new ActionEvent(classItem, e.getID(), e.getActionCommand(), e.getModifiers());
            }

            default -> {
                System.out.print("snippet default " + snippet);
                return new ActionEvent(classItem, e.getID(), e.getActionCommand(), e.getModifiers());
            }
        }


    }

    private void performLastAction() {
        //saving propertes
        fileHandle.updateJSONFile(filePath, Language, snippet, input, livePrev, multi);
    }
    private ActionListener createLanguageGroupListener() {
        // Initialize and group the menus
        ArrayList<JMenuItem> languageList = new ArrayList<>();

        languageList.add(javaItem);
        languageList.add(cPlusPlusItem);
        ButtonGroup group = new ButtonGroup();

        for (JMenuItem lang: languageList)group.add(lang);
        // Create and return the ActionListener
        return (ActionEvent event) -> {
            // Handle menu item selection
            JMenuItem selectedMenuItem = (JMenuItem) event.getSource();
            // Update the selected state of all menu items
            for (JMenuItem lang: languageList) {
                if (lang == selectedMenuItem) {
                    lang.setSelected(true);
                } else {
                    lang.setSelected(false);
                }
            }
            // Add more menu items and update their selected states accordingly
        };
    }
    //idea to automate boring tasks with saving a snip in a specfic language like java ex: userinput1.add(userinput2);
    private ActionListener createSnipGroupListener() {

        ArrayList<JMenuItem> snipList = new ArrayList<>();

        snipList.add(getSetItem);
        snipList.add(classItem);
        snipList.add(mainClass);

        ButtonGroup OOPGroup = new ButtonGroup();

        for (JMenuItem snips: snipList) {
            OOPGroup.add(snips);
        }

        return (ActionEvent event) -> {
            JMenuItem selectedMenuItem = (JMenuItem) event.getSource();
            // Update the selected state of all menu items
            for (JMenuItem snips: snipList) {
                if (snips == selectedMenuItem) snips.setSelected(true);
                else snips.setSelected(false);
            }

        };
    }



}