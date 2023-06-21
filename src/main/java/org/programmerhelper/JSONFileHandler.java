/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.programmerhelper;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.FileHandler;

/**
 *
 * @author ahmed
 */
public class JSONFileHandler {
    private boolean livePrevBox;

    private boolean multipleInputBox;

    private String inputText;

    private String language;
    
    private String snippet;


    public void setSnippet(String snippet){
	
    this.snippet = snippet;	
	
	}

    public String getSnippet(){

    return snippet; 	

	}



    public void setLanguage(String language){
	
    this.language = language;	
	
	}

    public String getLanguage(){

    return language; 	

	}

    public void setInputText(String inputText){
	
    this.inputText = inputText;	
	
	}

    public String getInputText(){

    return inputText; 	

	}



    public void setLivePrevBox(boolean livePrevBox){
	
    this.livePrevBox = livePrevBox;	
	
	}

    public boolean getLivePrevBox(){

    return livePrevBox; 	

	}


    public void setMultipleInputBox(boolean multipleInputBox){
	
    this.multipleInputBox = multipleInputBox;	
	
	}

    public boolean getMultipleInputBox(){

    return multipleInputBox; 	

	}

    @Override
    public String toString() {
        return "JSONFileHandler{" +
                "livePrevBox=" + livePrevBox +
                ", multipleInputBox=" + multipleInputBox +
                ", inputText='" + inputText + '\'' +
                ", language='" + language + '\'' +
                ", snippet='" + snippet + '\'' +
                '}';
    }

    public  void updateJSONFile(String filePath, String language1, String snippet1, String inputText1, boolean livePrevBox1, boolean multipleInputBox1) {
        try {
            JSONObject jsonObject;
            try ( // Read the existing JSON file
                    FileReader fileReader = new FileReader(filePath)) {
                JSONTokener tokener = new JSONTokener(fileReader);
                jsonObject = new JSONObject(tokener);
            }

System.out.println(this);

            // Update the JSON data
            if(!(language1.equals(language)) ) {jsonObject.put("language", language1);
                language=language1;}

            
            if(!(snippet1.equals(snippet)) ) {
                jsonObject.put("snippet", snippet1);
                snippet=snippet1;
            }

            
            if(!(inputText1.equals(inputText)) ) {
                jsonObject.put("inputText", inputText1);
                inputText=inputText1;
            }
           
            if(livePrevBox1!=livePrevBox) {
                jsonObject.put("livePrevBox", livePrevBox1);
                livePrevBox=livePrevBox1;
            }
            
            if((multipleInputBox1!=multipleInputBox) ) {
                jsonObject.put("multipleInputBox", multipleInputBox1);
                multipleInputBox=multipleInputBox1;
            }
            System.out.println(this);

            try ( // Write the updated JSON back to the file
                    FileWriter fileWriter = new FileWriter(filePath)) {
                fileWriter.write(jsonObject.toString(4)); // Use indentation of 4 spaces
            } // Use indentation of 4 spaces

            System.out.println("JSON file updated successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    


    public  void readJSONFile(String filePath) {
        try {
            JSONObject jsonObject;
            try ( // Read the JSON file
                    FileReader fileReader = new FileReader(filePath)) {
                JSONTokener tokener = new JSONTokener(fileReader);
                jsonObject = new JSONObject(tokener);
            }

            // Access the JSON data
            setLanguage(jsonObject.getString("language"));
            setSnippet(jsonObject.getString("snippet"));
            setLivePrevBox(jsonObject.getBoolean("livePrevBox"));
            setInputText(jsonObject.getString("inputText"));
            setMultipleInputBox(jsonObject.getBoolean("multipleInputBox"));

            // Display the JSON data
            
            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }