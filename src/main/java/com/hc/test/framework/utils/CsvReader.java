//package com.hc.test.framework.utils;
//
//import java.io.FileWriter;
//import java.io.IOException;
//import java.text.MessageFormat;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.List;
//
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import au.com.bytecode.opencsv.CSVWriter;
//import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy; 
//
//public class CsvReader {
//
//	
//	private List<Defaulter> defaulters;
//	
//	public List<Defaulter> getDefaulters() {
//		return defaulters;
//	}
//
//	public void setDefaulters(List<Defaulter> defaulters) {
//		this.defaulters = defaulters;
//	}
//
//	static {
//		System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "DEBUG");
//	}
//	
//	public static Logger LOGGER = LoggerFactory.getLogger(CsvReader.class);
//	
//	public void writeBeanToCsv(String csvPath)
//	{
//		MessageFormat csvPathFormat =  new MessageFormat(csvPath);
//		Calendar cal = Calendar.getInstance();
//		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd'T'HH:MM:SSZ");
//		Object [] csvFileSuffix = {sdf.format(cal.getTime())};
//		String finalCsvFileName = csvPathFormat.format(csvFileSuffix);
//		try {
//			CSVWriter writer = new CSVWriter(new FileWriter(finalCsvFileName, true));
//			String[] columns = new String[] { "User Id", "User Name", "E-Mail",
//					"Commit SHA", "Commit Message", "Repo Name", "Branch Name", "Error" };
//			writer.writeNext(columns);
//			
//			for(Defaulter defaulter:defaulters)
//			{
//				writer.writeNext(defaulter.toString().split(","));
//			}
//			
//			writer.flush();
//			writer.close();
//			LOGGER.info("Created report.");
//			
//		} catch (IOException e) {
//			LOGGER.error("IO Exception in CSV Writer.");
//			LOGGER.info(e.getMessage());
//		}
//	}
//	
//	private ColumnPositionMappingStrategy<Defaulter> setColumnMapping() {
//		ColumnPositionMappingStrategy<Defaulter> strategy = new ColumnPositionMappingStrategy<Defaulter>();
//		String[] columns = new String[] { "User Id", "User Name", "E-Mail",
//				"Commit SHA", "Commit Message", "Repo Name", "Branch Name" };
//		strategy.setColumnMapping(columns);
//		return strategy;
//	}
//}
