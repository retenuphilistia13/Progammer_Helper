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

import java.awt.*;
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

    EventHandler eventHandler;
    public ArrayList<JavaSnippets> javaSnipList;
    public ArrayList<C_Plus_PlusSnippets> CPlusPlusSnipList;
    JMenuBar menuBar;
    JMenu snippetsMenu, languageMenu;
    JMenu edits;
    JMenuItem showCheckboxes;
    JMenuItem getSetItem, classItem, mainClass;
    JMenuItem javaItem, cPlusPlusItem;
    boolean flagFirstActivity;
    private final JSONFileHandler fileHandle;
    private final String filePath;
    Language language, prevLanguage;
    private String snippet, input, output;
    private boolean multi, livePrev;
    boolean isSnip, isOOP;
    ArrayList<JMenuItem> snipList;

    JMenuItem undoMenuItem, redoMenuItem, saveMenuItem;
    JMenuItem tabbedMenuItem;
    public OOPSnip oopSnip;
    private OutputManager<String> outputManager, inputManager;
    private OutputManager<Boolean> previewManager, multipleManager;
    //private OutputManager<ActionEvent> actionManager;
    private JTabbedPane tabbedPane;
    public ProgrammerHelper() {
        // Set up the frame
        setTitle("Programmer Little Helper");

        undoMenuItem = new JMenuItem("Undo");
        redoMenuItem = new JMenuItem("Redo");
        saveMenuItem = new JMenuItem("Save");
        tabbedMenuItem = new JMenuItem("add new tab");

        setSize(600, 700);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        // Add a window listener to capture the close event


        outputManager = new OutputManager<>(150, 50);
        inputManager = new OutputManager<>(150, 50);
        previewManager = new OutputManager<>(150, 50);
        multipleManager = new OutputManager<>(150, 50);
        //actionManager=new OutputManager(150,50);

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


        eventHandler = new EventHandler();
        menuBar = new JMenuBar();

        edits = new JMenu("Edits");

        snippetsMenu = new JMenu("Snippets");
        languageMenu = new JMenu("Language");


        // Create the menu items
        ArrayList<JMenuItem> languageList = new ArrayList<>();

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
            prevLanguage = this.language;

        } else if (lan.equals(Language.CPLUSPLUS.name())) {
            cPlusPlusItem.setSelected(true);
            isOOP = true;

            this.language = Language.CPLUSPLUS;
            prevLanguage = this.language;

        } else {
            OOPLanguage = new Java();//intializing
            javaSnipList = new ArrayList<>();
            javaSnipList.add(new JavaSnippets(OOPLanguage, this, input, multi, livePrev));
            javaItem.setSelected(false);
        }

        switch (snippet) { //to do
            case "getSetItem" -> {
                oopSnip = OOPSnip.GETSETSNIP;
                getSetItem.setSelected(true);
            }
            case "classItem" -> {
                oopSnip = OOPSnip.CLASSSNIP;
                classItem.setSelected(true);
            }
            case "mainClass" -> {
                oopSnip = OOPSnip.MAINCLASSSNIP;
                mainClass.setSelected(true);
            }
            default -> {
                classItem.setSelected(true);
            }
        }

        for (JMenuItem lanList : languageList) {
            languageMenu.add(lanList);
        }

        menuBar.add(languageMenu); //add to bar

        //if ooP language

        if (isOOP) {
            snipList.add(getSetItem);
            snipList.add(classItem);
            snipList.add(mainClass);

            for (JMenuItem snip : snipList) snippetsMenu.add(snip);
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

        // Create a JTabbedPane
        tabbedPane = new JTabbedPane();
        flagFirstActivity = false;
        //////////activate first snip////////
        outputManager.addToUndoStack(" ");

        inputManager.addToUndoStack(input);
        previewManager.addToUndoStack(livePrev);
        multipleManager.addToUndoStack(multi);

        javaSnipList = new ArrayList<>();
        CPlusPlusSnipList = new ArrayList<>();

        if (javaItem.isSelected() && javaSnipList.isEmpty() && !flagFirstActivity) {
            OOPLanguage = new Java();
            isSnip = false;

            JavaSnippets javaSnip = new JavaSnippets(OOPLanguage, this, input, multi, livePrev);
            javaSnipList.add(javaSnip);

            createSnippet();

            addNewTab(javaSnipList.get(0));
        }
        else if (cPlusPlusItem.isSelected() && CPlusPlusSnipList == null && !flagFirstActivity) {


            OOPLanguage = new C_Plus_Plus();
            isSnip=false;

            C_Plus_PlusSnippets CPlusPlusSnip = new C_Plus_PlusSnippets(OOPLanguage, this, input, multi, livePrev);
            CPlusPlusSnipList.add(CPlusPlusSnip);

            createSnippet();

            addNewTab(CPlusPlusSnipList.get(0));

            //addToFrame();
        }



        // Add the JTabbedPane to the main frame
        getContentPane().add(tabbedPane, BorderLayout.CENTER);

        setVisible(true);
    }

    static class CloseableTabComponent extends JPanel {
        private final JTabbedPane tabbedPane;
        private final int tabIndex;

        public CloseableTabComponent(JTabbedPane tabbedPane, int tabIndex) {
            super(new BorderLayout());
            this.tabbedPane = tabbedPane;
            this.tabIndex = tabIndex;
            setOpaque(false);

            // Create label for tab title
            JLabel titleLabel = new JLabel(tabbedPane.getTitleAt(tabIndex));
            add(titleLabel, BorderLayout.CENTER);

            // Create close button

            JButton closeButton = new JButton("X");
            closeButton.setPreferredSize(new Dimension(12, 12));
            //closeButton.setIcon(new ImageIcon("close_icon.png")); // Replace "close_icon.png" with your actual icon path
            closeButton.setMargin(new Insets(0, 0, 0, 0));

            closeButton.addActionListener(e -> tabbedPane.removeTabAt(tabIndex));
            add(closeButton, BorderLayout.LINE_END);
        }
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
            outputManager.addToUndoStack(getCurrentOutput());
            inputManager.addToUndoStack(getCurrentInput());
            previewManager.addToUndoStack(getCurrentPreview());
            multipleManager.addToUndoStack(getCurrentMultiInput());

        }


        //check language choice
        if (e.getSource() == javaItem) {
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
            isSnip=true;
            isOOP=true;

            prevLanguage= language;

            OOPLanguage = new C_Plus_Plus();
            language = OOPLanguage.getLanguageType();

            addCurrentAction();
            updateTab();

            createSnippet();

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

            if (e.getSource()==undoMenuItem||e.getSource()==redoMenuItem||e.getSource() == getSetItem || e.getSource() == classItem || e.getSource() == mainClass) { //set Text when entering from panel to panel

                sendOOPPreferences(language);

                System.out.println("text :" + input);
            }

            OOPLastAction = e;
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


    private void addNewTab(JPanel panel) {
        tabbedPane.addTab("tabTitle", panel);
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            tabbedPane.setTabComponentAt(i, new CloseableTabComponent(tabbedPane, i));
        }
    }
    private void updateTab(){
        //tabbedPane.remove(removeFromFrame());
        removeFromFrame();
        removeSelectedTab();
        tabbedPane.add("update tap",addOOPSnip(language));
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            tabbedPane.setTabComponentAt(i, new CloseableTabComponent(tabbedPane, i));
        }
    }
    private void removeSelectedTab() {
        int selectedIndex = tabbedPane.getSelectedIndex();
        if (selectedIndex != -1) {
            tabbedPane.removeTabAt(selectedIndex);
        }
    }

    public void sendOOPPreferences(Language language1) {
        switch (language1) {
            case JAVA:
                if (!javaSnipList.isEmpty()) {
                    javaSnipList.get(0).setOutput(outputManager.getUndoStack());
                    javaSnipList.get(0).setUserInput(inputManager.getUndoStack());
                    javaSnipList.get(0).setMultipleInputs(multipleManager.getUndoStack());
                    javaSnipList.get(0).setPrevBox(previewManager.getUndoStack());

                }
                break;
            case CPLUSPLUS:
                if (!CPlusPlusSnipList.isEmpty()) {
                    CPlusPlusSnipList.get(0).setUserInput(inputManager.getUndoStack());
                    CPlusPlusSnipList.get(0).setOutput(outputManager.getUndoStack());
                    CPlusPlusSnipList.get(0).setPrevBox(previewManager.getUndoStack());
                    CPlusPlusSnipList.get(0).setMultipleInputs(multipleManager.getUndoStack());

                }
                break;
        }
    }


    public JPanel addOOPSnip(Language lan) {
        switch (lan) {
            case JAVA:
                System.out.println("java initializing");
                JavaSnippets javaSnip = new JavaSnippets(OOPLanguage, this, input, multi, livePrev);
                javaSnipList.add(javaSnip);
                return javaSnipList.get(0);
            case CPLUSPLUS:
                System.out.println("c++ initializing");
                C_Plus_PlusSnippets CPlusPlusSnip = new C_Plus_PlusSnippets(OOPLanguage, this, input, multi, livePrev);
                CPlusPlusSnipList.add(CPlusPlusSnip);
                return CPlusPlusSnipList.get(0);
            default:
                System.out.println("java initializing (default)");
                JavaSnippets javaSnipt = new JavaSnippets(OOPLanguage, this, input, multi, livePrev);
                javaSnipList.add(javaSnipt);
                return javaSnipList.get(0);
        }
    }


    public JPanel removeFromFrame() {
        System.out.println("\n\n");
        System.out.println("isSnip " + isSnip + "\n");
        System.out.println("prevLanguage " + prevLanguage);
        System.out.println("Language " + language);

        Language lan;

        if (!isSnip) {
            lan = prevLanguage;
            System.out.println("prev language executed");
        } else {
            System.out.println("language executed");
            lan = language;
        }

        JPanel removedPanel = null;

        switch (language) {
            case JAVA -> {
                if (!javaSnipList.isEmpty()) {
                    removedPanel = javaSnipList.remove(0);
                    remove(removedPanel);
                }
            }
            case CPLUSPLUS-> {
                if (!CPlusPlusSnipList.isEmpty()) {
                    removedPanel = CPlusPlusSnipList.remove(0);
                    remove(removedPanel);
                }
            }

            default-> {
                if (!javaSnipList.isEmpty()) {
                    removedPanel = javaSnipList.remove(0);
                    remove(removedPanel);
                }
            }

        }

        revalidate();
        repaint();

        return removedPanel;
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
        fileHandle.updateJSONFile(filePath, language, snippet, input, livePrev, multi);
    }


    public void createSnippet() {

        switch (language) {
            case JAVA -> {eventHandler.handleJavaSnippet(oopSnip,javaSnipList.get(0));}
            case CPLUSPLUS -> {eventHandler.handleCPlusPlusSnippet(oopSnip,CPlusPlusSnipList.get(0));}
        }
    }

    private String getCurrentOutput() {
        switch (prevLanguage) {
            case JAVA -> {
                return javaSnipList.get(0).getTextPaneText();
            }
            case CPLUSPLUS -> {
                return CPlusPlusSnipList.get(0).getTextPaneText();
            }
            default -> {
                return javaSnipList.get(0).getTextPaneText();
            }
        }
        //    output = javaSnip.getTextPaneText();
//    output = CPlusPlusSnip.getTextPaneText();

    }

    private String getCurrentInput(){
        switch (prevLanguage) {
            case JAVA -> {
                return javaSnipList.get(0).getInputText();
            }
            case CPLUSPLUS -> {
                return CPlusPlusSnipList.get(0).getInputText();
            }
            default -> {
                return javaSnipList.get(0).getInputText();
            }
        }
    }

    private boolean getCurrentPreview(){
        switch (prevLanguage) {
            case JAVA -> {
                return javaSnipList.get(0).isLivePrev();
            }
            case CPLUSPLUS -> {
                return CPlusPlusSnipList.get(0).isLivePrev();
            }
            default -> {
                return javaSnipList.get(0).isLivePrev();
            }
        }
    }

    private boolean getCurrentMultiInput(){
        switch (prevLanguage) {
            case JAVA -> {
                return javaSnipList.get(0).isMultiInputs();
            }
            case CPLUSPLUS -> {
                return CPlusPlusSnipList.get(0).isMultiInputs();
            }
            default -> {
                return javaSnipList.get(0).isMultiInputs();
            }
        }
    }

}