package edu.wright.airviewer2;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestToDoc {
	
	ToDoc docObj = null;

	@Before
	public void setUp() throws Exception {
		
		docObj = new ToDoc("C:\\Users\\Swetha\\Desktop\\AirViewer2_Ajay\\sample.pdf");
	}

	@After
	public void tearDown() throws Exception {
		
		docObj = null;
	}

	@Test
	public void test() {
		assertEquals(true, docObj.convertToDoc());
	}

}
