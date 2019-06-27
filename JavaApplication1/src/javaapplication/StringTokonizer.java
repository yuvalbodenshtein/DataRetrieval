/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * class StringTokonizer producing a tokens string list from
 * a string passage.
 * @author gal
 */
public class StringTokonizer {
    
    List<String> sentenceTokens; 
    int k=0;
    
    public StringTokonizer (String passage) {
        sentenceTokens = new ArrayList<String> ();
        StringTokenizer defaultTokenizer = new StringTokenizer(passage);
        while (defaultTokenizer.hasMoreTokens())
             {
               sentenceTokens.add( defaultTokenizer.nextToken().replaceAll("\\W", ""));
                k++;
             }
    }
    
    public List<String> GetTokensList(){
        return sentenceTokens;
    }
    public double get_passage_length()
    {
        return sentenceTokens.size(); 
    }
    public List<String> getWords()
    {
        List<String> PassageTokens = GetTokensList(); 
          StopwordsListCreator swlc = new StopwordsListCreator ("C:\\Users\\gal\\Documents\\NetBeansProjects\\JavaApplication1\\src\\javaapplication\\stopwords.txt");
         List<String> stopwords = swlc.Getstopwordlist();
          for (int j = 0; j < stopwords.size(); j++) {
                                   while (PassageTokens.remove(stopwords.get(j))) {}
                          }
          return    PassageTokens;  
    }
            
          
}
