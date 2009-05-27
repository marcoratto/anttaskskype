package uk.co.marcoratto.ant.skype;

import java.io.File;

import org.apache.tools.ant.Main;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class TestSendMessage extends MyTestCase {
	

	protected void setUp() {
		System.out.println(this.getName() + ".setUp()");	
		System.setProperty("ant.home", "d:\\Programmi\\apache-ant-1.7.1");
		
		try {
	        configureProject("./test/res/TestSkype.xml");
	        			
		} catch (Throwable t) {
			t.printStackTrace();
			fail(t.getMessage());
		} 
	}

	protected void tearDown() {
		System.out.println(this.getName() + ".tearDown()");
	}

	public static void main (String[] args) {
		TestRunner.run(suite());
	}
	
	public static Test suite() {
		return new TestSuite(TestSendMessage.class);
	}	
		
	public void testVersion() {
		System.out.println(this.getClass().getName() + ".testVersion()");
		
		try {
			String expected = "Apache Ant version 1.7.1 compiled on June 27 2008";
			String actual = Main.getAntVersion();
			assertEquals(expected, actual);
			// Main.main(new String[] { "-version" } );
		} catch (Throwable t) {
			t.printStackTrace();
			fail(t.getMessage());
		} 					
	}	

	public void testParameterMessageNull() {
		System.out.println(this.getClass().getName() + ".testParameterMessageNull()");
		try {
			executeTarget("testParameterMessageNull");
			
			fail("Why here?");
		} catch (Throwable t) {
			t.printStackTrace();			
		} 		
	}			
	
	public void testParameterListOfNickNamesNull() {
		System.out.println(this.getClass().getName() + ".testParameterListOfNickNamesNull()");
		try {
			executeTarget("testParameterListOfNickNamesNull");
			
			fail("Why here?");
		} catch (Throwable t) {
			t.printStackTrace();			
		} 		
	}			
	
	public void testHelloWorld() {
		System.out.println(this.getClass().getName() + ".testHelloWorld()");
		try {
			executeTarget("testHelloWorld");
		} catch (Throwable t) {
			t.printStackTrace();			
			fail(t.getMessage());
		} 		
	}	
	
	public void testFailOnErrorTrue() {
		System.out.println(this.getClass().getName() + ".testFailOnErrorTrue()");
		try {
			executeTarget("testFailOnErrorTrue");
			fail("Why here?");
		} catch (Throwable t) {
			t.printStackTrace();			
		} 		
	}	
	
}



