package xyz.joseyamut.awscwxls.console;

import static java.lang.System.out;

import java.io.PrintWriter;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

public class ConsoleHelpManual {
	private static final String version		= "1.0.1";
	private static String command 			= "java -jar awscwxls.jar";	
	protected final String
		// Option names
		HELP_OPTION							= "help",
		PROPERTIES_OPTION					= "properties",
		PROPERTY_OPTION						= "D",
		PROPERTY_OPTION_END_TIME			= "endTime",
		PROPERTY_OPTION_INSTANCE_ID			= "instanceId",
		PROPERTY_OPTION_INTERVAL_PERIOD		= "intervalPeriod",
		PROPERTY_OPTION_METROIC_NAMES		= "metricNames",
		PROPERTY_OPTION_OUTPUT_PATH			= "outputPath",
		PROPERTY_OPTION_PROFILE_CREDENTIAL	= "profileCrdential",
		PROPERTY_OPTION_REGION				= "region",
		PROPERTY_OPTION_START_TIME			= "startTime",		
		VERSION_OPTION						= "version",
		// Option descriptions
		HELP_OPTION_DESC 					= "Print a summary of available command-line options",
		PROPERTIES_OPTION_DESC				= "Use given properties file",
		PROPERTY_OPTION_DESC				= "Use value for given property",
		VERSION_OPTION_DESC					= "Print the version number";		

	public ConsoleHelpManual() { }
	
	protected static void printUsage(Options options) {
		final HelpFormatter formatter = new HelpFormatter();
		formatter.printUsage(new PrintWriter(out), 240, command, options);
//		out.println("\n");
	}
	
	protected static void printHelp(Options options) {
		final HelpFormatter formatter = new HelpFormatter();
		final String usageHeader = new StringBuilder().append(String.format("%n")).append("Command-line options reference for the AWS-CloudWatch-Export-Metrics tool").append(String.format("%n%n")).toString();
		final String usageFooter = new StringBuilder().append(String.format("%n%n")).append("See https://10.8.0.1/AWS-CloudWatch-Export-Metrics @ Nano Code Repository for further details.").toString();	      
		formatter.printHelp(command, usageHeader, options, usageFooter);
//		out.println("\n");
	}

	protected static void printVersion() {
		out.format(new StringBuilder().append("AWS-CloudWatch-Export-Metrics tool version " + version + "\n").append(String.format("%n")).toString());
		PrintWriter printWriter = new PrintWriter(out);
		printWriter.flush();
	}

}
