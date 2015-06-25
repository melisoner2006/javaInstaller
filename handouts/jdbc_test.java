/**
 * @(#)equip.java
 *
 *
 * @author 
 * @version 1.00 2008/11/11
 */
 
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

public class jdbc_test extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<html>");
		out.println("<head>");
		out.println("<title>JDBC Test</title>");
		out.println("</head>");
		out.println("<body>");
        out.println("<a href=\"../index.html\">Tomcat</a>");
        out.println(" ... <a href=\"index.html\">My Home Page</a>");
        out.println("<h3>JDBC Test</h3>");
        out.println("<br />");
        
   		try {
    		Statement stmt;
    		ResultSet rs;
      		//
      		// Register the JDBC driver for MySQL ...
      		//
      		Class.forName("com.mysql.jdbc.Driver");

      		//
      		// Define URL of database server for database on the localhost
      		// with the default port number 3306 and use the "test" database
      		//
      		String url = "jdbc:mysql://localhost:3306/test";

      		//
      		// Get a connection to the database for an MySQL database account/user ...
      		//
      		Connection con = DriverManager.getConnection(url, "mccl", "mccl");
      		//con.setAutoCommit(false);
	
      		//
      		// Display URL and connection information ...
      		//
      		out.println("URL: " + url + "<br />");
      		out.println("Connection: " + con + "<br />");

      		//
      		// Get a Statement object ...
      		// 
      		stmt = con.createStatement();

      		//
      		// As a precaution, delete myTable if it already exists ...
      		//
      		try {
		       stmt.executeUpdate("DROP TABLE myTable");
	    	} catch(Exception e) {
		       // out.println(e);	// Print System Exception Error ...
		       out.println("No existing table to delete");
	    	} //end catch

			//
			// Create a table in the database named myTable ...
			// 
			stmt.executeUpdate( "CREATE TABLE myTable(test_id int, test_val char(15) not null)");

			// 
			// Insert some values into the table ...
			//
			stmt.executeUpdate("INSERT INTO myTable(test_id, test_val) VALUES(1,'One')");
			stmt.executeUpdate("INSERT INTO myTable(test_id, test_val) VALUES(2,'Two')");
			stmt.executeUpdate("INSERT INTO myTable(test_id, test_val) VALUES(3,'Three')");
			stmt.executeUpdate("INSERT INTO myTable(test_id, test_val) VALUES(4,'Four')");
			stmt.executeUpdate("INSERT INTO myTable(test_id, test_val) VALUES(5,'Five')");

			//
			// Get another statement object initialized ...
			// 
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			// 
			// Query the database, storing the result in an object of type ResultSet
			// 
			rs = stmt.executeQuery("SELECT * from myTable ORDER BY test_id");

			// 
			// Use the methods of class ResultSet in a loop to display all the data in the database.
			// 
			out.println("Display all results: <br />");
			while(rs.next()) {
				int theInt= rs.getInt("test_id");
				String str = rs.getString("test_val");
				out.println("\ttest_id= " + theInt + "\tstr = " + str + "<br />");
			} //end while loop

			// 
			// Display the data in a specific row using the rs.absolute method.
			// 
			out.println("Display row number 2:" + "<br />");
			if( rs.absolute(2) ) {
		        int theInt= rs.getInt("test_id");
		        String str = rs.getString("test_val");
		        out.println("\ttest_id= " + theInt + "\tstr = " + str + "<br />");
			} //end if

			//
			// HTML Table ...
			//
			int rowCount = 0;

			out.println("<TABLE BORDER=1>");
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			// table header
			out.println("<TR>");
			for (int i = 0; i < columnCount; i++) {
				out.println("<TH>" + rsmd.getColumnLabel(i + 1) + "</TH>");
   			}
			out.println("</TR>");
			// the data
			while (rs.next()) {
			  rowCount++;
			  out.println("<TR>");
			  for (int i = 0; i < columnCount; i++) {
			    out.println("<TD>" + rs.getString(i + 1) + "</TD>");
			  }
			  out.println("</TR>");
			}
			out.println("</TABLE>");

			// 
        	// Delete the table ...
        	// 
        	//stmt.executeUpdate("DROP TABLE myTable");

        	// 
        	// Close the connection to the database ...
        	// 
        	con.close();


    	} catch( Exception e ) {
    	   out.println(e);	// Print System Exception Error ...
    	   // out.println("No existing table to delete");
    	}	//end catch

		out.println("</body>");
		out.println("</html>");
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        doGet(request, response);    
    }
}