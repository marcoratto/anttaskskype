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

import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.BuildListener;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

import junit.framework.TestCase;

public class MyTestCase extends TestCase {

    protected Project project;

    private StringBuffer logBuffer;
    private StringBuffer fullLogBuffer;
    private StringBuffer outBuffer;
    private StringBuffer errBuffer;
    private BuildException buildException;
    
	public MyTestCase() {
		super();
	}
	
    public MyTestCase(String name) {
        super(name);
    }
    
    public void configureProject(String filename) throws BuildException {
        configureProject(filename, Project.MSG_DEBUG);
    }

    public void configureProject(String filename, int logLevel) throws BuildException {
        logBuffer = new StringBuffer();
        fullLogBuffer = new StringBuffer();
        project = new Project();
        project.init();
        File antFile = new File(System.getProperty("root"), filename);
        project.setUserProperty("ant.file" , antFile.getAbsolutePath());
        project.addBuildListener(new AntTestListener(logLevel));
        ProjectHelper.configureProject(project, antFile);
    }
    
    /**
     * Executes a target we have set up
     *
     * @pre configureProject has been called
     * @param  targetName  target to run
     */
    public void executeTarget(String targetName) {
        PrintStream sysOut = System.out;
        PrintStream sysErr = System.err;
        try {
            sysOut.flush();
            sysErr.flush();
            outBuffer = new StringBuffer();
            PrintStream out = new PrintStream(new AntOutputStream(outBuffer));
            System.setOut(out);
            errBuffer = new StringBuffer();
            PrintStream err = new PrintStream(new AntOutputStream(errBuffer));
            System.setErr(err);
            logBuffer = new StringBuffer();
            fullLogBuffer = new StringBuffer();
            buildException = null;
            project.executeTarget(targetName);
            
        	System.out.println(this.getFullLog());
        	System.out.println(this.getError());
        } finally {
            System.setOut(sysOut);
            System.setErr(sysErr);
        }

    }    
    
    /**
     * an output stream which saves stuff to our buffer.
     */
    private static class AntOutputStream extends OutputStream {
        private StringBuffer buffer;

        public AntOutputStream( StringBuffer buffer ) {
            this.buffer = buffer;
        }

        public void write(int b) {
            buffer.append((char)b);
        }
    }
    
    public Project getProject() {
        return project;
    }
    
    public String getOutput() {
        return cleanBuffer(outBuffer);
    }

    public String getError() {
        return cleanBuffer(errBuffer);
    }
    
    public String getFullLog() {
        return fullLogBuffer.toString();
    }

    public BuildException getBuildException() {
        return buildException;
    }

    private String cleanBuffer(StringBuffer buffer) {
        StringBuffer cleanedBuffer = new StringBuffer();
        boolean cr = false;
        for (int i = 0; i < buffer.length(); i++) {
            char ch = buffer.charAt(i);
            if (ch == '\r') {
                cr = true;
                continue;
            }

            if (!cr) {
                cleanedBuffer.append(ch);
            } else {
                cleanedBuffer.append(ch);
            }
        }
        return cleanedBuffer.toString();
    }    
    
    /**
     * Gets the log the object.
     * Only valid if configureProject() has been called.
     *
     * @pre logBuffer!=null
     * @return    The log value
     */
    public String getLog() {
        return logBuffer.toString();
    }
    
    /**
     * Assert that only the given message has been logged with a
     * priority &lt;= INFO when running the given target.
     */
    public void expectLog(String exptected) {
        assertEquals(exptected, getLog());
    }
    
    /**
     * Our own personal build listener.
     */
    private class AntTestListener implements BuildListener {
        private int logLevel;

        /**
         * Constructs a test listener which will ignore log events
         * above the given level.
         */
        public AntTestListener(int logLevel) {
            this.logLevel = logLevel;
        }

        /**
         * Fired before any targets are started.
         */
        public void buildStarted(BuildEvent event) {
        }

        /**
         * Fired after the last target has finished. This event
         * will still be thrown if an error occurred during the build.
         *
         * @see BuildEvent#getException()
         */
        public void buildFinished(BuildEvent event) {
        }

        /**
         * Fired when a target is started.
         *
         * @see BuildEvent#getTarget()
         */
        public void targetStarted(BuildEvent event) {
            //System.out.println("targetStarted " + event.getTarget().getName());
        }

        /**
         * Fired when a target has finished. This event will
         * still be thrown if an error occurred during the build.
         *
         * @see BuildEvent#getException()
         */
        public void targetFinished(BuildEvent event) {
            //System.out.println("targetFinished " + event.getTarget().getName());
        }

        /**
         * Fired when a task is started.
         *
         * @see BuildEvent#getTask()
         */
        public void taskStarted(BuildEvent event) {
            //System.out.println("taskStarted " + event.getTask().getTaskName());
        }

        /**
         * Fired when a task has finished. This event will still
         * be throw if an error occurred during the build.
         *
         * @see BuildEvent#getException()
         */
        public void taskFinished(BuildEvent event) {
            //System.out.println("taskFinished " + event.getTask().getTaskName());
        }

        /**
         * Fired whenever a message is logged.
         *
         * @see BuildEvent#getMessage()
         * @see BuildEvent#getPriority()
         */
        public void messageLogged(BuildEvent event) {
            if (event.getPriority() > logLevel) {
                // ignore event
                return;
            }

            if (event.getPriority() == Project.MSG_INFO ||
                event.getPriority() == Project.MSG_WARN ||
                event.getPriority() == Project.MSG_ERR) {
                logBuffer.append(event.getMessage());
            }
            fullLogBuffer.append(event.getMessage());
        }
    }

}
