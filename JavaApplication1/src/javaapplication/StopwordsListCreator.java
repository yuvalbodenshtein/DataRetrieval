/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 *calss stopwordslistcreator create from .txt file a list of stopwords in the form of
 * list of strings.
 * @author gal
 */
public class StopwordsListCreator {
    
    String sCurrentLine;
    List<String> stopwords = new ArrayList<String>();
    
    public StopwordsListCreator(String url){
        try {
            FileReader sw = new FileReader(url);
            BufferedReader br= new BufferedReader(sw);
            while ((sCurrentLine =  br.readLine()) != null){
             stopwords.add(sCurrentLine);
                      //System.out.println(sCurrentLine);
             }
            
        }    catch (Exception e) {
             System.out.println(e.getClass());
          }
    
     }
    
     public List<String> Getstopwordlist ()  {
         return stopwords;
     }
}