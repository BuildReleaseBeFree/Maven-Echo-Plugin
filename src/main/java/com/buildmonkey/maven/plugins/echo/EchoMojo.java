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
package com.buildmonkey.maven.plugins.echo;

import java.io.File;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.LegacySupport;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;

import java.util.Scanner;

import com.buildmonkey.terminal.banner.FigletFont;

/**
 * @author <a href="mailto:james@purplenoise.net">James Borkowski</a>
 */
@Mojo(name="echo", requiresProject = true, threadSafe=true)
public class EchoMojo extends AbstractEchoPlugIn {

    @Requirement
    private LegacySupport legacySupport;

    /**
     * Base directory of the project.
     *
     * @parameter default-value="${basedir}"
     * @required
     * @readonly
     */
    private File basedir;

    /**
     * This will cause the assembly to run only at the top of a given module tree. That is, run in the project
     * contained in the same folder where the mvn execution was launched.
     * @parameter expression="${runOnlyAtExecutionRoot}" default-value="false"
     * @since 2.2-beta-4
     */
    @Parameter(defaultValue="false", required=false)
    private boolean runOnlyAtExecutionRoot;

    // boolean result = legacySupport.getSession().getExecutionRootDirectory().equalsIgnoreCase(basedir.toString());

    protected boolean isThisTheExecutionRoot()
    {
        getLog().debug("Root Folder:" + legacySupport.getSession().getExecutionRootDirectory());
        getLog().debug("Current Folder:" + basedir);
        boolean result = legacySupport.getSession().getExecutionRootDirectory().equalsIgnoreCase(basedir.toString());
        if (result)
        {
            getLog().debug("This is the execution root.");
        }
        else
        {
            getLog().debug("This is NOT the execution root.");
        }
        return result;
    }

	public static int getCharacterCountLongestLine(String toParse){
		//Parses multi-line text and returns a character count of the longest line
		Scanner Scanner = new Scanner( toParse );
		int lines=0;
		int characters=0;
		int maxCharacters=0;
		int wrapCharacters=80;
		while(Scanner.hasNextLine()){
			String line = Scanner.nextLine();
			lines++;
			characters+=line.length();
			if(maxCharacters<line.length()){
				maxCharacters = line.length();
			}
		}
		return maxCharacters;
	}
	
	public void renderMavenOutputScanner(Scanner scanner){
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
	}

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
	 * This will turn on or off the LARGE text heading that displays
	 * the text set with the 'default-text' property, or, if not set
	 * defaults to 'project.artifactId' 'project.version' and uses
	 * the jFiglet library to display it as ASCII Art.
	 * 
	 */
	@Parameter(defaultValue="false", required=false)
	private boolean headerEnable;

    /**
     * This will turn on or off the LARGE text heading that displays
     * the text set with the 'default-text' property, or, if not set
     * defaults to 'project.artifactId' 'project.version' and uses
     * the jFiglet library to display it as ASCII Art.
     *
     */
    @Parameter(defaultValue="false", required=false)
    private boolean headingEnable;

    /**
     * This will turn on or off the LARGE text heading that displays
     * the text set with the 'default-text' property, or, if not set
     * defaults to 'project.artifactId' 'project.version' and uses
     * the jFiglet library to display it as ASCII Art.
     *
     */
    @Parameter(defaultValue="false", required=false)
    private boolean subheadingEnable;

    /**
     * This will turn on or off the LARGE text heading that displays
     * the text set with the 'default-text' property, or, if not set
     * defaults to 'project.artifactId' 'project.version' and uses
     * the jFiglet library to display it as ASCII Art.
     *
     */
    @Parameter(defaultValue="false", required=false)
    private boolean footerEnable;

	/**
	 * This will set the text width in characters before the text being
	 * displayed be wrapped to the next line.
	 * 
	 */
	@Parameter(defaultValue="120", required=false)
	private int textWidth;

    /**
     * This will set the text to use for the heading,
     * the given message will be printed out as a heading using
     * the jFiglet library to display it as ASCII Art.
     *
     */
    @Parameter(defaultValue="test", required=false)
    private String headerText;

    /**
     * This will set the text to use for the heading,
     * the given message will be printed out as a heading using
     * the jFiglet library to display it as ASCII Art.
     *
     */
    @Parameter(defaultValue="${project.artifactId}", required=false)
    private String headingText;

    /**
     * This will set the text to use for the heading,
     * the given message will be printed out as a heading using
     * the jFiglet library to display it as ASCII Art.
     *
     */
    @Parameter(defaultValue="${project.version}", required=false)
    private String subheadingText;

    /**
     * This will set the text to use for the heading,
     * the given message will be printed out as a heading using
     * the jFiglet library to display it as ASCII Art.
     *
     */
    @Parameter(defaultValue="test", required=false)
    private String footerText;


    /**
     * This will set the text to use for the heading,
     * the given message will be printed out as a heading using
     * the jFiglet library to display it as ASCII Art.
     *
     */
    @Parameter(defaultValue="small", required=false)
    private String headerFont;

    /**
     * This will set the text to use for the heading,
     * the given message will be printed out as a heading using
     * the jFiglet library to display it as ASCII Art.
     *
     */
    @Parameter(defaultValue="small", required=false)
    private String headingFont;

    /**
     * This will set the text to use for the heading,
     * the given message will be printed out as a heading using
     * the jFiglet library to display it as ASCII Art.
     *
     */
    @Parameter(defaultValue="small", required=false)
    private String subheadingFont;

    /**
     * This will set the text to use for the heading,
     * the given message will be printed out as a heading using
     * the jFiglet library to display it as ASCII Art.
     *
     */
    @Parameter(defaultValue="small", required=false)
    private String footerFont;

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
        //run only at the execution root.
        if ( ( runOnlyAtExecutionRoot && !isThisTheExecutionRoot() ) || ! runOnlyAtExecutionRoot )
        {
            getLog().info( "Skipping the assembly in this project because it's not the Execution Root" );
        }
        else
        {
            getLog().info("As this appeared to be the Execution Root or we don't care, we are doing something useful");
            if ( headerEnable == true )
            {
                headerText = headerText.substring(0, 1).toUpperCase() + headerText.substring(1);
                String asciiArt = FigletFont.getBannerAsFontMaxWidth(headerFont, textWidth, headerText);
                Scanner scanner = new Scanner( asciiArt );
                renderMavenOutputScanner(scanner);
            }

            if ( headingEnable == true )
            {
                headingText = headingText.substring(0, 1).toUpperCase() + headingText.substring(1);
                String asciiArt = FigletFont.getBannerAsFontMaxWidth(headingFont, textWidth, headingText);
                //String asciiArt = FigletFont.convertOneLine( headingText );
                Scanner scanner = new Scanner( asciiArt );
/*			String headingTextToDo = "";
			while ( getCharacterCountLongestLine( asciiArt ) > textWidth ){
				while ( getCharacterCountLongestLine( asciiArt ) > textWidth ){
					headingTextToDo=Character.toString(headingText.charAt(headingText.length()-1))+headingTextToDo;
					headingText=headingText.substring(0, headingText.length()-1);
                    asciiArt = FigletFont.getBannerAsFontMaxWidth("standard", textWidth, headingText);
					//asciiArt = FigletFont.convertOneLine( headingText );
					scanner = new Scanner( asciiArt );
				}
				renderMavenOutputScanner(scanner);
				headingText=headingTextToDo;
				headingTextToDo="";
                asciiArt = FigletFont.getBannerAsFontMaxWidth("standard", textWidth, headingText);
				//asciiArt = FigletFont.convertOneLine( headingText );
				scanner = new Scanner( asciiArt );
			}*/
                renderMavenOutputScanner(scanner);
            }


            if ( subheadingEnable == true )
            {
                subheadingText = subheadingText.substring(0, 1).toUpperCase() + subheadingText.substring(1);
                String asciiArt = FigletFont.getBannerAsFontMaxWidth(subheadingFont, textWidth, subheadingText);
                Scanner scanner = new Scanner( asciiArt );
                renderMavenOutputScanner(scanner);
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

            if ( footerEnable == true )
            {
                footerText = footerText.substring(0, 1).toUpperCase() + footerText.substring(1);
                String asciiArt = FigletFont.getBannerAsFontMaxWidth(footerFont, textWidth, footerText);
                Scanner scanner = new Scanner( asciiArt );
                renderMavenOutputScanner(scanner);
            }
        }
	}

}
