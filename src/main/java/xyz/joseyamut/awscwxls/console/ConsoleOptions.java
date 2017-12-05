package xyz.joseyamut.awscwxls.console;

import static java.lang.System.out;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import xyz.joseyamut.awscwxls.console.model.ConsoleOptionsPropertiesModel;

public class ConsoleOptions extends ConsoleHelpManual {
	private static Log log = LogFactory.getLog(ConsoleOptions.class);
	private Validator validator;
	private Properties listProperties;
	private Options options;	
	private Option
				help,			// Boolean
				property,		// Requires key=value pair
				properties,		// Requires name of file
				version;		// Boolean
	public ConsoleOptionsPropertiesMapper propertiesMapper;
	public ConsoleOptionsPropertiesModel propertiesModel;
	
	public ConsoleOptions(String[] arguments) {
		options = defineOptions();
		if (arguments.length < 1) { // No arguments provided. print usage/help and stop execution with exit value -1
			printHelp(options);
			System.exit(-1);
		}
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
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
			log.error(
				"Unable to parse command-line arguments "
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
			} catch (IOException e) {
				log.error(e.getMessage());
			}
						
		} else {
			try {
				propertiesModel = new ConsoleOptionsPropertiesModel.ConsoleOptionsPropertiesBuilder()
					.dimensionName(commandLine.getOptionProperties(PROPERTY_OPTION).getProperty(ConsoleOptionsPropertiesMapper.DIMENSION_NAME_KEY))
					.endTime(
						new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(commandLine.getOptionProperties(PROPERTY_OPTION).getProperty(ConsoleOptionsPropertiesMapper.END_TIME_KEY))
					)
					.instanceId(commandLine.getOptionProperties(PROPERTY_OPTION).getProperty(ConsoleOptionsPropertiesMapper.INSTANCE_ID_KEY))
					.intervalPeriod(
						Integer.parseInt(commandLine.getOptionProperties(PROPERTY_OPTION).getProperty(ConsoleOptionsPropertiesMapper.INTERVAL_PERIOD_KEY))
					)					
					.metricNames(commandLine.getOptionProperties(PROPERTY_OPTION).getProperty(ConsoleOptionsPropertiesMapper.METRIC_NAMES_KEY))
					.outputPath(commandLine.getOptionProperties(PROPERTY_OPTION).getProperty(ConsoleOptionsPropertiesMapper.OUTPUT_PATH_KEY))
					.profileCredential(commandLine.getOptionProperties(PROPERTY_OPTION).getProperty(ConsoleOptionsPropertiesMapper.PROFILE_CREDENTIAL_KEY))
					.regionName(commandLine.getOptionProperties(PROPERTY_OPTION).getProperty(ConsoleOptionsPropertiesMapper.REGION_NAME_KEY))
					.serviceName(commandLine.getOptionProperties(PROPERTY_OPTION).getProperty(ConsoleOptionsPropertiesMapper.SERVICE_NAME_KEY))
					.startTime(
						new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(commandLine.getOptionProperties(PROPERTY_OPTION).getProperty(ConsoleOptionsPropertiesMapper.START_TIME_KEY))
					)
					.build();
			} catch (NumberFormatException | java.text.ParseException e) {
				log.error(e.getMessage());
			}
			try {
				validateProperties(propertiesModel);
				propertiesMapper = new ConsoleOptionsPropertiesMapper(propertiesModel);
				out.println(propertiesMapper.getProperties().toString());
				this.listProperties = propertiesMapper.getProperties();
			} catch(ConstraintViolationException e) {
				log.error(e.getMessage());
			}
		}
	}
	
	private void validateProperties(ConsoleOptionsPropertiesModel model) throws ConstraintViolationException {
		Set<ConstraintViolation<ConsoleOptionsPropertiesModel>> violations = validator.validate(model);
		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(new HashSet<ConstraintViolation<ConsoleOptionsPropertiesModel>>(violations));
		}
	}
	
	public Properties getProperties() {
		return listProperties;
	}
	
	private void loadPropertiesFromFile(String pathToPropertiesFile) throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream(pathToPropertiesFile));
		this.listProperties = properties;
	}
}
