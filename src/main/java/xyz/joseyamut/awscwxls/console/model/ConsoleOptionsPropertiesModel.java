package xyz.joseyamut.awscwxls.console.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.*;

public class ConsoleOptionsPropertiesModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4853548498731426533L;
	@NotNull (message = "Date format must be in yyyy-MM-dd HH:mm[:ss]")
	@Past
	private Date endTime;	
	@NotNull (message = "Date format must be in yyyy-MM-dd HH:mm[:ss]")
	@Past
	private Date startTime;
	@NotNull (message = "is required")
	@Min(5)
	private int intervalPeriod;
	/*@NotNull (message = "is required")
	@NotBlank (message = "must be a valid value")*/
	private String dimensionName; // Set a default value
	@NotNull (message = "is required")
	@NotBlank (message = "must be a valid value")
	private String instanceId;
	/*@NotNull (message = "is required")
	@NotBlank (message = "specify valid values delimited with '|'")*/
	private String metricNames; // Set a default value
	/*@NotNull (message = "is required")
	@NotBlank (message = "specify a valid location to save exported data")*/
	private String outputPath; // Set a default value
	/*@NotNull (message = "is required")
	@NotBlank (message = "specify a valid value")*/
	private String profileCredential; // Set a default value
	/*@NotNull (message = "is required")
	@NotBlank (message = "specify a valid value")*/
	private String regionName; // Set a default value
	/*@NotNull (message = "is required")
	@NotBlank (message = "specify a valid value")*/
	private String serviceName;	// Set a default value

	public ConsoleOptionsPropertiesModel() { }
	
	private ConsoleOptionsPropertiesModel(ConsoleOptionsPropertiesBuilder builder) {
		this.dimensionName = builder.dimensionName;
		this.endTime = builder.endTime;		
		this.instanceId = builder.instanceId;
		this.intervalPeriod = builder.intervalPeriod;
		this.metricNames = builder.metricNames;
		this.outputPath = builder.outputPath;		
		this.profileCredential = builder.profileCredential;
		this.regionName = builder.regionName;
		this.serviceName = builder.serviceName;
		this.startTime = builder.startTime;
	}
	
	public String getDimensionName() {
		return dimensionName;
	}

	public void setDimensionName(String dimensionName) {
		this.dimensionName = dimensionName;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public int getIntervalPeriod() {
		return intervalPeriod;
	}

	public void setIntervalPeriod(int intervalPeriod) {
		this.intervalPeriod = intervalPeriod;
	}

	public String getMetricNames() {
		return metricNames;
	}

	public void setMetricNames(String metricNames) {
		this.metricNames = metricNames;
	}

	public String getOutputPath() {
		return outputPath;
	}

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

	public String getProfileCredential() {
		return profileCredential;
	}

	public void setProfileCredential(String profileCredential) {
		this.profileCredential = profileCredential;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Override
	public String toString() {
		return "ConsoleOptionsPropertiesModel [dimensionName=" + dimensionName + ", endTime=" + endTime
				+ ", instanceId=" + instanceId + ", intervalPeriod=" + intervalPeriod + ", metricNames=" + metricNames
				+ ", outputPath=" + outputPath + ", profileCredential=" + profileCredential + ", regionName="
				+ regionName + ", serviceName=" + serviceName + ", startTime=" + startTime + "]";
	}
	
	public static class ConsoleOptionsPropertiesBuilder {
		private Date endTime;
		private Date startTime;
		private int intervalPeriod;
		private String dimensionName;		
		private String instanceId;		
		private String metricNames;		
		private String outputPath;		
		private String profileCredential;
		private String regionName;
		private String serviceName;		
		
		public ConsoleOptionsPropertiesBuilder dimensionName(String dimensionName) {
			this.dimensionName = dimensionName;
			return this;
		}
		
		public ConsoleOptionsPropertiesBuilder endTime(Date endTime) {
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
		
		public ConsoleOptionsPropertiesBuilder serviceName(String serviceName) {
			this.serviceName = serviceName;
			return this;
		}
		
		public ConsoleOptionsPropertiesBuilder startTime(Date startTime) {
			this.startTime = startTime;
			return this;
		}
		
		public ConsoleOptionsPropertiesModel build() {
			return new ConsoleOptionsPropertiesModel(this);
		}
	}

}
