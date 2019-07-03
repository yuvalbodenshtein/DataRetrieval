/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
 import java.util.ArrayList;
import java.util.LinkedList;
/**
 *
 * @author gal
 */
public class QueryParser {
  static LinkedList<Tuple<Integer, String>> Queries; 
    public  static void Parse() throws Exception 
    {         
        Queries = new LinkedList<Tuple<Integer, String>>(); 
            BufferedReader TSVFile = 
    new BufferedReader(new FileReader("C:\\Users\\gal\\Documents\\NetBeansProjects\\JavaApplication1\\src\\javaapplication\\test-sample.tsv"));



String dataRow = TSVFile.readLine(); //read headers line.
dataRow = TSVFile.readLine();// Read first line.

while (dataRow != null){
String[] dataArray = dataRow.split("\t");
String query = dataArray[1]; 
int Question_id = Integer.parseInt(dataArray[0]); 
Queries.add(new Tuple<Integer, String>(Question_id, query)); 
// Print the data line.
dataRow = TSVFile.readLine(); // Read next line of data.
}
// Close the file once all data has been read.
TSVFile.close();

// End the printout with a blank line.
System.out.println();
RunQueries(); 
 } //main()


// Close the file once all data has been read.
public static void RunQueries() throws IOException 
{
    for(Tuple<Integer, String> tup:Queries )
    {
        ParseQuery(tup.y.toLowerCase(), tup.x); 
    }
}

// End the printout with a blank line.
    public static void ParseQuery(String query, int q_id) throws IOException 
    {
       StringTokonizer st1= new StringTokonizer(query);
       List<String>QueryTokens = st1.getWords();
       
       
       
       Retrival.parseQuery(QueryTokens, q_id);
         
        
    }
    

 } //main()


