package edu.wright.airviewer2;


import com.aspose.pdf.Document;

import com.aspose.pdf.TextFragment;
import com.aspose.pdf.TextFragmentAbsorber;
import com.aspose.pdf.TextFragmentCollection;

import com.google.java.contract.Invariant;

import com.google.java.contract.Requires;

import com.google.java.contract.Ensures;

/**
 * This class has attributes "filePath","text" and "fontSize", which will
 * be used by setFontSize() method to set font size for that text in a PDF.
 * @invariant ("text != null && text.length() > 0 && fontSize != null && fontSize.length() > 0")
 * @author Dorayya
 *
 */
@Invariant("text != null && text.length() > 0 && fontSize != null && fontSize.length() > 0")
public class SetFontSize {
	
	String filePath;
	String text;
	String fontSize;
	
/**
 * This constructor takes the "filePath","text" and "fontSize" as a parameters.
 * @pre ("text != null && text.length() > 0 && fontSize != null && fontSize.length() > 0")
 * @param filePath
 * @param text
 * @param fontSize
 */
	@Requires("text != null && text.length() > 0 && fontSize != null && fontSize.length() > 0")
	public SetFontSize(String filePath, String text, String fontSize) {
		super();
		this.filePath = filePath;
		this.text = text;
		this.fontSize = fontSize;
	}
	
/**
 * This method setFontSize() sets font size of a text in a PDF.
 * 
 * This function is using aspose.pdf library to achieve this purpose.
 * 
 * Reference link for this functionality: https://docs.aspose.com/pdf/java/replace-text-in-a-pdf-document/
 * @pre ("text != null && text.length() > 0 && fontSize != null && fontSize.length() > 0")
 * @post ("result == true")	
 * @return
 */
	@Requires("text != null && text.length() > 0 && fontSize != null && fontSize.length() > 0")
	@Ensures("result == true")
	public boolean setFontSize() {
		
		boolean result = false;
		{
		int parsedFontSize = Integer.parseInt(fontSize);
	
		Document pdfDocument = new Document(filePath);

        // Create TextAbsorber object to find all instances of the input search phrase
        TextFragmentAbsorber textFragmentAbsorber = new TextFragmentAbsorber(text);
        
        // Accept the absorber for first page of document
        pdfDocument.getPages().accept(textFragmentAbsorber);
        
        // Get the extracted text fragments into collection
        TextFragmentCollection textFragmentCollection = textFragmentAbsorber.getTextFragments();
        
        // Loop through the fragments
        for (TextFragment textFragment : (Iterable<TextFragment>) textFragmentCollection) {
            // Update text and other properties
            textFragment.setText(text);
            textFragment.getTextState().setFontSize(parsedFontSize);
        }
        // Save the updated PDF file
        pdfDocument.save(filePath);
        result = true;
		}
		return result;
	}
 
}
