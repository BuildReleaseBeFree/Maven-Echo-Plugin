/**
 * The Maven Echo Plugin
 * 
 * Copyright (c) 2012 by SoftwareEntwicklung Beratung Schulung (SoEBeS)
 * Copyright (c) 2012 by Karl Heinz Marbaise
 * 
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.buildmonkey.maven.plugins.echo;

import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.util.Scanner;

import com.github.lalyos.jfiglet.FigletFont;

/**
 * @author <a href="mailto:james@purplenoise.net">James Borkowski</a>
 */
@Mojo(name="echo", requiresProject = true, threadSafe=true)
public class EchoMojo extends AbstractEchoPlugIn {

  /**
   * The different levels 
   * which exist to printout the echos.
   */
  public enum LogLevels {
	INFO,
	WARNING,
  	ERROR,
  	DEBUG,
  }
	
  /**
   * This will define in which logging level 
   * the given messages will be printed out.
   * Their exist the following logging levels:
   * <ul>
   * <li>INFO</li>
   * <li>WARNING</li>
   * <li>ERROR<li>
   * <li>DEBUG<li>
   * </ul>
   * 
   */
  @Parameter(defaultValue="INFO", required=true)
  private LogLevels logLevel;
  

  /**
   * This will set the text to use for the heading,
   * the given message will be printed out as a heading using
   * the jFiglet library to display it as ASCII Art.
   * 
   */
  @Parameter(defaultValue="${project.artifactId} ${project.version}", required=false)
  private String heading;
	
  /**
   * The messages you would like to print out.
   * 
   * <pre>
   *    ..
   *    &lt;configuration&gt;
   *      &lt;echos&gt;
   *        &lt;echo&gt;Message Line Nr. 1&lt;/echo&gt;
   *        &lt;echo&gt;Message Line Nr. 2&lt;/echo&gt;
   *      &lt;/echos&gt;
   *    &lt;/configuration&gt;
   *    ..
   * </pre>
   */
  @Parameter(required = true)
  private List<String> echos;

    /*
     * (non-Javadoc)
     * @see org.apache.maven.plugin.Mojo#execute()
     */
    public void execute () throws MojoExecutionException
    {
        String asciiArt = FigletFont.convertOneLine( heading );
        Scanner scanner = new Scanner( asciiArt );
        switch ( logLevel ) 
        {
            case DEBUG:
                while (scanner.hasNextLine()) 
                {
                    String line = scanner.nextLine();
                    // process the line
                    getLog().debug( line );
                }
                scanner.close();
                break;
            case ERROR:
                while (scanner.hasNextLine()) 
                {
                    String line = scanner.nextLine();
                    // process the line
                    getLog().error( line );
                }
                scanner.close();
                break;
            case INFO:
                while (scanner.hasNextLine()) 
                {
                    String line = scanner.nextLine();
                    // process the line
                    getLog().info( line );
                }
                scanner.close();
                break;
            case WARNING:
                while (scanner.hasNextLine()) 
                {
                    String line = scanner.nextLine();
                    // process the line
                    getLog().warn( line );
                }
                scanner.close();
                break;
        }

        for ( String item : echos )
        {
            switch ( logLevel ) 
            {
                case DEBUG:
                    getLog ( ).debug ( item );
                    break;
                case ERROR:
                    getLog ( ).error ( item );
                    break;
                case INFO:
                    getLog ( ).info ( item );
                    break;
                case WARNING:
                    getLog ( ).warn ( item );
                    break;
            }
        }
    }

}
