# Generic AWS CloudWatch to Spreadsheet Exporter

*Forked from Pete Zybrick's work @ [https://github.com/petezybrick/awscwxls](https://github.com/petezybrick/awscwxls)*

CloudWatch doesn't provide an Export utility - this does.  awscwxls creates spreadsheets based on generic sets of Namespace/Dimension/Metric/Statistic specifications.
As long as AWS continues to follow the Namespace/Dimension/Metric/Statistic pattern, awscwxls should work for existing and future Namespaces (Services). Each set of specifications is stored in a properties file, so each properties file can be configured for a specific set of AWS Services and resources.  Take a look at run/properties/template.properties for a complete example.


## To run:

- Pre-requisites
	* Java 1.7
	* AWS account, .aws/credentials file containing your keys
	* AWS account must have CloudWatch IAM permission	 
- Copy the run/ directory to the local file system
- Review properties/first.properties.  This is a very simple properties file that will extract EC2 statistics for a single instance
- Update properties/first.properties based on the TODO comments in the file
- Run
	* Linux: 	./runcwxls properties/first.properties
	* Windows:	runcwxls properties/first.properties
- Review the spreadsheet
- Advanced
	* Review properties/template.properties.  This is an example of collecting stats from multiple Namespace/Dimension/Metric/Statistic combinations
- NEW: Command-line options
	* It is possible to pass all properties through command-line options instead of the properties file in Java-style -D<property=value> format
	* When not using a *properties* file, only *startTime*, *endTime*, *dimensionValues.0*, and *periodMinutes.0* must be specified
	* Default *namespace* is AWS/EC2
	* Default *dimensionName* is InstanceId
	* e.g. java -jar awscwxls.jar -DstartTime="2017-12-01 00:00:00" -DendTime="2017-12-03 23:59:59" -DperiodMinutes.0=5 -DdimensionValues.0="i-05c44961d20f78ab7" -DxlsPathNamePrefix="/home/jose/cw-metrics" -DmetricStatisticNames.0="CPUUtilization|Average CPUUtilization|Minimum CPUUtilization|Maximum NetworkIn|Sum NetworkOut|Sum" -DcredentialsProfileName=default -DcredentialsRegion=ap-southeast-1
	* Pass *--help* option to get usage and a list of available options
	* Multiple namespaces are only supported using *properties* file (e.g. @ properties/template.properties)

 
## Java Project Overview
 * Maven project in Eclipse, but should easily port to other IDE's
 * Uses Apache POI to create the spreadsheets.  Take a look at BaseXls.java, encapsulates most of the POI complexity, MetricSpreadsheet.java creates the XLS.
 * Entry point: ExtractSpreadsheet.main()
 * ExtractMetrics.java does most of the heavy lifting - connecting to CW and getting the metrics/statistics
 
## Sample properties file in *sample-properties* branch

 
*Thanks to Alois Reitbauer, the examples on his [blog](http://apmblog.compuware.com/2010/04/22/week-14-building-your-own-amazon-cloudwatch-monitor-in-5-steps/) accelerated my understanding of the CW API.* 
 
*AWS Namespace list can be found [at this page](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/viewing_metrics_with_cloudwatch.html).*

### Caveats
* AWS will limit API calls when there are too many data points. 

