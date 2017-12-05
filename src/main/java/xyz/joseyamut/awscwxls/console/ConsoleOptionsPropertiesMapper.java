package xyz.joseyamut.awscwxls.console;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Properties;

import xyz.joseyamut.awscwxls.console.model.ConsoleOptionsPropertiesModel;

public class ConsoleOptionsPropertiesMapper {
	ConsoleOptionsPropertiesModel model;
	public static final String
//		Expected keys
		DIMENSION_NAME_KEY				= "dimensionName.0",
		END_TIME_KEY					= "endTime",		
		INSTANCE_ID_KEY					= "dimensionValues.0",
		INTERVAL_PERIOD_KEY				= "periodMinutes.0",
		METRIC_NAMES_KEY				= "metricStatisticNames.0",
		OUTPUT_PATH_KEY					= "xlsPathNamePrefix",		
		PROFILE_CREDENTIAL_KEY			= "credentialsProfileName",
		REGION_NAME_KEY					= "credentialsRegion",		
		SERVICE_NAME_KEY				= "namespace.0",
		START_TIME_KEY					= "startTime",
//		Default values
		DIMENSION_NAME_DEFAULT			= "InstanceId",
		METRIC_NAMES_DEFAULT			= "CPUUtilization|Average CPUUtilization|Minimum CPUUtilization|Maximum NetworkIn|Sum NetworkOut|Sum",
		OUTPUT_PATH_DEFAULT				= System.getProperty("user.dir").concat(File.separator).concat("exports"),
		PROFILE_CREDENTIAL_DEFAULT		= "default",
		REGION_NAME_DEFAULT				= "ap-southeast-1",
		SERVICE_NAME_DEFAULT			= "AWS/EC2";
	public static final int
		INTERVAL_PERIOD_DEFAULT			= 5;
		
	
	public ConsoleOptionsPropertiesMapper(ConsoleOptionsPropertiesModel model) {
		model.setDimensionName(getValueOrDefault(model.getDimensionName(), DIMENSION_NAME_DEFAULT));
		model.setMetricNames(getValueOrDefault(model.getMetricNames(), METRIC_NAMES_DEFAULT));
		model.setOutputPath(getValueOrDefault(model.getOutputPath(), OUTPUT_PATH_DEFAULT));
		model.setProfileCredential(getValueOrDefault(model.getProfileCredential(), PROFILE_CREDENTIAL_DEFAULT));
		model.setRegionName(getValueOrDefault(model.getRegionName(), REGION_NAME_DEFAULT));
		model.setServiceName(getValueOrDefault(model.getServiceName(), SERVICE_NAME_DEFAULT));
		this.model = model; 
	}
	
	private <T> T getValueOrDefault(T value, T defaultValue) {
		return value == null ? defaultValue : value;
	}	
	
	public Properties getProperties() {
		Properties properties = new Properties();
		properties.setProperty(DIMENSION_NAME_KEY, model.getDimensionName());
		properties.setProperty(END_TIME_KEY, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(model.getEndTime()));			
		properties.setProperty(INSTANCE_ID_KEY, model.getInstanceId());
		properties.setProperty(INTERVAL_PERIOD_KEY, Integer.toString(model.getIntervalPeriod()));
		properties.setProperty(METRIC_NAMES_KEY, model.getMetricNames());
		properties.setProperty(OUTPUT_PATH_KEY, model.getOutputPath());
		properties.setProperty(PROFILE_CREDENTIAL_KEY, model.getProfileCredential());
		properties.setProperty(REGION_NAME_KEY, model.getRegionName());		
		properties.setProperty(SERVICE_NAME_KEY, model.getServiceName());
		properties.setProperty(START_TIME_KEY, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(model.getStartTime()));	
		return properties;
	}


}
