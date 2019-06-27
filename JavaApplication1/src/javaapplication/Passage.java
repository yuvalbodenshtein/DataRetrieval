/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication;

import java.util.List;

/**
 *
 * @author gal
 */
public class Passage {
    int doc_id; 
    int passage_id; 
    double Passage_length;  
    double Document_length; 
     List<String> sentenceTokens ; 
     String passage; 
     double score; 
     
     public Passage(int pass_id, int doc_id, String passage)
     {
         passage_id = pass_id; 
         this.passage = passage; 
       this.doc_id = doc_id; 
     }
     public void set_document_length(double doc_length)
     {
         Document_length =  doc_length; 
     }
     public double get_score()
     {
         return score; 
     }
     public boolean isGreater(Passage passage2)
     {
         return score > passage2.score; 
     }
     public void resetScore()
     {
         score = 0; 
     }
     public void addToScore(double score_addition)
     {
         score += score_addition; 
     }
     public void setSentenceTokens(List<String> sentenceTokens )
     {
         this.sentenceTokens = sentenceTokens; 
     }
    public double get_document_length()
    {
        return Document_length; 
    }
     public void setLength(double length)
    {
          Passage_length= length; 
    }
 
     public void printPassage()
     {
         System.out.println(passage); 
     }
     public int get_doc_id()
     {
         return doc_id; 
     }
     public int get_passage_id()
     {
         return passage_id; 
     }
}
