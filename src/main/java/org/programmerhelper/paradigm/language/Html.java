package org.programmerhelper.paradigm.language;

import org.programmerhelper.Language;
import org.programmerhelper.paradigm.PLanguage;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Stream;

public class Html extends PLanguage {
    public Html(Language language) {
        super(language);
    }

    private final static String []reservedKeywords= {
            "html", "head", "title", "base", "link", "meta", "style", "script", "noscript", "body", "section", "nav", "article", "aside",
            "h1", "h2", "h3", "h4", "h5", "h6"
            , "header", "footer", "address", "main", "p", "hr", "pre", "blockquote", "ol", "ul", "li", "dl",
            "dt", "dd", "figure", "figcaption", "div", "a", "em", "strong", "small", "s", "cite", "q", "dfn", "abbr", "data", "time",
            "code", "var", "samp", "kbd", "sub", "sup", "i", "b", "u", "mark", "ruby", "rt", "rp", "bdi", "bdo", "span", "br", "wbr",
            "ins", "del", "img", "iframe", "embed", "object", "param", "video", "audio", "source", "track", "map", "area", "table", "caption",
            "colgroup", "col", "tbody", "thead", "tfoot", "tr", "td", "th", "form", "fieldset", "legend", "label", "input", "button",
            "select", "datalist", "optgroup", "option", "textarea", "keygen", "output", "progress", "meter", "details", "summary",
            "command", "menu"};
//    private static final char[] illegalChar = {'~', '!', '@', '#', '%', '^', '&', '*', '(', ')', '-', '+', '=', '{', '}', '[',
//            ']', '|', '\\', ':', ';', '"', '\'', '<', '>', ',', '.', '?', '/'};

    @Override
    public Boolean isVariableValid(String userInput) {
        return true;
    }

    @Override
    public boolean isIllegalCharacter(String userInput) {
        return false;
    }

    @Override
    public boolean isReserved(String userInput) {
        return false;
    }

    @Override
    public String[] getReservedWords() {
        return reservedKeywords;
    }

    public String []body={"<",">","/"};
    public String []comment={"//"};



}
