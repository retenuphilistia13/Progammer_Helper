/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.programmerhelper.snippets.paradigm.components;

import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author ahmed
 */
public class RadioPanel extends JPanel {
     private final  ArrayList<JRadioButton> radios;
     
     
private final ButtonGroup buttonGroup;
    public RadioPanel(ArrayList<JRadioButton> radioss, boolean x) {
        radios = radioss;

        if (x) {
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        } else {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        }

        buttonGroup = new ButtonGroup();
        // group buttons
        for (JRadioButton radioButton : radios) {
            buttonGroup.add(radioButton);
        }

        addRadioButtons(); // Add the initial radio buttons to the panel

        radios.get(0).setSelected(true);
    }

    private void addRadioButtons() {
        for (JRadioButton radioButton : radios) {
            add(radioButton);
        }
    }
}
