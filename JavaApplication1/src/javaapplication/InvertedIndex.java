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
        double score = term_document_frequency_log  / (term_freq_log*document_length); 
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
}
