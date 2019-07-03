package javaapplication;


import java.util.Hashtable;
import java.util.LinkedList;
import javaapplication.Tuple;
import java.lang.Math; 
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gal
 */
public class InvertedIndex {
    static LinkedList<String>Corpus; 
    static Hashtable<String,LinkedList<Tuple<Tuple<Integer, Integer>, Double>>> Index; 
     public static Hashtable<Integer, Document> documents; 
    public static void IntializeInvertedIndex () {
        Index = new   Hashtable<String,LinkedList<Tuple<Tuple<Integer, Integer>, Double>>> (); 
        documents = new Hashtable<Integer, Document>(); 
    }
    public static void addDocument(int doc_id, Document mydoc)
    {
        documents.put(doc_id, mydoc); 
    }
    public static Document getDocument(int doc_id)
    {
        return documents.get(doc_id); 
    }
    public static void add(String word, int doc_id, int pass_id, double count)
    {
        if(!Index.containsKey(word))
        {
            Index.put(word, new LinkedList<Tuple<Tuple<Integer, Integer>, Double>>()); 
          //  Corpus.add(word); 
        }
        LinkedList<Tuple<Tuple<Integer, Integer>, Double>> mylist = Index.get(word); 
        Tuple<Tuple<Integer, Integer>, Double> tuple = new  Tuple<Tuple<Integer, Integer>, Double>(new Tuple<Integer, Integer>(doc_id, pass_id), count); 
        mylist.add(tuple); 
    }//new Tuple<Tuple<Integer, Integer>, Double>(new Tuple<Integer, Integer>(doc_id, pass_id)), count)
    public static LinkedList<Tuple<Tuple<Integer, Integer>, Double>>  get(String word)
    {
        return Index.get(word); 
    }
    public static LinkedList <Tuple<Tuple<Document,Passage>, Double>> getPassage(String word)
    {
        LinkedList <Tuple<Tuple<Document,Passage>, Double>> myPassageList; 
        myPassageList = new  LinkedList<Tuple<Tuple<Document,Passage>, Double>>();
        LinkedList<Tuple<Tuple<Integer, Integer>, Double>> mylist = Index.get(word); 
        for(int k = 0; k < mylist.size(); k++)
        {
            Tuple<Tuple<Document, Passage>, Double>tup; 
            Document doc1 = getDocument(mylist.get(k).x.x); 
            Passage pass1= doc1.getPassage(mylist.get(k).x.y); 
            double count = mylist.get(k).y; 
            tup = new Tuple<Tuple<Document, Passage>, Double>(new Tuple <Document, Passage>(doc1, pass1), count); 
        }
        return myPassageList; 
    }
    public static double getTermDocumentScoreAddition(String word, double document_length, double term_frequency, double term_document_frequency)
    {//first version 
       
        double term_freq_log = java.lang.Math.log(term_frequency+0.5); 
       double term_document_frequency_log = java.lang.Math.log(term_document_frequency + 0.5); 
       double doc_length_log= java.lang.Math.log(document_length + 0.5); 
       double score = term_document_frequency_log  / (term_freq_log*doc_length_log); 
      return score; 
    }
    public static boolean doesContain1()
    {
        return documents.containsKey(1); 
    }
    public static boolean isEmptyList(String word)
    {
        return Index.get(word) == null; 
    }
    public static double get_total_term_frequency(String word)
    {
        if(Index.get(word) == null)
            return 0; 
        double total_frequency =0; 
        int listSize = Index.get(word).size(); 
        for(int i = 0 ; i < listSize; i++)
        {
            
            Tuple<Tuple<Integer, Integer>, Double> tup= Index.get(word).get(i); 
            total_frequency+= tup.y; 
            
        }
        return total_frequency; //frequency in all corpus
    }
    public static void ResetAllScore()
    {//Debug good, but preffered only to query relevant passages
        for(int i = 0; i < documents.size(); i++)
            for(int j = 0; j < documents.get(i).passages.size(); j++)
            {
                documents.get(i).passages.get(j).resetScore();
            }
    }
    public static boolean doesWordExist(String word)
    {
    
        
        return Index.containsKey(word)&& Index.get(word)!=null; 
        
    }
    public static  LinkedList<Tuple<Passage, Double>>getFivePassages()
    {
        LinkedList<Tuple<Passage, Double>>l = new LinkedList<Tuple<Passage, Double>>(); 
        for(int i = 0; i < 5; i++)
        {
            Passage pass ; 
            pass = documents.get(i).getPassage(0); 
            l.add(new Tuple<Passage, Double>(pass, 0.0)); 
        }
        return l; 
    }
    public static boolean not_appear_but_very_similar(String q_term)
    {
        double best_similarity=10; 
        String most_similar_word; 
        if(Corpus.contains(q_term))
            return false; 
        else
            for(String word: Corpus)
            {
                if(Math.abs(StringCompare(q_term, word))<best_similarity)
                {
                    best_similarity = Math.abs(StringCompare(q_term, word)); 
                    most_similar_word = word; 
                }
            }
        
        if(best_similarity<0.15)
            return true; 
        return false; 
    }
    public static double StringCompare(String st1, String st2)
    {
        
        int l1 = st1.length(); 
        int l2 = st2.length(); 
        int lmin; 
       double diff_counter=0; 
        lmin = Math.min(l1, l2);
  
        for (int i = 0; i < lmin; i++) { 
            int str1_ch = (int)st1.charAt(i); 
            int str2_ch = (int)st2.charAt(i); 
  
            if (str1_ch != str2_ch) { 
               diff_counter++; 
            } 
        } 
        return (diff_counter/lmin)*(Math.max(l1, l2)/lmin); 
  
    }
    public static String getClosestWord(String q_term)
            {
                 double best_similarity=10; 
        String most_similar_word= ""; 
     
      
            for(String word: Corpus)
            {
                if(Math.abs(StringCompare(q_term, word))<best_similarity)
                {
                    best_similarity = Math.abs(StringCompare(q_term, word)); 
                    most_similar_word = word; 
                }
            }
        
       return most_similar_word; 
            }
}
