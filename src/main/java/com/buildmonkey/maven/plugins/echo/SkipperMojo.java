package com.buildmonkey.maven.plugins.echo;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;

/**
 * @goal skipper
 * @execute phase="validate"
 **/
public class SkipperMojo extends AbstractMojo
{

    @Parameter( defaultValue = "${session}", readonly = true )
    private MavenSession session;

    @Parameter( defaultValue = "${project}", readonly = true )
    private MavenProject project;

    /**
     * Base directory of the project.
     */
    @Parameter( defaultValue = "${project.basedir}", readonly = true )
    private File basedir;

    /**
     * This will cause the assembly to run only at the top of a given module tree. That is, run in the project
     * contained in the same folder where the mvn execution was launched.
     */
    @Parameter(defaultValue="false", required=false)
    private boolean runOnlyAtExecutionRoot;

    public void execute()
            throws MojoExecutionException, MojoFailureException
    {

        //run only at the execution root.
        if (runOnlyAtExecutionRoot && !isThisTheExecutionRoot())
        {
            getLog().info( "Skipping the assembly in this project because it's not the Execution Root" );
        }
        else
        {
            getLog().info("Doing something usefull");
        }
    }

    /**
     * Returns true if the current project is located at the Execution Root Directory (where mvn was launched)
     * @return
     */
    protected boolean isThisTheExecutionRoot()
    {
        Log log = this.getLog();
        log.debug("Root Folder:" + session.getExecutionRootDirectory());
        log.debug("Current Folder:"+ basedir );
        boolean result = session.getExecutionRootDirectory().equalsIgnoreCase(basedir.toString());
        if (result)
        {
            log.debug( "This is the execution root." );
        }
        else
        {
            log.debug( "This is NOT the execution root." );
        }
        return result;
    }
}