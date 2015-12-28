package com.hc.test.framework.selenium;

import java.io.File;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ObjectLocator {
	
	private String objRepoName;
	
	static {
		System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "DEBUG");
	}

	public static Logger LOGGER = LoggerFactory
			.getLogger(ObjectLocator.class);
	
	ObjectLocator(String objRepoName)
	{
		this.objRepoName = objRepoName;
	}
	
	public Configuration getObjectsFromRepo()
	{
		Parameters params = new Parameters();
		Configuration objRepo = null;
		
		File propertiesFile = new File(objRepoName+".properties");

		FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
		    new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
		    .configure(params.fileBased()
		        .setFile(propertiesFile));
		try
		{
			objRepo = builder.getConfiguration();
		    
		}
		catch(ConfigurationException cex)
		{
			LOGGER.error(cex.getMessage());
		}
		
		return objRepo;
	}

}
