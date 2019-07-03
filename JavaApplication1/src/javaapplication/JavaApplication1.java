package javaapplication;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.json.*;
import java.io.FileNotFoundException;
import java.util.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.io.FileReader;
import javaapplication.Document;
import javaapplication.InvertedIndex;
import javaapplication.Passage;
import javaapplication.StopwordsListCreator;
import javaapplication.StringTokonizer;
import javaapplication.QueryParser;
import javaapplication.Retrival;

/**
 *
 * @author gal
 */
public class JavaApplication1 {
    public static int numOfDocuments = 50; 
    public static int NumberOfPassages = 2; 
   
     public static void main(String[] args) throws FileNotFoundException,
            IOException, ParseException, Exception 
     {
          JSONParser parser = new JSONParser();
           Object obj = parser.parse(new FileReader("C:\\Users\\gal\\Documents\\NetBeansProjects\\JavaApplication1\\src\\javaapplication\\document_passages.json"));
         String passage;
         StringTokonizer st1; 
         List<String> PassageTokens;
         
        InvertedIndex.IntializeInvertedIndex();
         
             /* for (int j=0; j<PassageTokens.size(); j++) {
                   System.out.println(PassageTokens.get(j));
               }*/
              numOfDocuments = (Integer) ((JSONObject)((JSONObject)obj)).size(); 
              for(int i = 0; i < numOfDocuments; i++) {
                  Document doc = new Document(i); 
                  double document_length = 0; 
                   NumberOfPassages =  (Integer)((JSONObject)((JSONObject)obj).get(Integer.toString(i))).size(); 
                   for(int j=0; j < NumberOfPassages; j++){
                       passage = (String) ((JSONObject)((JSONObject)obj).get(Integer.toString(i))).get(Integer.toString(j)); 
                       passage = passage.toLowerCase();
                       Passage mypassage = new Passage(j, i, passage); 
                             st1 = new StringTokonizer(passage);
                             PassageTokens = st1.getWords();
                            
                             Map<String, Double> termFrequencies = new TreeMap<String, Double>(); 
                             for(int currentTerm = 0; currentTerm< PassageTokens.size(); currentTerm++ )
                             {
                                 if(!termFrequencies.containsKey(PassageTokens.get(currentTerm)))
                                 {
                                     termFrequencies.put(PassageTokens.get(currentTerm),1.0); 
                                 }
                                 else 
                                 {
                                     double frequency = termFrequencies.get(PassageTokens.get(currentTerm)); 
                                     frequency++; 
                                     termFrequencies.replace(PassageTokens.get(currentTerm), frequency); 
                                 }
                              }
                             for (Map.Entry<String,Double> entry : termFrequencies.entrySet())
                             {
                                 InvertedIndex.add(entry.getKey(), i, j, entry.getValue()); 
                                
                             }
                             mypassage.setSentenceTokens(PassageTokens);
                             mypassage.setLength(passage.length()); 
                             document_length+= passage.length(); 
                             doc.assPassage(mypassage, j);
                    }
                  
                doc.setLength(document_length);
                InvertedIndex.addDocument(i, doc);
            


    
              }
             
             
          
              Retrival.InitializeRetriever(); 
              QueryParser.Parse(); 
              Retrival.printToJson(); 
     }
}

   
    
    

