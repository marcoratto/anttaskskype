/*
 * Copyright (C) 2009 Marco Ratto
 *
 * This file is part of the project AntTaskMqSeries.
 *
 * AntTaskSkype is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * any later version.
 *
 * AntTaskSkype is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with AntTaskSkype; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package uk.co.marcoratto.junit;

import uk.co.marcoratto.ant.skype.TestSendMessage;
import junit.framework.Test;
import junit.framework.TestSuite;

public class TestAll {

	public static void main (String[] args) {
		junit.swingui.TestRunner.run(TestAll.class);
		// junit.textui.TestRunner.run (suite());
	}

	public static Test suite ( ) {
		TestSuite suite = new TestSuite("JUnit Test All");		
		suite.addTest(TestSendMessage.suite());
	    return suite;
	}
}