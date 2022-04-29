package edu.wright.airviewer2;

import java.io.File;
import java.io.IOException;


	
import com.google.java.contract.Invariant;

import com.google.java.contract.Requires;
import com.aspose.pdf.facades.Algorithm;
import com.aspose.pdf.facades.DocumentPrivilege;
import com.aspose.pdf.facades.KeySize;
import com.aspose.pdf.facades.PdfFileSecurity;
import com.google.java.contract.Ensures;

	/**
	 * 
	 * @author Ajay krishna reddy
	 * @invariant ("password != null && password.length() > 0")
	 * This class has a method that has an ability to add password to a PDF
	 *
	 */
	@Invariant("password != null && password.length() > 0")
	public class SetPassword {
		
	  String filePath;
	  String password;
	  
	  /**
	   * This constructor takes filePath and the its password to be set as input arguments.
	   * @pre ("password != null && password.length() > 0")
	   * @param filePath
	   * @param password
	   */
	  @Requires("password != null && password.length() > 0")
	  public SetPassword(String filePath, String password) {
		super();
		this.filePath = filePath;
		this.password = password;
	}

    /**
     * This method sets password chosen by the user to the PDF
     * @pre ("password != null && password.length() > 0")
     * @post ("result == true")
     * @return
     * @throws IOException 
     */
	  
	@Requires("password != null && password.length() > 0")
	@Ensures("result == true")
	public boolean setPassword() throws IOException {
		  
		boolean result = false;
		
		{
		// Create PdfFileSecurity object
        PdfFileSecurity fileSecurity = new PdfFileSecurity();
        fileSecurity.bindPdf(filePath);
        // Encrypt file using 256-bit encryption
        fileSecurity.encryptFile(password, password, DocumentPrivilege.getPrint(), KeySize.x256, Algorithm.AES);
        fileSecurity.save(filePath);
	    result = true;
		}

	    return result;
	  }
	}
