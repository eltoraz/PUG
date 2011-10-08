package eltoraz.pug.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import eltoraz.pug.*;
import java.util.Vector;;

public class MySQLconnect {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public void connectToDataBase() throws Exception {
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			connect = DriverManager
					.getConnection("jdbc:mysql://localhost/pug?"
							+ "user=sqluser&password=sqluserpw");

			
			
		} catch (Exception e) {
			throw e;
		} 

	}
	
	
	public Vector<Game> getAllGames() {
		try{
			Vector<Game> allgames;
			Game tempgame;
		
			statement = connect.createStatement();
			
			//issue query to get all the games in database
			resultSet = statement.executeQuery("Select g.sport, l.lat, l.longi, l.name, u.first_name, u.last_name from games g, locations l, users u where g.location = l.id and g.creator = u.id;");
			
			//put all the info into a vector of games
			//tempgame
			
			//STUFF from the tutorial-------->>>
			/*//resultSet = statement.executeQuery( " insert INTO FEEDBACK>COMMENTS VALUES(default,sharon,?,?,?,?,?)" );
			writeResultSet(resultSet);

			// PreparedStatements can use variables and are more efficient
			preparedStatement = connect
					.prepareStatement("insert into  FEEDBACK.COMMENTS values (default, ?, ?, ?, ? , ?, ?)");
			// "myuser, webpage, datum, summery, COMMENTS from FEEDBACK.COMMENTS");
			// Parameters start with 1
			preparedStatement.setString(1, "Test");
			preparedStatement.setString(2, "TestEmail");
			preparedStatement.setString(3, "TestWebpage");
			preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
			preparedStatement.setString(5, "TestSummary");
			preparedStatement.setString(6, "TestComment");
			preparedStatement.executeUpdate();

			preparedStatement = connect
					.prepareStatement("SELECT myuser, webpage, datum, summery, COMMENTS from FEEDBACK.COMMENTS");
			resultSet = preparedStatement.executeQuery();
			//writeResultSet(resultSet);

			// Remove again the insert comment
			preparedStatement = connect
			.prepareStatement("delete from FEEDBACK.COMMENTS where myuser= ? ; ");
			preparedStatement.setString(1, "Test");
			preparedStatement.executeUpdate();
			
			resultSet = statement
			.executeQuery("select * from FEEDBACK.COMMENTS");
			//writeMetaData(resultSet); */		
			
			return allgames;
		}
		catch(Exception e) {
			throw e;
		} finally{
			
		}
			
		
	}

	private void writeMetaData(ResultSet resultSet) throws SQLException {
		// 	Now get some metadata from the database
		// Result set get the result of the SQL query
		
		System.out.println("The columns in the table are: ");
		
		System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
		for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
			System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
		}
	}

	private void writeResultSet(ResultSet resultSet) throws SQLException {
		// ResultSet is initially before the first data set
		while (resultSet.next()) {
			// It is possible to get the columns via name
			// also possible to get the columns via the column number
			// which starts at 1
			// e.g. resultSet.getSTring(2);
			String id = resultSet.getString(1);
			String first = resultSet.getString(2);
			String last = resultSet.getString(3);
			System.out.println("User ID: " + id);
			System.out.println("First Name " + first);
			System.out.println("Last Name: " + last);
			
		}
	}

	// You need to close the resultSet
	public void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}

}