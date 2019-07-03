/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication;

import java.util.Hashtable;
import java.util.LinkedList;

/**
 *
 * @author gal
 */
public class Document {
    int identifier; 
    double document_length; 
     Hashtable<Integer, Passage> passages; 
    
    public Document(int id)
    {
        identifier = id; 
        passages = new    Hashtable<Integer, Passage>(); 
    }
    public void assPassage(Passage p, int passage_id)
    {
        passages.put(passage_id, p); 
    }
    public void setLength(double length)
    {
        document_length = length; 
       
    }
    public double getLength()
    {
        return document_length; 
    }
    public Passage getPassage(int passage_id)
    {
        return passages.get(passage_id); 
    }


}
