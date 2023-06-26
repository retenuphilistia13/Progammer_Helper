package org.programmerhelper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class EventHandler {
    ActionListener createLanguageGroupListener(ArrayList<JMenuItem> languageList) {//set selected menu item to true while other menus false


        ButtonGroup group = new ButtonGroup();

        for (JMenuItem lang: languageList)group.add(lang);
        // Create and return the ActionListener
        return (ActionEvent event) -> {
            // Handle menu item selection
            JMenuItem selectedMenuItem = (JMenuItem) event.getSource();
            // Update the selected state of all menu items
            for (JMenuItem lang: languageList) {
                lang.setSelected(lang == selectedMenuItem);
            }
            // Add more menu items and update their selected states accordingly
        };
    }

    ActionListener createSnipGroupListener(ArrayList<JMenuItem> snipList) {

        ButtonGroup OOPGroup = new ButtonGroup();

        for (JMenuItem snips: snipList) {
            OOPGroup.add(snips);
        }

        return (ActionEvent event) -> {
            JMenuItem selectedMenuItem = (JMenuItem) event.getSource();
            // Update the selected state of all menu items
            for (JMenuItem snips: snipList) {
                snips.setSelected(snips == selectedMenuItem);
            }

        };
    }
    public ActionEvent handleOOPFirstEvent(ActionEvent e,ArrayList<JMenuItem>snipList,String snippet) {
        int snippetIndex = -1;  // Default index if snippet is not found

        // Find the index of the snippet in the snipList
        for (int i = 0; i < snipList.size(); i++) {
            if (snipList.get(i).getActionCommand().equals(snippet)) {
                snippetIndex = i;
                break;
            }
        }

        if (snippetIndex != -1) {
            JMenuItem selectedSnippet = snipList.get(snippetIndex);
            return new ActionEvent(selectedSnippet, e.getID(), e.getActionCommand(), e.getModifiers());
        } else {
            System.out.println("Snippet not found: " + snippet);
            return new ActionEvent(snipList.get(0), e.getID(), e.getActionCommand(), e.getModifiers());
        }


    }
}
