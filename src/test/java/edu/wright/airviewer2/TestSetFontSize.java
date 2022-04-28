package edu.wright.airviewer2;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestSetFontSize {

	SetFontSize objFont = null;
	
	@Before
	public void setUp() throws Exception {
		
		objFont = new SetFontSize("C:\\Users\\Swetha\\Desktop\\AirViewer2_Dorayya\\sample.pdf","proceeded ","28");
	}

	@After
	public void tearDown() throws Exception {
		
		objFont = null;
	}

	@Test
	public void test() {
		
		assertEquals(true, objFont.setFontSize());
	}

}
