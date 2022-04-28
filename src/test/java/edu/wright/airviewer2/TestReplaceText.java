package edu.wright.airviewer2;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestReplaceText {

	ReplaceText objReplace = null;
	
	@Before
	public void setUp() throws Exception {
		
		objReplace = new ReplaceText("C:\\Users\\Swetha\\Desktop\\AirViewer2_Dorayya\\sample.pdf","continued","proceeded");
	}

	@After
	public void tearDown() throws Exception {
		
		objReplace = null;
	}

	@Test
	public void test() {
		assertEquals(true, objReplace.replaceText());
	}

}
