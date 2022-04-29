package edu.wright.airviewer2;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestSetPassword {
	
	SetPassword passObj = null;

	@Before
	public void setUp() throws Exception {
		
		passObj = new SetPassword ("C:\\Users\\Swetha\\Desktop\\AirViewer2_Ajay\\test.pdf","Dorayya62");
	}

	@After
	public void tearDown() throws Exception {
		
		passObj = null;
	}

	@Test
	public void test() throws IOException {
		passObj.setPassword();
	}

}
