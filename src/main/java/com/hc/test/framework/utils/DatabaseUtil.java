package com.hc.test.framework.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.commons.configuration2.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class DatabaseUtil {

	private HashMap<String, HashMap<String, String>> mapResult = new HashMap<String, HashMap<String, String>>();
	
	private Connection conn;
	private Configuration prop;
	private String schemaName;
	
	static {
		System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "DEBUG");
	}

	public static Logger LOGGER = LoggerFactory
			.getLogger(DatabaseUtil.class);
	
	public DatabaseUtil(String schemaName)
	{
		this.schemaName = schemaName;
	}
	
	public void createConnection()
	{
		prop = new ReadConfiguration("config").getConfigFile();
		try {
			Class.forName(prop.getString("jdbc.driver"));
			conn = DriverManager.getConnection(
					prop.getString("db.url"+"/"+schemaName),
					prop.getString("db.username"),prop.getString("db.passowrd"));
		} catch (ClassNotFoundException e) {
			LOGGER.error(e.getMessage());
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		}
	}
	
	public HashMap<String, HashMap<String, String>> getQueryResultAsMapForSingleTable(String query)
	{
		mapResult.clear();
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			HashMap<String, String> results = new HashMap<String, String>();
			int noOfCols = rs.getMetaData().getColumnCount();
			
			String tableName = rs.getMetaData().getTableName(1);
			while(rs.next())
			{
				for(int i = 1;i<=noOfCols;i++)
				{
					results.put(rs.getMetaData().getColumnName(i)+i, rs.getString(i));
				}
			}
			mapResult.put(tableName, results);
			
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		}
		
		
		return mapResult;
	}
	
	public void closeConnection()
	{
		try {
			conn.close();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		}
	}
	

}
