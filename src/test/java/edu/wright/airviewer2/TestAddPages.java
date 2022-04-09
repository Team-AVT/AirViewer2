package edu.wright.airviewer2;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestAddPages {
	
	AddPages addObj = null;

	@Before
	public void setUp() throws Exception {
		
		addObj = new AddPages("C:\\Users\\Swetha\\Desktop\\AirViewer2_Dorayya\\sample.pdf","2.5");
	}

	@After
	public void tearDown() throws Exception {
		addObj = null;
	}

	@Test
	public void test() throws IOException {
		assertEquals(true, addObj.addPages());
	}

}
