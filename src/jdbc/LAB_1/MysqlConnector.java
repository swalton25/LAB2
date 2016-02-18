package jdbc.LAB_1;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.*; 



public class MysqlConnector {

	public MysqlConnector() {
		try {
			// The newInstance() call is a work around for some
			// broken Java implementations

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			System.out.println("JDBC driver registered");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private Connection getConnection() {
		try {
			Connection conn = DriverManager
					.getConnection("jdbc:mysql://localhost/lab1?" + "user=root&password=greatsqldb");

			System.out.println("Got Mysql database connection");
			return conn;
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return null;
	}

	public boolean post(int id, String message) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		// 1) create a java calendar instance
		Calendar calendar = Calendar.getInstance();

		// 2) get a java.util.Date from the calendar instance.
		//		    this date will represent the current instant, or "now".
		java.util.Date now = calendar.getTime();

		// 3) a java current time (now) instance
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

		try {

			// Get the connection to the database
			con = getConnection();

			// Execute the query
			stmt = con.prepareStatement("insert into lab1 (id, message, time)"+ " values(?, ?, ? )");  
			stmt.setInt(1, id);

			rs = stmt.executeQuery();
			while (rs.next()) {

			}
			return true; 

		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} finally {
			// it is a good idea to release
			// resources in a finally{} block
			// in reverse-order of their creation
			// if they are no-longer needed

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore

				stmt = null;
			}
			if(con != null){
				try {
					con.close();
				} catch (SQLException sqlEx) {
				} // ignore

				con = null;
			}

		}
		return false;
	}




	public String get(int id) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		// 1) create a java calendar instance
		Calendar calendar = Calendar.getInstance();

		// 2) get a java.util.Date from the calendar instance.
		//		    this date will represent the current instant, or "now".
		java.util.Date now = calendar.getTime();

		// 3) a java current time (now) instance
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

		try {
			String message = " ";


			// Get the connection to the database
			con = getConnection();

			// Execute the query
			stmt = con.prepareStatement("select * from lab1 where id =? ");
			stmt.setInt(1, id);

			rs = stmt.executeQuery();
			while (rs.next()) {
				message= rs.getString(message);


			}
			return message; 

		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} finally {
			// it is a good idea to release
			// resources in a finally{} block
			// in reverse-order of their creation
			// if they are no-longer needed

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore

				stmt = null;
			}
			if(con != null){
				try {
					con.close();
				} catch (SQLException sqlEx) {
				} // ignore

				con = null;
			}

		}
		return null;
	}



	public Map<Integer, String> getAll() {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			Map<Integer, String>  message = new HashMap <Integer, String>();


			// Get the connection to the database
			con = getConnection();

			// Execute the query
			stmt = con.prepareStatement("select * from lab1");
			rs = stmt.executeQuery();
			while (rs.next()) {

				message.put(rs.getInt("id"), rs.getString("message"));

			}
			return message; 

		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} finally {
			// it is a good idea to release
			// resources in a finally{} block
			// in reverse-order of their creation
			// if they are no-longer needed

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore

				stmt = null;
			}
			if(con != null){
				try {
					con.close();
				} catch (SQLException sqlEx) {
				} // ignore

				con = null;
			}

		}
		return null;
	}
	  public int deleteUser(String ids){   
	        Statement stmt = null;   
	    	Connection con = null;
	        int rs=0;   
	        try {   
	            int id=Integer.parseInt(ids);   
	            stmt=con.createStatement();   
	            String sql="delete from users where id="+id;   
	            rs=stmt.executeUpdate(sql);   
	        } catch (SQLException e) {   
	            // TODO Auto-generated catch block   
	            rs=0;   
	            e.printStackTrace();   
	        }finally{   
	            try {   
	                stmt.close();   
	            } catch (SQLException e) {   
	                // TODO Auto-generated catch block   
	                e.printStackTrace();   
	            }   
	        }           
	        return rs;   
	    } 
	


	
	
	

}