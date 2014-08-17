package database;


import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;




public class RcmDBDAO {

	/**RCM database class
	 * @author Kavan,Preeth
	 */
	
	static Object[][] databaseResults = null;

	
	static Object[] columns = {"RcmID", "opStatus", "currentWeight", "moneyRemaining"};

	static DefaultTableModel dTableModel = new DefaultTableModel(databaseResults, columns) {
		private static final long serialVersionUID = 1L;

	public Class getColumnClass(int column) {
			Class returnValue;

			if((column>0) && (column < getColumnCount())) {
				returnValue = getValueAt(0, column).getClass();
			} else {
				returnValue = Object.class;
			}
			return returnValue;
		}
	};

	
	static JTable table = new JTable(dTableModel);	

	/** Method to establish database connection
	 * @return Connection
	 * */
	protected static Connection getConnection(){
		// JDBC driver name and database URL
		String driverName = "com.mysql.jdbc.Driver";
	    String connectionURL = "jdbc:mysql://localhost/ecorecyclesystem";
	    //  Database credentials
	    String userName = "root";
	    String password = "";
   	    try {
	        Class.forName(driverName);
	    }
	    catch( Exception e ) {
	        System.err.println("Failed to load driver: " + driverName);
	        return null;
	    } 
	    
	    Connection conn = null;
	    try {
	    	conn = DriverManager.getConnection(connectionURL, userName, password);
	    }
	    catch (SQLException e){
	       System.err.println( "Error connecting to database using URL: " + connectionURL + " & Username: " + userName);
	       e.printStackTrace();
	    }
	    
	    return conn;
	}
	
	
	/** Method to Close database connection
	 * @param connection
	 * */
	protected void closeConnection(Connection conn) {
		
		try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	/** Method to update sql query
	 * @param String
	 * */
	protected static void executeUpdateStatement(String sql) {
		System.out.println("SQL " + sql);
		Connection conn = null;
		
		try {
			conn = getConnection();
			Statement statement = conn.createStatement();
		    int rowsCount = statement.executeUpdate(sql);
		    System.out.println(rowsCount + " rows affected.");
		    statement.close();
	    }
	    catch (Exception e) {
	    	//JOptionPane.showMessageDialog(null,"RCM ID already exists!");
	      e.printStackTrace();
	    } finally {
	    	try {
	    		conn.close();
	    	} catch (SQLException e){
	    		System.err.println("Failed to close connection.");	    		
	    	}
	    }
	}
	

	/** Method to execute sql query
	 * @param String
	 * */
	protected static ResultSet executeQueryStatement(String sql) {
		System.out.println("SQL " + sql);
		Connection conn = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			Statement statement = conn.createStatement();
		    rs = statement.executeQuery(sql);
		    statement.close();
		    //return rs;
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    } 
		finally {
	    	try {
	    		conn.close();
	    	} catch (SQLException e){
	    		System.err.println("Failed to close connection.");	    		
	    	}
	    }
		return rs;
	}
	
	

	/** Method to insert a record into RCM
	 * @param groupID, capacity, location, rcmAmountMax
	 * */
	public  void insertRcm(int rcmID, int capacity, String location,double rcmAmountMax, ArrayList<String> itemType) {
		//Timestamp currentDate= (Timestamp) new Date();
		Connection conn = null;
		ResultSet rs = null;
		Statement stmt = null;
		
		try {
			String sql1 = "INSERT INTO rcm (RcmID,Capacity,Location,OpStatus,MaxAmount)"
					+"VALUES("+rcmID+","+capacity+",'"+location+"','Operational',"+rcmAmountMax+")";
			conn = getConnection();			
			stmt = conn.createStatement();			
			//int rowsCount = stmt.executeUpdate(sql1);
			executeUpdateStatement(sql1);
			String sql2 = "INSERT INTO transaction (RcmID,CurrentWeight,NumItems,MoneyRemaining)"
					+"VALUES("+rcmID+","+capacity+",0,"+rcmAmountMax+")";
	
			executeUpdateStatement(sql2);
			//	Take an array list of items that the rcm can accept - from the selected Check boxes!!
			for(int i=0; i<itemType.size();i++) {
				String sql3 = "INSERT INTO rcm_item (RcmID,ItemType) VALUES ("+rcmID+",'"+itemType.get(i)+"')";
				executeUpdateStatement(sql3);
			} 
		}catch (SQLException e) {
			JOptionPane.showMessageDialog(null,"RCM ID already exists!");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

	/** Method to insert a record into RCM_ITEM
	 * @param groupID, itemType
	 * */
	public static  void insertRcmItem(int rcmID, String itemType) {
		
		Connection conn = null;
		ResultSet rs = null;
		Statement stmt = null;
		
		String sql3 = "INSERT INTO rcm_item (RcmID,ItemType) VALUES ("+rcmID+",'"+itemType+"')";
		executeUpdateStatement(sql3);
	}
	

	/** Method to insert a record into RCM_ITEM
	 * @param groupID, itemType
	 * */
	public static  void deleteRcmItem(int rcmID, String itemType) {

		Connection conn = null;
		ResultSet rs = null;
		Statement stmt = null;
	
		String sql = "DELETE FROM rcm_item WHERE RcmID='"+rcmID+"' AND ItemType='"+itemType+"'";
		executeUpdateStatement(sql);
	}
	
	/** Method to activate RCM
	 * @param rcmID
	 */
	public static void activateRcm(int rcmID) {
		String sql = "UPDATE rcm SET OpStatus='Operational' WHERE RcmID="+rcmID;
		executeUpdateStatement(sql);		
	}
	
	/** Method to deactivate RCM
	 * @param rcmID
	 */
	public static void deactivateRcm(int rcmID) {
		String sql = "UPDATE rcm SET OpStatus='Non-Operational' WHERE RcmID="+rcmID;
		executeUpdateStatement(sql);		
	}
	
	/** Method to check status of RCM
	 * @param rcmID
	 */
	
	public static JTable checkRcmStatus(int id)
			throws SQLException {
		// clear old data
		dTableModel.setRowCount(0);
		
		
		Connection conn = null;
		Statement stmt = null;
		    
		// converting int into string
		String idString = Integer.toString(id);
		    
		String sql1= "SELECT R.RcmID,OpStatus,CurrentWeight,MoneyRemaining from rcm R,transaction T WHERE R.RcmID=T.RcmID="+ idString + " "; 
		
		try {
			
			conn = getConnection();			
			stmt = conn.createStatement();			
			ResultSet rs = stmt.executeQuery(sql1);
		        
			/*************************/
			Object[] tempRow;
		  
			while (rs.next()) {
				/**************************************************************************************/
		        	
				//static Object[] columns = {"RcmID", "opStatus", "currentWeight", "moneyRemaining"};
				tempRow = new Object[]{rs.getInt(1), rs.getString(2), rs.getDouble(3),  rs.getDouble(4)}; 
				dTableModel.addRow(tempRow);
				//System.out.println(table);
				
				/**************************************************************************************/
				           
			}
		} catch (SQLException e ) {
			//  JDBCTutorialUtilities.printSQLException(e);
		    	
		} finally {
			if (stmt != null) { stmt.close(); }
		}
		    
		table.setFont(new Font("Serif", Font.PLAIN, 16));
		// table.setRowHeight(table.getRowHeight()+12);
		//table.setAutoCreateRowSorter(true);
		    
		   
		// returns JTable to be displayed
		return table;
	}
	

	/** Method to get Rcm ID list
	 * @return ArrayList
	 * */
	public static ArrayList<Integer> getRcmID() {
		Connection conn = null;
		 ResultSet rs = null;
		 Statement stmt = null;
		ArrayList<Integer> rcmIDList = new ArrayList<Integer>();
		String sql= "SELECT RcmID from rcm";
		try {
			conn = getConnection();			
			stmt = conn.createStatement();			
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				rcmIDList.add(rs.getInt(1));	
				System.out.println(rs.getInt(1));
				}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rcmIDList;
	}
	
	
	/** Method to retrieve recycle items for Rcm ID 
	 * @param rcmID: int
	 * @return ArrayList<String>
	 * */

	public static ArrayList<String> retrieveRcmRecycleItem(int rcmID) {
		Connection conn = null;
		ResultSet rs = null;
		Statement stmt = null;
		ArrayList<String> rcmItemList = new ArrayList<String>();
		String sql = "SELECT ItemType,Price,Weight FROM rcm_item, recycleitem WHERE Type = Itemtype AND RcmID="+rcmID;
		try {
			conn = getConnection();			
			stmt = conn.createStatement();			
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				rcmItemList.add(rs.getString(1));	
				rcmItemList.add(rs.getString(2));	
				rcmItemList.add(rs.getString(3));	
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rcmItemList;
	}

	/** Method to retrieve  Rcm stations
	 * @return ArrayList<String>
	 * */
	public static ArrayList<String> retrieveRcmStation() {
		Connection conn = null;
		 ResultSet rs = null;
		 Statement stmt = null;
		ArrayList<String> rcmStnList = new ArrayList<String>();
		String sql= "SELECT RcmID,Location,OpStatus from rcm";
		try {
			conn = getConnection();			
			stmt = conn.createStatement();			
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				rcmStnList.add(rs.getString(1));	
				rcmStnList.add(rs.getString(2));
				rcmStnList.add(rs.getString(3));
				//System.out.println(rs.getString(1));
				//System.out.println(rs.getString(2));
				//System.out.println(rs.getString(3));
				}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rcmStnList;
		
	}
	
	/** Method to retrieve rcms and clear them 
	 * @param rcmID: int
	 * */
	public static void retrieveRcmForClear(int rcmID) {
		Connection conn = null;
		ResultSet rs = null;
		Statement stmt = null;
		double weight = 0, amount = 0;
		
		try {
			
			String sql1= "SELECT Capacity,MaxAmount from rcm where rcmID ="+rcmID;
			conn = getConnection();			
			stmt = conn.createStatement();			
			rs = stmt.executeQuery(sql1);
			while(rs.next()) {
				 weight= rs.getDouble(1);	
				 amount= rs.getDouble(2);
			}
			Date timeLastEmptied = new Date();
			String sql2 = "UPDATE transaction SET CurrentWeight ="+weight+",MoneyRemaining="+amount+
							" WHERE RcmID = "+rcmID;
			executeUpdateStatement(sql2);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/** Method to retrieve Rcm capacity 
	 * @param rcmID: int
	 * @return ArrayList<String>
	 * */
	public static ArrayList<String> retrieveRcmCapacityAmount(int rcmID) {
		Connection conn = null;
		ResultSet rs = null;
		Statement stmt = null;
		ArrayList<String> rcmStnList = new ArrayList<String>();
			
		try {
			
			String sql1= "SELECT Capacity,MaxAmount from rcm where rcmID ="+rcmID;
			conn = getConnection();			
			stmt = conn.createStatement();			
			rs = stmt.executeQuery(sql1);
			while(rs.next()) {
				 rcmStnList.add(rs.getString(1));	
				 rcmStnList.add(rs.getString(2));
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rcmStnList;
		
	}
	
	
	/** Method to delete rcm
	 * @param rcmID: int
	 * */
	public static void deleteRcm(int rcmID) {
		String sql1 = "DELETE from rcm WHERE RcmID ="+rcmID;
		String sql2 = "DELETE from rcm_item WHERE RcmID ="+rcmID;
		String sql3 = "DELETE from transaction WHERE RcmID ="+rcmID;
		executeUpdateStatement(sql1);
		executeUpdateStatement(sql2);
		executeUpdateStatement(sql3);
	}

	/**Method updates the transaction table on every transaction */
	public static void updateTransaction(int machineID, double weight,double amount,int numItems,int numEmptied) {
		String sql = "UPDATE transaction SET TotalWeight="+weight+",TotalAmountSpent="+amount+",NumItems ="
							+numItems+",NumEmptied ="+numEmptied+" WHERE RcmID='"+machineID+"'";
		executeUpdateStatement(sql);
	 }
	
	
	/** Method to retrieve transacation data 
	 * @param rcmID: int
	 * @return ArrayList<String>
	 * */
	public static ArrayList<String> RetrieveTransaction(int rcmID) {
		Connection conn = null;
		ResultSet rs = null;
		Statement stmt = null;
		ArrayList <String> transactionInfo = new ArrayList<String>(); 
		try {
			
			String sql1= "SELECT TotalWeight,NumItems,TotalAmountSpent,NumEmptied from transaction where rcmID ="+rcmID;
			conn = getConnection();			
			stmt = conn.createStatement();			
			rs = stmt.executeQuery(sql1);
			while(rs.next()) {
				transactionInfo.add(rs.getString(1));	
				transactionInfo.add(rs.getString(2));
				transactionInfo.add(rs.getString(3));
				transactionInfo.add(rs.getString(4));
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return transactionInfo;
		
	}
	

}




