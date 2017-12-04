package xyz.joseyamut.awscwxls.console;

import java.util.Properties;

public class ConsoleOptionsPropertiesMapper extends ConsoleOptionsPropertiesModel {
	public static final String
		END_TIME_KEY					= "endTime",
		DIMENSION_NAME_KEY				= "dimensionName.0",
		INSTANCE_ID_KEY					= "dimensionValues.0",
		INTERVAL_PERIOD_KEY				= "periodMinutes.0",
		METRIC_NAMES_KEY				= "metricStatisticNames.0",
		OUTPUT_PATH_KEY					= "xlsPathNamePrefix",		
		PROFILE_CREDENTIAL_KEY			= "credentialsProfileName",
		REGION_NAME_KEY					= "credentialsRegion",		
		SERVICE_NAME_KEY				= "namespace.0",
		START_TIME_KEY					= "startTime";
	private final String
		DIMENSION_NAME_VALUE			= "InstanceId",
		SERVICE_NAME_VALUE				= "AWS/EC2";
	
	public ConsoleOptionsPropertiesMapper() { 
		this.dimensionName = DIMENSION_NAME_VALUE;
		this.serviceName = SERVICE_NAME_VALUE;
	}
	
	private ConsoleOptionsPropertiesMapper(ConsoleOptionsPropertiesBuilder builder) {
		this.endTime = builder.endTime;
		this.dimensionName = DIMENSION_NAME_VALUE;		
		this.instanceId = builder.instanceId;
		this.intervalPeriod = builder.intervalPeriod;
		this.metricNames = builder.metricNames;
		this.outputPath = builder.outputPath;		
		this.profileCredential = builder.profileCredential;
		this.regionName = builder.regionName;
		this.serviceName = SERVICE_NAME_VALUE;
		this.startTime = builder.startTime;
	}
	
	public Properties getProperties() {
		Properties properties = new Properties();
		properties.setProperty(END_TIME_KEY, endTime);
		properties.setProperty(DIMENSION_NAME_KEY, dimensionName);		
		properties.setProperty(INSTANCE_ID_KEY, instanceId);
		properties.setProperty(METRIC_NAMES_KEY, metricNames);
		properties.setProperty(OUTPUT_PATH_KEY, outputPath);
		properties.setProperty(PROFILE_CREDENTIAL_KEY, profileCredential);
		properties.setProperty(REGION_NAME_KEY, regionName);
		properties.setProperty(INTERVAL_PERIOD_KEY, Integer.toString(intervalPeriod));
		properties.setProperty(SERVICE_NAME_KEY, serviceName);
		properties.setProperty(START_TIME_KEY, startTime);
		return properties;
	}
	
	public static class ConsoleOptionsPropertiesBuilder {
		// optional parameters
		private String endTime;
		private String instanceId;		
		private String metricNames;		
		private String outputPath;		
		private String profileCredential;
		private String regionName;
		private String startTime;
		private int intervalPeriod;
		
		public ConsoleOptionsPropertiesBuilder endTime(String endTime) {
			this.endTime = endTime;
			return this;
		}
		
		public ConsoleOptionsPropertiesBuilder instanceId(String instanceId) {
			this.instanceId = instanceId;
			return this;
		}
		
		public ConsoleOptionsPropertiesBuilder metricNames(String metricNames) {
			this.metricNames = metricNames;
			return this;
		}
		
		public ConsoleOptionsPropertiesBuilder outputPath(String outputPath) {
			this.outputPath = outputPath;
			return this;
		}
		
		public ConsoleOptionsPropertiesBuilder intervalPeriod(int intervalPeriod) {
			this.intervalPeriod = intervalPeriod;
			return this;
		}
		
		public ConsoleOptionsPropertiesBuilder profileCredential(String profileCredential) {
			this.profileCredential = profileCredential;
			return this;
		}
		
		public ConsoleOptionsPropertiesBuilder regionName(String regionName) {
			this.regionName = regionName;
			return this;
		}
		
		public ConsoleOptionsPropertiesBuilder startTime(String startTime) {
			this.startTime = startTime;
			return this;
		}
		
		public ConsoleOptionsPropertiesMapper build() {
			return new ConsoleOptionsPropertiesMapper(this);
		}
	}

}
