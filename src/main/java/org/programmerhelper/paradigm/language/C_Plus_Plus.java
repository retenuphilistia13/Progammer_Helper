package org.programmerhelper.paradigm.language;


import org.programmerhelper.Language;
import org.programmerhelper.paradigm.OOPLanguage;
import java.util.HashSet;
import java.util.Set;


public class C_Plus_Plus extends OOPLanguage{

	public C_Plus_Plus() {
        super(Language.CPLUSPLUS);
	}
        
  
	@Override
	public String gettersSetters(String userInput, String dataType, String accessModifier) {
		
		String modifiedInput;

		modifiedInput = capitalizeFirstChar(userInput);
                
               if("package".equals(accessModifier))
                accessModifier="";
               
		return

				"\n\n   " + accessModifier + " void set" + modifiedInput + "(" + dataType + " " + userInput + ")"
				+ "{\n\t\n    this." + userInput + " = " + userInput + ";\t\n\t\n\t}" +

				"\n\n   " + accessModifier + " " + dataType + " get" + modifiedInput + "()" + "{\n\n    return "
				+ userInput + "; \t\n\n\t}\n";
	}

	@Override
	public String createClass(String userInput, String type, String accessModifier) {
		// TODO Auto-generated method stub
		return "createClass C++";
	}

	@Override
	public String createMainClass(String userInput, String type, String accessModifier) {
		// TODO Auto-generated method stub
		return """
                         #include<iostream>
                       
                           int main(){
                       
                           cout<<"Hello_World";
                                
                           }
                       """;
	}

	@Override
	public Boolean isVariableValid(String userInput) {
		// TODO Auto-generated method stub
		return true;
	}

    @Override
    public String createVariable(String userInput, String dataType, String accessModifier) {
         String output ="    "+"private "+dataType+" "+userInput+";\n";
            return output;
    }

	@Override
	public String createVariable(String userInput, String dataType) {
		return null;
	}

	@Override
    public boolean isIllegalCharacter(String userInput) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isReserved(String userInput) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

	@Override
	public String[] getReservedWords() {
		return new String[0];
	}


}
