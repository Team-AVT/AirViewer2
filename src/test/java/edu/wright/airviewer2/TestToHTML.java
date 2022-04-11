package edu.wright.airviewer2;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestToHTML {
	
	ToHTML htmlObj = null;

	@Before
	public void setUp() throws Exception {
		
		htmlObj = new ToHTML("C:\\Users\\Swetha\\Desktop\\AirViewer2_Ajay\\sample.pdf");
	}

	@After
	public void tearDown() throws Exception {
		
		htmlObj = null;
	}

	@Test
	public void test() {
		assertEquals(true, htmlObj.convertToHTML());
	}

}
