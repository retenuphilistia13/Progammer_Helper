package org.programmerhelper.paradigm;

public abstract class OOPLanguage extends PLanguage {

    public OOPLanguage(String language) {
        super(language);
    }


	
	
	public abstract String gettersSetters(String userInput,String dataType,String accessModifier);

	public abstract String createClass(String userInput,String type,String accessModifier);//type ->abstract class interface etc

	public abstract String createMainClass(String userInput,String type,String accessModifier);

        public abstract String createVariable(String userInput, String dataType, String accessModifier);

}
