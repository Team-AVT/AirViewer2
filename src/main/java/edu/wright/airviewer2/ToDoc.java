package edu.wright.airviewer2;

import com.aspose.pdf.Document;


import com.aspose.pdf.SaveFormat;

import com.google.java.contract.Invariant;

import com.google.java.contract.Requires;

import com.google.java.contract.Ensures;

/**
 * The class ToDoc has two parameters "originalFilePath" and "convertedFilePath", which will be used by convertToDoc() method, 
 * which will convert the PDF in that path to word doc. 
 * 
 * @invariant ("originalFilePath != null && originalFilePath.length() > 0")
 * @author Ajay krishna reddy
 */

@Invariant("originalFilePath != null && originalFilePath.length() > 0")
public class ToDoc {
	
	String originalFilePath;
	String convertedFilePath;
	
	/**
	 * This is the constructor which takes the PDF path as a parameter, and
	 * assigns that path to a String "convertedFilePath" by replacing the word "pdf" with "doc"
	 * in that path. This "convertedFilePath" string serves convertToDoc functionality to save the 
	 * word document in the same path.
	 * 
	 * @pre ("originalFilePath != null && originalFilePath.length() > 0")
	 * @param originalFilePath
	 */
	
	@Requires("originalFilePath != null && originalFilePath.length() > 0")
	public ToDoc(String originalFilePath) {
		super();
		this.originalFilePath = originalFilePath;
		convertedFilePath = originalFilePath.replace("pdf", "doc");
	}

	/**
	 * This function converts a PDF into word format and saves that word document with same name
	 * in the same path.
	 * 
	 * This function is using aspose.pdf library to achieve this purpose.
	 * 
	 * Reference link for this functionality: https://docs.aspose.com/pdf/net/convert-pdf-to-word/
	 * 
	 * @pre ("convertedFilePath != null && convertedFilePath.length() > 0")
	 * @post ("result == true")
	 * @return
	 */
	
	@Requires("convertedFilePath != null && convertedFilePath.length() > 0")
	@Ensures("result == true")
	public boolean convertToDoc() {
		
		boolean returnValue = false;
		{
		// Load source PDF file
		Document doc = new Document(originalFilePath);

		// Save resultant DOC file
		doc.save(convertedFilePath, SaveFormat.Doc);
		
		returnValue = true;
		}
		
		return returnValue;
	}

}
