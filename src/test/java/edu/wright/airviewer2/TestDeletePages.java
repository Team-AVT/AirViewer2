package edu.wright.airviewer2;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestDeletePages {
	
	DeletePages delObj = null;

	@Before
	public void setUp() throws Exception {
		
		delObj = new DeletePages("1","C:\\Users\\Swetha\\Desktop\\AirViewer2_Dorayya\\sample.pdf");
	}

	@After
	public void tearDown() throws Exception {
		
		delObj = null;
	}

	@Test
	public void test() throws IOException {
		
		assertEquals(true, delObj.deletePage());
	}

}
