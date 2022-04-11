package edu.wright.airviewer2;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestToPPT {
	
	ToPPT pptObj = null;

	@Before
	public void setUp() throws Exception {
		
		pptObj = new ToPPT("C:\\Users\\Swetha\\Desktop\\AirViewer2_Ajay\\sample.pdf");
	}

	@After
	public void tearDown() throws Exception {
		
		pptObj = null;
	}

	@Test
	public void test() {
		assertEquals(true, pptObj.convertToPPT());
	}

}
