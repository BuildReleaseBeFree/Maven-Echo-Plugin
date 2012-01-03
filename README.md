# Maven Echo Plugin

# Overview

If you are working with Maven it often happens that you come to the point
where you get the feeling to print out some kind of message during the build.
But how can you do that? Some people think a second time and came to the 
Maven-AntRun-Plugin and use some ant task. But why does not exist
a simple small plugin which can simply print out some kind of messages.

Exactly for such situations the Maven Echo Plugin is intended.


## License

[Apache License, Version 2.0, January 2004](http://www.apache.org/licenses/)

## Issue Tracker

[The Issue Tracker](https://github.com/khmarbaise/Maven-Echo-Plugin/issues)

## Status

 * First Idea


## TODOs

## Usage

The first and simplest usage is to configure the Maven Licenses Verifier Plugin

    <plugin>
      <groupId>com.soebes.maven.plugins</groupId>
      <artifactId>maven-echo-plugin</artifactId>
      <version>0.1</version>
      <executions>
        <execution>
          <phase>initialization</phase>
          <goals>
            <goal>print</goal>
          </goals>
        </execution>
      </executions>
      <configuration>
        <messages>This is the Text which will be printed out.</messages>
      </configuration>
    </plugin>

## Settings Configuration

If you like you can configure an appropriate plugin group in your
settings.xml file to make life a little bit easier.

    <settings>
      ...
      <pluginGroups>
        <pluginGroup>com.soebes.maven.plugins</pluginGroup>
      </pluginGroups>
      ...
    </settings>

The above setting makes it possible to call the plugin on command 
line simply:

  mvn -Dmessage="This is a Test" echo:show


## Reporting about Licenses



Examples
--------

