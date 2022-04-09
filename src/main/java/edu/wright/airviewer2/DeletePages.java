package edu.wright.airviewer2;

import java.io.File;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;

import com.google.java.contract.Invariant;

import com.google.java.contract.Requires;

import com.google.java.contract.Ensures;

/**
 * This class is having a member function deletePage() which deletes a page from the PDF.
 * 
 * @invariant ("pageNumber != null && pageNumber.length() > 0")
 * @author Dorayya
 *
 */

@Invariant("pageNumber != null && pageNumber.length() > 0")
public class DeletePages {

	String pageNumber;
	String filePath;
	int parsedPageNumber;
	File file = null;
	PDDocument document = null;
	int noOfPages;
	int noOfPages1;
	
	/**
	 * This constructor takes the "filePath" and "pageNumber" as a parameters.
	 * It also parses the "pageNumber" to integer data type for delete page functionality purpose
	 * This constructor also finds number of pages of the PDF to set pre-condition for DeletePages() method.
	 * 
	 * @pre ("pageNumber != null && pageNumber.length() > 0")
	 * @param pageNumber
	 * @param filePath
	 */

	@Requires("pageNumber != null && pageNumber.length() > 0")
	public DeletePages(String pageNumber, String filePath) {
		super();
		this.pageNumber = pageNumber;
		this.filePath = filePath;
		parsedPageNumber = Integer.parseInt(pageNumber) - 1;
		// Loading the file
		file = new File(filePath);
		// Getting the count of pages from document
		noOfPages = document.getNumberOfPages();
	}

	
	/**
	 * This method deletePage() deletes a page from a PDF which holds a page number given by the user.
	 * 
	 * This function is using Apache PDF box library to achieve this purpose.
	 * 
	 * Reference link for this functionality: https://pdfbox.apache.org/docs/1.8.10/javadocs/index.html?org/apache/pdfbox/pdmodel/PDDocument.html
	 * 
	 * @pre ("noOfPages >= parsedPageNumber")
	 * @post ("result == true")
	 * @return
	 * @throws IOException
	 */
	@Requires("noOfPages >= parsedPageNumber")
	@Ensures("result == true")
	public boolean deletePage() throws IOException {

		boolean result = false;
		{
			// Removing the pages
			document.removePage(parsedPageNumber);

			// Saving the document
			document.save(filePath);

			result = true;
		}
		return result;

	}
}
