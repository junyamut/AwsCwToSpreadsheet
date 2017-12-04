package xyz.joseyamut.awscwxls.console;

public class ConsoleOptionsPropertiesModel {
	protected String
		endTime,
		dimensionName,		
		instanceId,
		metricNames,
		outputPath,
		profileCredential,
		regionName,
		serviceName,
		startTime;
	protected int intervalPeriod;

	public ConsoleOptionsPropertiesModel() { }

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getDimensionName() {
		return dimensionName;
	}

	public void setDimensionName(String dimensionName) {
		this.dimensionName = dimensionName;
	}	

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
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

	public int getIntervalPeriod() {
		return intervalPeriod;
	}

	public void setIntervalPeriod(int intervalPeriod) {
		this.intervalPeriod = intervalPeriod;
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

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	@Override
	public String toString() {
		return "ConsoleOptionsPropertiesModel [dimensionName=" + dimensionName + ", serviceName=" + serviceName
				+ ", instanceId=" + instanceId + ", metricNames=" + metricNames + ", outputPath=" + outputPath
				+ ", profileCredential=" + profileCredential + ", regionName=" + regionName + ", intervalPeriod="
				+ intervalPeriod + "]";
	}

}
