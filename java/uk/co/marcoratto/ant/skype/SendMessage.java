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
package uk.co.marcoratto.ant.skype;

import java.util.StringTokenizer;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import com.skype.Skype;

public class SendMessage extends Task {

	/**
	 * 
	 */
	private final static String DEFAULT_SEPARATOR = ",";
	
	/**
	 * 
	 */
	private String listOfNicknames = null;
	
	/**
	 * 
	 */
	private String message = null;
	
	/**
	 * 
	 */
	private boolean failonerror = true;

	private String separator = null;
	
	/**
	 * 
	 */
	private boolean debug = false;
	
	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	
	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public boolean isFailonerror() {
		return failonerror;
	}

	public void setFailonerror(boolean failonerror) {
		this.failonerror = failonerror;
	}
		
	public SendMessage() {
		super();
		log("SendMessage constructor", Project.MSG_DEBUG);
	}
	
	// The method executing the task
	public void execute() throws BuildException {
		log("execute()", Project.MSG_DEBUG);
        if (getProject() == null) {
            throw new IllegalStateException("project has not been set");
        }		
        if (this.listOfNicknames == null) {
            throw new BuildException("Parameter 'listOfNicknames' is null!");
        }		
        if (this.message == null) {
            throw new BuildException("Parameter 'message' is null!");
        }		
        if (this.separator == null) {
        	this.separator = DEFAULT_SEPARATOR;
        }
        StringTokenizer st = new StringTokenizer(this.listOfNicknames, this.separator);
        while (st.hasMoreElements()) {
        	String s = st.nextToken();        	
        	this.sendMessage(s);
        }
	}

	private void sendMessage(String nick) throws BuildException {
        try {        	
        	if (this.debug) {
            	Skype.setDebug(true);        		
        	}
        	this.log("Send message '" + this.message + "' to " + nick, Project.MSG_VERBOSE);
			Skype.chat(nick).send(this.message);        	
		} catch (Throwable t) {
			if (this.debug) {
				t.printStackTrace();
			}
			if (failonerror) {
				log(t.getMessage(), Project.MSG_ERR);
				throw new BuildException(t);
			} else {
				log("ERROR!Skipped.", Project.MSG_WARN);
			}
		}		
	}

	public String getListOfNicknames() {
		return listOfNicknames;
	}

	public void setListOfNicknames(String aListOfNicknames) {
		this.listOfNicknames = aListOfNicknames;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
