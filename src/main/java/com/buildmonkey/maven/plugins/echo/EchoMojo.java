/**
 * The BuildMonkey Maven Echo Banner Plugin
 * - feat. the BuildMonkey Terminal Toolkit
 * - based on the jFiglet implementation of the classic Unix Figlet Ascii banner tool
 * - Derivative works of the Maven Echo Plugin, original copyright acknowledgement given to
 *   SoftwareEntwicklung Beratung Schulung (SoEBeS) & Karl Heinz Marbaise 2012
 * 
 * Copyright (c) 2014 by BuildMonkey
 * Copyright (c) 2014 by James Borkowski
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

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.util.Scanner;

import com.buildmonkey.terminal.banner.FigletFont;

/**
 * @author <a href="mailto:james@purplenoise.net">James Borkowski</a>
 */
@Mojo( name="echo", requiresProject=true, threadSafe=true )
public class EchoMojo extends AbstractEchoPlugIn {

    /**
     * MavenProject of the Maven project.
     */
    @Parameter( defaultValue="${project}", readonly=true )
    private MavenProject project;

    /**
     * MavenSession of the Maven project.
     */
    @Parameter( defaultValue="${session}", readonly = true )
    private MavenSession session;

    /**
     * Base directory of the Maven project.
     */
    @Parameter( defaultValue="${project.basedir}", readonly=true )
    private File basedir;

    /**
     * This will cause the assembly to run only at the top of a given module tree. That is, run in the project
     * contained in the same folder where the mvn execution was launched.
     */
    @Parameter( defaultValue="false", required=false )
    private boolean runOnlyAtExecutionRoot;

	public static int getCharacterCountLongestLine( String toParse ){
        // Internal method to parse a multi-line piece of text and return a character count of the longest line
		Scanner Scanner = new Scanner( toParse );
		int characters=0;
		int maxCharacters=0;
		while( Scanner.hasNextLine() ){
			String line = Scanner.nextLine();
			characters += line.length();
			if( maxCharacters < line.length() ){
				maxCharacters = line.length();
			}
		}
		return maxCharacters;
	}
	
	public void renderMavenOutputScanner( Scanner scanner ){
		switch ( logLevel ) 
		{
		case DEBUG:
			while ( scanner.hasNextLine() )
			{
				String line = scanner.nextLine();
				// process the line
				getLog().debug( line );
			}
			scanner.close();
			break;
		case ERROR:
			while ( scanner.hasNextLine() )
			{
				String line = scanner.nextLine();
				// process the line
				getLog().error( line );
			}
			scanner.close();
			break;
		case INFO:
			while ( scanner.hasNextLine() )
			{
				String line = scanner.nextLine();
				// process the line
				getLog().info( line );
			}
			scanner.close();
			break;
		case WARNING:
			while ( scanner.hasNextLine() )
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
	 * The different levels which exist to printout the echos.
	 */
	public enum LogLevels {
		INFO,
		WARNING,
		ERROR,
		DEBUG,
	}

	/**
	 * This will define in which logging level the given messages will be printed out.
     * Their exist the following logging levels:
	 * <ul>
	 * <li>INFO</li>
	 * <li>WARNING</li>
	 * <li>ERROR<li>
	 * <li>DEBUG<li>
	 * </ul>
	 */
	@Parameter( defaultValue="INFO", required=true )
	private LogLevels logLevel;

	/**
	 * This will turn on or off the LARGE text heading that displays the text set with the 'default-text' property, or,
     * if not set defaults to 'project.artifactId' 'project.version' and uses the jFiglet library to display it as
     * ASCII Art.
	 */
	@Parameter( defaultValue="false", required=false )
	private boolean headerEnable;

    /**
     * This will turn on or off the LARGE text heading that displays the text set with the 'default-text' property, or,
     * if not set defaults to 'project.artifactId' 'project.version' and uses the jFiglet library to display it as
     * ASCII Art.
     */
    @Parameter( defaultValue="false", required=false )
    private boolean headingEnable;

    /**
     * This will turn on or off the LARGE text heading that displays the text set with the 'default-text' property, or,
     * if not set defaults to 'project.artifactId' 'project.version' and uses the jFiglet library to display it as
     * ASCII Art.
     */
    @Parameter( defaultValue="false", required=false )
    private boolean subheadingEnable;

    /**
     * This will turn on or off the LARGE text heading that display the text set with the 'default-text' property, or,
     * if not set defaults to 'project.artifactId' 'project.version' and uses the jFiglet library to display it as
     * ASCII Art.
     */
    @Parameter( defaultValue="false", required=false )
    private boolean footerEnable;

	/**
	 * This will set the text width in characters before the text being displayed be wrapped to the next line.
	 */
	@Parameter( defaultValue="120", required=false )
	private int textWidth;

    /**
     * This will set the text to use for the heading, the given message will be printed out as a heading using
     * the jFiglet library to display it as ASCII Art.
     */
    @Parameter( defaultValue="test", required=false )
    private String headerText;

    /**
     * This will set the text to use for the heading, the given message will be printed out as a heading using
     * the jFiglet library to display it as ASCII Art.
     *
     */
    @Parameter( defaultValue="${project.artifactId}", required=false )
    private String headingText;

    /**
     * This will set the text to use for the heading, the given message will be printed out as a heading using
     * the jFiglet library to display it as ASCII Art.
     */
    @Parameter( defaultValue="${project.version}", required=false )
    private String subheadingText;

    /**
     * This will set the text to use for the heading, the given message will be printed out as a heading using
     * the jFiglet library to display it as ASCII Art.
     */
    @Parameter( defaultValue="test", required=false )
    private String footerText;


    /**
     * This will set the text to use for the heading, the given message will be printed out as a heading using
     * the jFiglet library to display it as ASCII Art.
     */
    @Parameter( defaultValue="small", required=false )
    private String headerFont;

    /**
     * This will set the text to use for the heading, the given message will be printed out as a heading using
     * the jFiglet library to display it as ASCII Art.
     */
    @Parameter( defaultValue="small", required=false )
    private String headingFont;

    /**
     * This will set the text to use for the heading, the given message will be printed out as a heading using
     * the jFiglet library to display it as ASCII Art.
     */
    @Parameter( defaultValue="small", required=false )
    private String subheadingFont;

    /**
     * This will set the text to use for the heading, the given message will be printed out as a heading using
     * the jFiglet library to display it as ASCII Art.
     */
    @Parameter( defaultValue="small", required=false )
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
	@Parameter( required=true )
	private List<String> echos;

	/*
	 * (non-Javadoc)
	 * @see org.apache.maven.plugin.Mojo#execute()
	 */
	public void execute () throws MojoExecutionException
	{
        if ( runOnlyAtExecutionRoot && ! project.isExecutionRoot() ) {
            getLog().debug( "This does not appear to be a root module and we must not have runOnlyAtExecutionRoot " +
                    "set to true, so not executing this time..." );
            return ;
        }
        getLog().debug( "As this appeared to be the Execution Root or runOnlyAtExecutionRoot is set to false, " +
                "we are going to do something useful..." );
        if ( headerEnable == true ) {
            getLog().debug( "As headerEnable is set to true, we are going to render our banner..." );
            headerText = headerText.substring( 0, 1 ).toUpperCase() + headerText.substring( 1 );
            String asciiArt = FigletFont.getBannerAsFontMaxWidth( headerFont, textWidth, headerText );
            Scanner scanner = new Scanner( asciiArt );
            renderMavenOutputScanner( scanner );
        }

        if ( headingEnable == true ) {
            getLog().debug( "As headingEnable is set to true, we are going to render our banner..." );
            headingText = headingText.substring( 0, 1 ).toUpperCase() + headingText.substring( 1 );
            String asciiArt = FigletFont.getBannerAsFontMaxWidth( headingFont, textWidth, headingText );
            Scanner scanner = new Scanner( asciiArt );
            renderMavenOutputScanner( scanner );
        }


        if ( subheadingEnable == true ) {
            getLog().debug( "As subheadingEnable is set to true, we are going to render our banner..." );
            subheadingText = subheadingText.substring( 0, 1 ).toUpperCase() + subheadingText.substring( 1 );
            String asciiArt = FigletFont.getBannerAsFontMaxWidth( subheadingFont, textWidth, subheadingText );
            Scanner scanner = new Scanner( asciiArt );
            renderMavenOutputScanner( scanner );
        }

        getLog().debug( "No logic here, we will do the echo thang for each echo we have..." );
        for ( String item : echos ) {
            getLog().debug( "Echoing an echo!! Well, it is debug level... :-)" );
            switch ( logLevel ) {
                case DEBUG:
                    getLog().debug( item );
                    break;
                case ERROR:
                    getLog().error( item );
                    break;
                case INFO:
                    getLog().info( item );
                    break;
                case WARNING:
                    getLog().warn( item );
                    break;
            }
        }

        if ( footerEnable == true ) {
            getLog().debug( "As footerEnable is set to true, we are going to render our banner..." );
            footerText = footerText.substring( 0, 1 ).toUpperCase() + footerText.substring( 1 );
            String asciiArt = FigletFont.getBannerAsFontMaxWidth( footerFont, textWidth, footerText );
            Scanner scanner = new Scanner( asciiArt );
            renderMavenOutputScanner( scanner );
        }
    }
}
