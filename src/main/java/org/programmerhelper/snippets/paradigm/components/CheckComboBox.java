/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.programmerhelper.snippets.paradigm.components;

import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JPanel;

/**
 *
 * @author ahmed
 */
public class CheckComboBox extends JPanel{
  
private final JComboBox<String> comboBox;

        public CheckComboBox(String[] items) {
            comboBox = new JComboBox<>(items);
            add(comboBox);
        }

        public String getSelectedItem() {
            return comboBox.getSelectedItem().toString();
        }

        public void addActionListener(ActionListener listener) {
            comboBox.addActionListener(listener);
        }  
}
