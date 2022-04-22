package edu.wright.airviewer2;

import com.aspose.pdf.Color;


import com.aspose.pdf.Document;
import com.aspose.pdf.TextFragment;
import com.aspose.pdf.TextFragmentAbsorber;
import com.aspose.pdf.TextFragmentCollection;

import com.google.java.contract.Invariant;

import com.google.java.contract.Requires;

import com.google.java.contract.Ensures;

/**
 * This class has attributes "filePath" and "text", which will
 * be used by highlightText() method to highlight that text in the PDF.
 * @invariant ("text != null && text.length() > 0")
 * @author Dorayya
 *
 */
@Invariant("text != null && text.length() > 0")
public class HighlightText {
	
	String text;
	String filePath;
	
/**
 * This constructor takes the "filePath" and "text" as a parameters.
 * @pre ("text != null && text.length() > 0")
 * @param text
 * @param filePath
 */
	@Requires("text != null && text.length() > 0")
	public HighlightText(String text, String filePath) {
		super();
		this.text = text;
		this.filePath = filePath;
	}

/**
 * This method highlightText() highlights a text in a PDF given by the user.
 * 
 * This function is using aspose.pdf library to achieve this purpose.
 * 
 * Reference link for this functionality: https://docs.aspose.com/pdf/java/replace-text-in-a-pdf-document/
 * @pre ("text != null && text.length() > 0")
 * @post ("result == true")
 * @return
 */
	@Requires("text != null && text.length() > 0")
	@Ensures("result == true")
	public boolean highlightText() {
		boolean result = false;
		{
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
            //textFragment.getTextState().setForegroundColor(Color.getBlack());
            textFragment.getTextState().setBackgroundColor(Color.getYellow());
        }
        // Save the updated PDF file
        pdfDocument.save(filePath);
        result = true; 
		}
		return result;
	}

}
