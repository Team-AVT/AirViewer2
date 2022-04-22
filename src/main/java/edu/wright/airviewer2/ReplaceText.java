package edu.wright.airviewer2;

import java.io.IOException;


import com.aspose.pdf.*;

import com.google.java.contract.Invariant;

import com.google.java.contract.Requires;

import com.google.java.contract.Ensures;

/**
 * This class has attributes "filePath","repaceText" and "withText", which will
 * be used by replaceText() method to replace one text with other text in a PDF.
 * @invariant ("replaceText != null && replaceText.length() > 0 && withText != null && withText.length() > 0")
 * @author Dorayya
 *
 */

@Invariant("replaceText != null && replaceText.length() > 0 && withText != null && withText.length() > 0")
public class ReplaceText {
	
	String filePath;
	String replaceText;
	String withText;
	
	
/**
 * This constructor takes the "filePath","repaceText" and "withText" as a parameters.
 * @pre ("replaceText != null && replaceText.length() > 0 && withText != null && withText.length() > 0")
 * @param filePath
 * @param replaceText
 * @param withText
 */
	@Requires("replaceText != null && replaceText.length() > 0 && withText != null && withText.length() > 0")
	public ReplaceText(String filePath, String replaceText, String withText) {
		super();
		this.filePath = filePath;
		this.replaceText = replaceText;
		this.withText = withText;
	}

/**
 * This method replaceText() replaces a text with an other text in a PDF given by the user.
 * 
 * This function is using aspose.pdf library to achieve this purpose.
 * 
 * Reference link for this functionality: https://docs.aspose.com/pdf/java/replace-text-in-a-pdf-document/
 * @pre ("replaceText != null && replaceText.length() > 0 && withText != null && withText.length() > 0")
 * @post ("result == true")
 * @return
 */
	@Requires("replaceText != null && replaceText.length() > 0 && withText != null && withText.length() > 0")
	@Ensures("result == true")
	public boolean replaceText() {
		
		boolean result = false;
		{
		Document pdfDocument = new Document(filePath);

        // Create TextAbsorber object to find all instances of the input search phrase
        TextFragmentAbsorber textFragmentAbsorber = new TextFragmentAbsorber(replaceText);
        
        // Accept the absorber for first page of document
        pdfDocument.getPages().accept(textFragmentAbsorber);
        
        // Get the extracted text fragments into collection
        TextFragmentCollection textFragmentCollection = textFragmentAbsorber.getTextFragments();
        
        // Loop through the fragments
        for (TextFragment textFragment : (Iterable<TextFragment>) textFragmentCollection) {
            // Update text and other properties
            textFragment.setText(withText);
            textFragment.getTextState().setForegroundColor(Color.getBlack());
            textFragment.getTextState().setBackgroundColor(Color.getWhite());
        }
        // Save the updated PDF file
        pdfDocument.save(filePath);
        result = true;
		}
		return result;
    }
	
	}



