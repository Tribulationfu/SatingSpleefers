package me.Kruithne.SatingSpleefers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;


public class DatabaseConnection {

	 private Connection connection = null;
	 private String url;
	 private String user;
	 private String password;
     
     SatingSpleefers rPlugin = null;
     
     public DatabaseConnection(SatingSpleefers plugin, String url, String user, String password)
     {
    	 this.rPlugin = plugin;
    	 
    	 this.url = url;
    	 this.user = user;
    	 this.password = password;
     }
     
     public void establishConnection()
     {
    	try
    	{
    		this.connection = DriverManager.getConnection(this.url, this.user, this.password);
    	}
    	catch (SQLException e)
    	{
    		this.rPlugin.outputToConsole(String.format(Constants.SQLError, e.getMessage()), Level.SEVERE);
		}
     }
     
     public void closeConnection()
     {
    	try
    	{
    		this.connection.close();
		}
    	catch (SQLException e)
    	{
    		this.rPlugin.outputToConsole(String.format(Constants.SQLError, e.getMessage()), Level.SEVERE);
		}
     }
     
     public ResultSet getQuery(String query)
     {
    	 try
    	 {
    		 if (this.connection.isClosed())
    		 {
    			 this.establishConnection();
    		 }
    	 }
    	 catch (SQLException e1)
    	 {
    		 this.rPlugin.outputToConsole(String.format(Constants.SQLError, e1.getMessage()), Level.SEVERE);
    	 }
    	 
    	 ResultSet result = null;
    	 
    	 try
    	 {
    		 Statement newStatement = connection.createStatement();
    		 result = newStatement.executeQuery(query);
    	 }
    	 catch (SQLException e)
    	 {
    		 this.rPlugin.outputToConsole(String.format(Constants.SQLError, e.getMessage()), Level.SEVERE);
    	 }
    	 
    	 return result;
     }
     
     public boolean query(String query)
     {
    	 try
    	 {
    		 if (this.connection.isClosed())
    		 {
    			 this.establishConnection();
    		 }
    	 }
    	 catch (SQLException e1)
    	 {
    		 this.rPlugin.outputToConsole(String.format(Constants.SQLError, e1.getMessage()), Level.SEVERE);
    	 }
    	 
    	 try
    	 {
    		 Statement statement = this.connection.createStatement();
    		 
    		 statement.executeUpdate(query);
    		 return true;
    		 
    	 }
    	 catch (SQLException e)
    	 {
    		 this.rPlugin.outputToConsole(String.format(Constants.SQLError, e.getMessage()), Level.SEVERE);
    		 return false;
    	 }
     }
}
