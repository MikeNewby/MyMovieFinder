package myMovieFinder;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Connect {
	   static final String databasePrefix ="mymoviefinder"; // TODO: need to change this depending on DB location
	   static final String netID ="user1"; // Please enter your netId
	   static final String hostName ="localhost"; //leave as is
	   static final String databaseURL ="jdbc:mysql://"+hostName+"/"+databasePrefix+"?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
	   static final String password="password"; // please enter your own password
	    
	   private static Connection connection = null;
       private static Statement statement = null;
       private static ResultSet resultSet = null;
       
	   public static Connection getConnection(){
		   //no need to reconnect if we're already established
		  if(connection != null)
			   return connection;
		  
	      try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            System.out.println("databaseURL: "+ databaseURL);
	            // Set the connection in this class.
	            connection = DriverManager.getConnection(databaseURL, netID, password);
	            System.out.println("Successfully connected to the database");
	         }
	        catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	        catch (SQLException e) {
	            e.printStackTrace();
	        }
			return connection;
	    } // end of getConnection
	    
	   //run simple query
	   public static void runQuery(String sqlQuery) {  
		  try {
	    			statement = connection.createStatement();
	    			resultSet = statement.executeQuery(sqlQuery);

	    			ResultSetMetaData metaData = resultSet.getMetaData();
	    			int columns = metaData.getColumnCount();

	    			for (int i=1; i<= columns; i++) {
	    				System.out.print(metaData.getColumnName(i)+"\t");
	    			}
	    			System.out.println();
	    			while (resultSet.next()) {
	    				for (int i=1; i<= columns; i++) {
	    					System.out.print(resultSet.getObject(i)+"\t\t");
	    				}
	    				System.out.println();
	    			}
	    		} catch (SQLException e) {
	    			e.printStackTrace();
	    		}	 
	    }

	    // executes an update query, returns row count of executed query
	    public static int update(String query) {
			try {
				statement = connection.createStatement();
				return statement.executeUpdate(query);
			}
			catch (SQLException e) {
				e.printStackTrace();
				return -1;
			}
		}

	    //check user ID and p/w against users database.
	    public static int checkUser(String sqlQuery) {
	    		System.out.println("Check user");
	    		if (connection == null) {
	    			System.out.println("connection is null!!!");
	    		}
	    		try {
	    			statement = connection.createStatement();
	    			resultSet = statement.executeQuery(sqlQuery);
	    			int cnt=0;
	    			while(resultSet.next()) {
	    				cnt++;
	    			}
	    		
	    			if(cnt == 1) {
	    				resultSet.first();
					int uid = resultSet.getInt(1);
					if(uid > 0) {
						return uid;
					}
	    			}
	    			//All other cases are a failure, 
	    			return -1;	
	    		} catch (SQLException e) {
	    			e.printStackTrace();
	    			return -1;
	    		} 
	    } 
}
