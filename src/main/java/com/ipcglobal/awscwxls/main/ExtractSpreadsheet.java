package com.ipcglobal.awscwxls.main;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
//import java.util.logging.Level;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

import com.ipcglobal.awscwxls.cw.DimensionMetric;
import com.ipcglobal.awscwxls.cw.ExtractMetrics;
import com.ipcglobal.awscwxls.util.LogTool;
import com.ipcglobal.awscwxls.xls.MetricSpreadsheet;
import xyz.joseyamut.awscwxls.console.ConsoleOptions;

/**
 * ExtractSpreadsheet is the main entry point, it is passed a properties file to determine the execution path.
 */
public class ExtractSpreadsheet {
	
	/** The log. */
	private static Log log = LogFactory.getLog(ExtractSpreadsheet.class);

	/**
	 * The main method.
	 * A single argument is required - the path/name.ext of the properties file.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {
		customLogPropertiesFile("log4j.properties");
		ConsoleOptions consoleOptions = new ConsoleOptions(args);		
		ExtractMetrics extractMetrics = new ExtractMetrics(consoleOptions.getProperties());
		MetricSpreadsheet metricSpreadsheet = new MetricSpreadsheet(consoleOptions.getProperties());
		boolean isSpreadsheetCreated = false;
		
		for( int offset=0 ; ; offset++ ) {
			String namespace = consoleOptions.getProperties().getProperty("namespace."+offset);
			if( namespace == null || "".equals(namespace)) break;
			
			ExtractItem extractItem = new ExtractItem(consoleOptions.getProperties(), offset);
			List<DimensionMetric> dimensionMetrics = extractMetrics.extractMetricsByDimension(extractItem);
			metricSpreadsheet.createSheet( dimensionMetrics, extractItem );
			isSpreadsheetCreated = true;
		}

		if( isSpreadsheetCreated ) metricSpreadsheet.writeWorkbook( );
		else log.error("Invalid properties - must contain at least one resource, i.e. one EC2 instance, one ELB name, etc.");
	}
	
	private static void customLogPropertiesFile(String propertiesFileName) {
		Properties properties = new Properties();
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(propertiesFileName);
			properties.load(fileInputStream);
			fileInputStream.close();
			PropertyConfigurator.configure(propertiesFileName);
		} catch (IOException e) {			
			System.out.println("Cannot find " + propertiesFileName + " file. Using defaults.");
			LogTool.initConsole();
		}
	}

}
