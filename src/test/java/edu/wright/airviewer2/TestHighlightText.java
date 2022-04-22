package edu.wright.airviewer2;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestHighlightText {
 
HighlightText objHighlight = null;
	
	@Before
	public void setUp() throws Exception {
		
		objHighlight = new HighlightText ("Simple","C:\\Users\\Swetha\\Desktop\\AirViewer2_Dorayya\\sample.pdf");
	}

	@After
	public void tearDown() throws Exception {
		
		objHighlight = null;
	}

	@Test
	public void test() {
		
		assertEquals(true, objHighlight.highlightText());
	}

}
