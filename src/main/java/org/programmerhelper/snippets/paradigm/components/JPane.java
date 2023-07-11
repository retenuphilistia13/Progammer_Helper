package org.programmerhelper.snippets.paradigm.components;



import java.awt.Color;
import javax.swing.*;
import javax.swing.text.*;


/**
 *inspired by @author saliya
 * github repository https://github.com/stark9000/java-Syntax-Highlight
 */

public class JPane extends JTextPane{
    private String[] keywordArray1 = {"int", "array", "bool", "boolean", "byte", "char", "double", "float", "long", "short", "String", "unsigned", "char", "unsigned", "int", "unsigned", "long", "void", "INPUT", "LOW", "HIGH", "OUTPUT", "INPUT_PULLUP", "LED_BUILTIN", "true", "false"};
    private String[] keywordArray2 = {"Serial", "begin", "WiFi", "print", "delay", "println", "ready", "size_t"};
    private String[] keywordArray3 = {"#", "define", "include", "setup", "else", "loop"};
    private String[] keywordArray4 = {"\".*\""};
    public JPane() {
        setDocument(doc);
    }

    public JPane(DefaultStyledDocument doc) {
        super(doc);
    }

    public JPane( String[] keywordArray1, String[] keywordArray2, String[] keywordArray3,String[] keywordArray4) {
        this.keywordArray1 = keywordArray1;
        this.keywordArray2 = keywordArray2;
        this.keywordArray3 = keywordArray3;
        this.keywordArray4 = keywordArray4;
        setDocument(doc);
    }

    @Override
    public void scrollRectToVisible(java.awt.Rectangle aRect) {
        // Override scrollRectToVisible() to prevent automatic scrolling
        // when typing at a specific line
        if (getParent() instanceof JScrollPane scrollPane) {
            scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getValue());
        }
    }
    // End of variables declaration//GEN-END:variables
    private int findLastNonWordChar(String text, int index) {
        while (--index >= 0) {
            if (String.valueOf(text.charAt(index)).matches("\\W")) {
                break;
            }
        }
        return index;
    }

    private int findFirstNonWordChar(String text, int index) {
        while (index < text.length()) {
            if (String.valueOf(text.charAt(index)).matches("\\W")) {
                break;
            }
            index++;
        }
        return index;
    }

    final StyleContext cont = StyleContext.getDefaultStyleContext();
    final AttributeSet keyw1 = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(23, 161, 165));//23,161,165 || 211 84 0 || 94,109,3
    final AttributeSet keyw2 = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(211, 84, 0));
    final AttributeSet keyw3 = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(94, 109, 3));
    final AttributeSet keyw4 = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.red);//new Color(0, 92, 95)
    final AttributeSet attrBlack = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLACK);
    DefaultStyledDocument doc = new DefaultStyledDocument() {


        @Override
        public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
            super.insertString(offset, str, a);

            String text = getText(0, getLength());
            int before = findLastNonWordChar(text, offset);
            if (before < 0) {
                before = 0;
            }
            int after = findFirstNonWordChar(text, offset + str.length());
            int wordL = before;
            int wordR = before;

            while (wordR <= after) {
                if (wordR == after || String.valueOf(text.charAt(wordR)).matches("\\W")) {
                    String word = text.substring(wordL, wordR);
                    if (containsKeyword(word, keywordArray1)) {
                        setCharacterAttributes(wordL, wordR - wordL, keyw1, false);
                    } else if (containsKeyword(word, keywordArray2)) {
                        setCharacterAttributes(wordL, wordR - wordL, keyw2, false);
                    } else if (containsKeyword(word, keywordArray3)) {
                        setCharacterAttributes(wordL, wordR - wordL, keyw3, false);
                    } else if (containsKeyword(word, keywordArray4)) {
                        setCharacterAttributes(wordL, wordR - wordL, keyw4, false);
                    } else {
                        setCharacterAttributes(wordL, wordR - wordL, attrBlack, false);
                    }
                    wordL = wordR;
                }
                wordR++;
            }
        }

        @Override
        public void remove(int offs, int len) throws BadLocationException {
            super.remove(offs, len);
            String text = getText(0, getLength());
            int before = findLastNonWordChar(text, offs);
            if (before < 0) {
                before = 0;
            }
            int after = findFirstNonWordChar(text, offs + len);
            try {
                String removedText = text.substring(before, after);
                if (containsKeyword(removedText, keywordArray1)) {
                    setCharacterAttributes(before, after - before, keyw1, false);
                } else if (containsKeyword(removedText, keywordArray2)) {
                    setCharacterAttributes(before, after - before, keyw2, false);
                } else if (containsKeyword(removedText, keywordArray3)) {
                    setCharacterAttributes(before, after - before, keyw3, false);
                } else if (containsKeyword(removedText, keywordArray4)) {
                    setCharacterAttributes(before, after - before, keyw4, false);
                } else {
                    setCharacterAttributes(before, after - before, attrBlack, false);
                }
            } catch (Exception e) {
                // Handle exception if needed
            }
        }


        private boolean containsKeyword(String word, String[] keywordArray) {
            for (String keyword : keywordArray) {
                if (word.matches("(\\W)*(" + keyword + ")")) {
                    return true;
                }
            }
            return false;
        }
    };
}


