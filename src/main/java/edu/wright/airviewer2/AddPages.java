package edu.wright.airviewer2;

import java.io.File;


import java.io.IOException;
 
import org.apache.pdfbox.pdmodel.PDDocument; 
import org.apache.pdfbox.pdmodel.PDPage;

import com.google.java.contract.Invariant;

import com.google.java.contract.Requires;

import com.google.java.contract.Ensures;

/**
 * This class has attributes "filePath" and "numberOfPages", which will
 * be used by addPages() method of this class to add blank pages at the end of that PDF.
*
*@invariant ("numberOfPages != null && numberOfPages.length() > 0")
*@author Dorayya
*/

@Invariant("numberOfPages != null && numberOfPages.length() > 0")
public class AddPages {
	
	String numberOfPages;
	String filePath;
	int parsedNumberOfPages;
	
/**
 * This constructor takes the "filePath" and "numberOfPages" as a parameters.
 * It also parses "numberOfPages" value to integer and assigns to "parsedNumberOfPage". 
 * 
 * @pre ("numberOfPages != null && numberOfPages.length() > 0")
 * @param path
 * @param number
 */
	
@Requires("numberOfPages != null && numberOfPages.length() > 0")
	public AddPages(String filePath, String numberOfPages) {
		this.filePath = filePath;
		this.numberOfPages = numberOfPages;
		parsedNumberOfPages = Integer.parseInt(numberOfPages);
	}

/**
 * This method addPages() adds the number of blank pages given by user at the end of the PDF.
 * 
 * @pre ("parsedNumberOfPages >= 1.0")
 * @post ("result == true")
 * @return
 * @throws IOException
 */
    
@Requires("parsedNumberOfPages >= 1.0")
@Ensures("result == true")
	
public boolean addPages() throws IOException { 	
	     
		boolean result = false;
		
		PDDocument document = null;
		//Loading an existing document 
	      File file = new File(filePath); 
	     
			document = PDDocument.load(file);
			
	      for (int i=0; i<parsedNumberOfPages ; i++) {
	          //Creating a blank page 
	          PDPage blankPage = new PDPage();

	          //Adding the blank page to the document
	          document.addPage( blankPage );
	       } 
	      
	      
	    	//Saving the document 
	        document.save(filePath);
	        
	       result = true;
	        
		return result;
	}
	
}
