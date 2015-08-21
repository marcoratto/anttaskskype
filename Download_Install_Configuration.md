# Download #

  1. Open the browser on your desktop and go to the [Downloads](http://code.google.com/p/anttaskskype/downloads/list) area
  1. Download the binary archive (.zip for Windows, .tar.gz for `*`nix or Mac]

# Install #

  1. Extract the .zip file (or .tar.gz file) on your desktop

# Configuration #

  1. Configure your _build.xml_

Define a classpath (`AntTaskSkype.jar` and Skype libraries dependencies):
```
	<path id="AntTaskSkype.classpath">
		<fileset dir="${project_lib}">
			<include name="**/*.jar" />
		</fileset>
	</path>
```

Define the task `AntTaskSkype:`
```
	<taskdef name="skypeSendMessage" classname="uk.co.marcoratto.ant.skype.SendMessage" classpathref="AntTaskSkype.classpath" />	

```

Use the task:
```
	<target name="testSendMessage">
		<skypeSendMessage debug="true" listofnicknames="Alice|Bob" separator="|" message="Hello World!" failonerror="false" />
	</target>	
```