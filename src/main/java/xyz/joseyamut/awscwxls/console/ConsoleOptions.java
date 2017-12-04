package xyz.joseyamut.awscwxls.console;

import static java.lang.System.out;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class ConsoleOptions extends ConsoleHelpManual {
	private Properties listProperties;
	private Options options;
	private Option
				help,			// Boolean
				property,		// Requires key=value pair
				properties,		// Requires name of file
				version;		// Boolean
	public ConsoleOptionsPropertiesMapper propertiesMapper;	
	
	public ConsoleOptions(String[] arguments) {
		options = defineOptions();
		if (arguments.length < 1) { // No arguments provided. print usage/help and stop execution with exit value -1
			printHelp(options);
			System.exit(-1);
		}
		CommandLine commandLine = generateCommandLine(options, arguments);
		processOptions(commandLine);
	}
	
	private Options defineOptions() {
		Options options = new Options();		
		help = new Option("help", HELP_OPTION_DESC);
		help.setLongOpt("help");
		version = new Option("version", VERSION_OPTION_DESC);
		version.setLongOpt("version");
		properties = Option.builder("p")
			.longOpt("properties")
			.desc(PROPERTIES_OPTION_DESC)
			.hasArg(true)
			.required(false)
			.argName("filename")
			.build();		
		property = Option.builder("D")
			.desc(PROPERTY_OPTION_DESC)
			.required(false)
			.hasArgs()
			.argName("property=value")
			.valueSeparator('=')
			.build();		
		options.addOption(help);
		options.addOption(version);
		options.addOption(property);
		options.addOption(properties);				
		return options;
	}
	
	private CommandLine generateCommandLine(Options options, String[] arguments) {
		CommandLineParser commanddLineParser = new DefaultParser();
		CommandLine commandLine = null;
		try {
			commandLine = commanddLineParser.parse(options, arguments);
		} catch (ParseException exception) {
			out.println(
				"ERROR: Unable to parse command-line arguments "
				+ Arrays.toString(arguments) 
				+ "with the following exception "
				+ exception
			);
		}
		return commandLine;
	}
	
	private void processOptions(CommandLine commandLine) {
		if (commandLine.hasOption(HELP_OPTION)) {
			printHelp(options);
			System.exit(-1);
		} else if (commandLine.hasOption(VERSION_OPTION)) {
			printVersion();
			System.exit(-1);
		} else if (commandLine.hasOption(PROPERTIES_OPTION)) {
			try {
				loadPropertiesFromFile(commandLine.getOptionValue(PROPERTIES_OPTION));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		} else {
			if (validateProperties(commandLine)) {
				propertiesMapper = new ConsoleOptionsPropertiesMapper.ConsoleOptionsPropertiesBuilder()				
					.profileCredential(commandLine.getOptionProperties(PROPERTY_OPTION).getProperty(ConsoleOptionsPropertiesMapper.PROFILE_CREDENTIAL_KEY))
					.regionName(commandLine.getOptionProperties(PROPERTY_OPTION).getProperty(ConsoleOptionsPropertiesMapper.REGION_NAME_KEY))
					.startTime(commandLine.getOptionProperties(PROPERTY_OPTION).getProperty(ConsoleOptionsPropertiesMapper.START_TIME_KEY))
					.endTime(commandLine.getOptionProperties(PROPERTY_OPTION).getProperty(ConsoleOptionsPropertiesMapper.END_TIME_KEY))
					.outputPath(commandLine.getOptionProperties(PROPERTY_OPTION).getProperty(ConsoleOptionsPropertiesMapper.OUTPUT_PATH_KEY))
					.intervalPeriod(Integer.parseInt(commandLine.getOptionProperties(PROPERTY_OPTION).getProperty(ConsoleOptionsPropertiesMapper.INTERVAL_PERIOD_KEY)))
					.instanceId(commandLine.getOptionProperties(PROPERTY_OPTION).getProperty(ConsoleOptionsPropertiesMapper.INSTANCE_ID_KEY))
					.metricNames(commandLine.getOptionProperties(PROPERTY_OPTION).getProperty(ConsoleOptionsPropertiesMapper.METRIC_NAMES_KEY))
					.build();
				this.listProperties = propertiesMapper.getProperties();
			}
		}
	}
	
	public Properties getProperties() {
		return listProperties;
	}
	
	private void loadPropertiesFromFile(String pathToPropertiesFile) throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream(pathToPropertiesFile));
		propertiesMapper = new ConsoleOptionsPropertiesMapper.ConsoleOptionsPropertiesBuilder()				
			.profileCredential(properties.getProperty(ConsoleOptionsPropertiesMapper.PROFILE_CREDENTIAL_KEY))
			.regionName(properties.getProperty(ConsoleOptionsPropertiesMapper.REGION_NAME_KEY))
			.startTime(properties.getProperty(ConsoleOptionsPropertiesMapper.START_TIME_KEY))
			.endTime(properties.getProperty(ConsoleOptionsPropertiesMapper.END_TIME_KEY))
			.outputPath(properties.getProperty(ConsoleOptionsPropertiesMapper.OUTPUT_PATH_KEY))
			.intervalPeriod(Integer.parseInt(properties.getProperty(ConsoleOptionsPropertiesMapper.INTERVAL_PERIOD_KEY)))
			.instanceId(properties.getProperty(ConsoleOptionsPropertiesMapper.INSTANCE_ID_KEY))
			.metricNames(properties.getProperty(ConsoleOptionsPropertiesMapper.METRIC_NAMES_KEY))
			.build();
		this.listProperties = propertiesMapper.getProperties();
	}
	
	private boolean validateProperties(CommandLine commandLine) {		
		try {			
			if (commandLine.hasOption(PROPERTY_OPTION)) {
				Properties properties = commandLine.getOptionProperties(PROPERTY_OPTION);
				if (properties.containsKey(ConsoleOptionsPropertiesMapper.END_TIME_KEY)
						&& properties.getProperty(ConsoleOptionsPropertiesMapper.END_TIME_KEY).isEmpty()) {
					throw new IllegalArgumentException(ConsoleOptionsPropertiesMapper.END_TIME_KEY.concat(" is required"));
				}
				if (properties.containsKey(ConsoleOptionsPropertiesMapper.INSTANCE_ID_KEY) 
						&& properties.getProperty(ConsoleOptionsPropertiesMapper.INSTANCE_ID_KEY).isEmpty()) {
					throw new IllegalArgumentException(ConsoleOptionsPropertiesMapper.INSTANCE_ID_KEY.concat(" should not be empty"));
				}
				if (properties.containsKey(ConsoleOptionsPropertiesMapper.INTERVAL_PERIOD_KEY)
						&& properties.getProperty(ConsoleOptionsPropertiesMapper.INTERVAL_PERIOD_KEY).isEmpty()
						|| properties.getProperty(ConsoleOptionsPropertiesMapper.INTERVAL_PERIOD_KEY).equals("0")) {
					throw new IllegalArgumentException(ConsoleOptionsPropertiesMapper.INTERVAL_PERIOD_KEY.concat(" should not be empty or equal to zero"));
				}
				if (properties.containsKey(ConsoleOptionsPropertiesMapper.METRIC_NAMES_KEY)
						&& properties.getProperty(ConsoleOptionsPropertiesMapper.METRIC_NAMES_KEY).isEmpty()) {
					throw new IllegalArgumentException(ConsoleOptionsPropertiesMapper.METRIC_NAMES_KEY.concat(" must be a valid path"));
				}
				if (properties.containsKey(ConsoleOptionsPropertiesMapper.OUTPUT_PATH_KEY)
						&& properties.getProperty(ConsoleOptionsPropertiesMapper.OUTPUT_PATH_KEY).isEmpty()) {
					throw new IllegalArgumentException(ConsoleOptionsPropertiesMapper.OUTPUT_PATH_KEY.concat(" must be a valid path"));
				}
				if (properties.containsKey(ConsoleOptionsPropertiesMapper.PROFILE_CREDENTIAL_KEY)
						&& properties.getProperty(ConsoleOptionsPropertiesMapper.PROFILE_CREDENTIAL_KEY).isEmpty()) {
					throw new IllegalArgumentException(ConsoleOptionsPropertiesMapper.PROFILE_CREDENTIAL_KEY.concat(" should be in the settings.ini of your AWS credentials"));
				}
				if (properties.containsKey(ConsoleOptionsPropertiesMapper.REGION_NAME_KEY)
						&& properties.getProperty(ConsoleOptionsPropertiesMapper.REGION_NAME_KEY).isEmpty()) {
					throw new IllegalArgumentException(ConsoleOptionsPropertiesMapper.REGION_NAME_KEY.concat(" must be a valid AWS region"));
				}
				if (properties.containsKey(ConsoleOptionsPropertiesMapper.START_TIME_KEY)
						&& properties.getProperty(ConsoleOptionsPropertiesMapper.START_TIME_KEY).isEmpty()) {
					throw new IllegalArgumentException(ConsoleOptionsPropertiesMapper.START_TIME_KEY.concat(" is required"));
				}
			}
			return true;
		} catch (Exception exception) {
			out.println(
				"ERROR: Trying to get a property(s) of an object with the following exception "
				+ exception
			);
		}
		return false;		
	}
}
