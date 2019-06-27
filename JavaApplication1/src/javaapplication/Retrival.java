/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import javaapplication.Passage;
import javaapplication.Tuple;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
/**
 *
 * @author gal
 */
public class Retrival {
    static List<Passage> passages; 
     static FileWriter file; 
    static int current_query_id = 0; 
   static JSONArray output; 
    //We cannoot do from tuples so get from Documents 
    public static void InitializeRetriever() throws IOException
    {
         file = new FileWriter("C:\\Users\\gal\\Documents\\NetBeansProjects\\JavaApplication1\\src\\javaapplication\\output.json") ; 
         output = new JSONArray();
         passages = new  LinkedList<Passage> (); 
    }
    public static void printDocIdsForQuery(List<String> terms, int Question_id)
    {
        System.out.println("Query id: " +  Question_id);
        for(int currentQueryWord = 0;  currentQueryWord < terms.size() ;  currentQueryWord++ )
         { 
               String term = terms.get(currentQueryWord); 
              for(Tuple<Tuple<Integer, Integer>, Double> tuple:InvertedIndex.get(term))
              {
                  Document myDocument =  InvertedIndex.getDocument(tuple.x.x); 
                Passage myPassage = myDocument.getPassage(tuple.x.y); 
                  System.out.println(myPassage.passage);
                  System.out.println("Doc Id:  " + tuple.x.x + "Passage_id: " + tuple.x.y + " Her the following term : " + term + "occurs " + tuple.y + " times!!!");
              }
         }
    }
    public static void printToJson() throws IOException
    {
         file.write(output.toJSONString());
            file.flush();
    }
    public static void parseQuery( List<String> terms, int Question_id) throws IOException 
    {//query comes from tsv json
        //printDocIdsForQuery(terms,Question_id); 
        current_query_id =  Question_id; 
        
         passages.clear();
         for(int currentQueryWord = 0;  currentQueryWord < terms.size() ;  currentQueryWord++ )
         {
             String term = terms.get(currentQueryWord); //
             
             if(InvertedIndex.doesWordExist(term))
             {
                    double total_term_frequency = InvertedIndex.get_total_term_frequency(term);
                 //Not doing anything 
                
                   
             
            for(Tuple<Tuple<Integer, Integer>, Double> tuple:InvertedIndex.get(term))
            {
               
               Document myDocument =  InvertedIndex.getDocument(tuple.x.x); 
                Passage myPassage = myDocument.getPassage(tuple.x.y); 
                double term_document_frequency; 
                if(!passages.contains(myPassage))// adding score to existed passage
                {
                    passages.add(myPassage);
                    double document_length = myDocument.getLength(); 
                   term_document_frequency = tuple.y; 
                 
                   double initial_score =  InvertedIndex.getTermDocumentScoreAddition(term, document_length,  term_document_frequency, term_document_frequency);
                   myPassage.resetScore();
                   myPassage.addToScore(initial_score);
                 
                    
                }
                else 
                {
                     
                      double addition_to_score ;  
                      double document_length = myDocument.getLength(); 
                    term_document_frequency = tuple.y; 
                 addition_to_score =  InvertedIndex.getTermDocumentScoreAddition(term, document_length,  term_document_frequency, term_document_frequency); 
                  
                  myPassage.addToScore(addition_to_score);
                }
            }
             }
         }
         LinkedList<Tuple<Passage, Double>> best_passages_to_print = getExcellentPassages(); 
         printToJsonSuccessPassagesDetails(best_passages_to_print); 
    }
    public static void printToJsonSuccessPassagesDetails(  LinkedList<Tuple<Passage, Double>>best_five_passages) throws IOException 
    {//padding with json starter and delimiter...
         
        JSONObject  passageDetails =  new JSONObject();
           passageDetails.put("id: ",Integer.toString(current_query_id)); 
           JSONArray answers = new JSONArray();
            for(Tuple<Passage, Double> RelevantPassage: best_five_passages)
         {// print record to json 
             JSONObject  passDetails =  new JSONObject();
             int pass_id = RelevantPassage.x.get_passage_id();
             int doc_id = RelevantPassage.x.get_doc_id(); 
             passDetails.put("score: ", String.format("%.7f", RelevantPassage.y)); 
             passDetails.put("answer: ", Integer.toString(doc_id) + ":" + Integer.toString(pass_id));
             answers.add(passDetails); 
         }
           passageDetails.put("answers", answers); 
        output.add(passageDetails); 
         
    }
    public static   LinkedList<Tuple<Passage, Double>> getExcellentPassages ()
    {//print in Json format
        LinkedList<Tuple<Passage, Double>>my_best_five_passages; 
        my_best_five_passages = new LinkedList<Tuple<Passage, Double>>(); 
        if(passages.isEmpty())
        {
              my_best_five_passages= InvertedIndex.getFivePassages(); 
        }
        int size = passages.size(); 
        
        for (int place = 1; place <=Math.min(5,  size); place++ )//place is place between excellent passages 
        {
            Passage pass = getCurrentMaxPassage();
            double score_of_best_passage = pass.get_score(); 
            my_best_five_passages.add(new Tuple<Passage, Double>(pass, score_of_best_passage)); 
            
        }
        if(size<5)
        {
            for(int i = size; i < 5; i++) 
             my_best_five_passages.add(InvertedIndex.getFivePassages().get(i)); 
        }
           
        return my_best_five_passages; 
    }
    public static Passage getCurrentMaxPassage()
    {//extracts that best passage too
        double current_max_Score=-1000; 
        double max_score; //each iteration gets the max score between previous max score and current passage score 
        Passage best_passage =  passages.get(0); 
       for(Passage my_passage: passages)
        {  
            if((max_score = my_passage.get_score())> current_max_Score)
            {
                best_passage =  my_passage; 
                current_max_Score = max_score; 
                
            }     
        }
       passages.remove(best_passage); 
    
   return best_passage; 

}}
