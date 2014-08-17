package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

public class ItemDBDAO {
	/**Item database class
	 * @author Kavan,Preethi
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
		    System.out.println(rowsCount + " rows inserted.");
		    statement.close();
	    }
	    catch (Exception e) {
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
	 * @return ResultSet
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
	
	/** Method to insert a record into Item database
	 * @param type:String, price:double, weight:double
	 * */
	public static void addItem( String type,double price, double weight) {
		String sql = "INSERT INTO recycleitem (Type,Price,Weight)"
						+"VALUES('"+type+"',"+price+","+weight+")";
		executeUpdateStatement(sql);
	}
	
	/** Method to update a record into Item database
	 * @param type:String weight:double
	 * */
	public static void updateItemWeight(String itemName,double weight) {
		String sql = "UPDATE recycleitem SET Weight="+weight+" WHERE Type='"+itemName+"'";
		executeUpdateStatement(sql);
	}
	
	/** Method to insert a record into Item database
	 * @param type:String, price:double
	 * */
	public static void updateItemPrice(String itemName,double price) {
		String sql = "UPDATE recycleitem SET Price="+price+" WHERE Type='"+itemName+"'";
		executeUpdateStatement(sql);
	}

	/** Method to get items from database
	 * @return ArrayList<String>
	 * */
	public static ArrayList getItemType() {
		Connection conn = null;
		 ResultSet rs = null;
		 Statement stmt = null;
		ArrayList<String> itemNameList = new ArrayList<String>();
		String sql= "SELECT Type from recycleitem";
		try {
			conn = getConnection();			
			stmt = conn.createStatement();			
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				itemNameList.add(rs.getString(1));	
				System.out.println(rs.getString(1));
				}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return itemNameList;
	}
	
	
	
	/** Method to  get currentweight from databse
	 * @param String ID
	 * @return double
	 * */
	public static double getCurrentWeight(String machineID) {
		
		Connection conn = null;
		 ResultSet rs = null;
		 Statement stmt = null;
		 
		Double currentWeight = 0.0;
		String sql = "SELECT CurrentWeight FROM transaction WHERE RcmID=" + machineID;
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				currentWeight = Double.parseDouble(rs.getString(1));
				System.out.println(rs.getString(1));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return currentWeight;
	}
	
	/** Method to  get amount from databse
	 * @param String ID
	 * @return double
	 * */
	public static double getCurrentAmount(String machineID) {
		
		Connection conn = null;
		 ResultSet rs = null;
		 Statement stmt = null;
		 
		Double currentAmount = 0.0;
		String sql = "SELECT MoneyRemaining FROM transaction WHERE RcmID=" + machineID;
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				currentAmount = Double.parseDouble(rs.getString(1));
				System.out.println(rs.getString(1));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return currentAmount;
	}
	
	
	/** Method to  get itemweight from databse
	 * @param String type
	 * @return double
	 * */
	 public double getItemWeight(String type) {
		
		 Connection conn = null;
		 ResultSet rs = null;
		 Statement stmt = null;
		
		 Double itemWeight = 0.0;
		 String sql = "SELECT Weight FROM recycleitem WHERE Type='" + type+"'";
		 try {
			 conn = getConnection();
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				while(rs.next()) {
					itemWeight = Double.parseDouble(rs.getString(1));
					System.out.println(rs.getString(1));
				}
		 
		 } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 return itemWeight;
			
		 }
		
		 
	 /**Method to returns the price given for each item 
	  * @param  itemType: String
	  * @return Item price: double
	  * */
	 public double getItemPrice(String type) {
			
		 Connection conn = null;
		 ResultSet rs = null;
		 Statement stmt = null;
		
		 Double itemPrice = 0.0;
		 String sql = "SELECT Price FROM recycleitem WHERE Type='" + type+"'";
		 try {
			 conn = getConnection();
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				while(rs.next()) {
					itemPrice = Double.parseDouble(rs.getString(1));
					System.out.println(rs.getString(1));
				}
		 
		 } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 return itemPrice;
			
		 }
	 
	 /**Method to get type of an item
	  * @param  itemType: String
	  * @returns type of the item 
	  * */
	 public ArrayList<String> getItemType(String type) {
			
		 Connection conn = null;
		 ResultSet rs = null;
		 Statement stmt = null;
		
		 Double itemPrice = 0.0;
		 ArrayList<String> itemTypeList = new ArrayList<String>();
		 String sql = "SELECT Type FROM recycleitem";
		 try {
			 conn = getConnection();
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				while(rs.next()) {
					itemTypeList.add(rs.getString(1));
					//System.out.println(rs.getString(1));
				}
		 
		 } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 return itemTypeList;
			
		 }
	 
	 /**Method to get type of an item
	  * @param  itemType: String
	  * @returns type of the item 
	  * */
	public static ArrayList<String> getRcmItemType (int rcmID) {
			Connection conn = null;
			 ResultSet rs = null;
			 Statement stmt = null;
			ArrayList<String> itemTypeList = new ArrayList<String>();
			String sql= "SELECT ItemType from rcm_item WHERE RcmID=" + rcmID;
			try {
				conn = getConnection();			
				stmt = conn.createStatement();			
				rs = stmt.executeQuery(sql);
				while(rs.next()) {
					itemTypeList.add(rs.getString(1));	
					System.out.println(rs.getString(1));
					}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return itemTypeList;
		}
	 
	/** Method to update current weight
	 * @param ID:String  weight:double
	 * */
	public static void updateCurrentWeight(String machineID, double weight) {
		String sql = "UPDATE transaction SET CurrentWeight="+weight+" WHERE RcmID='"+machineID+"'";
		executeUpdateStatement(sql);
	 }
	
	 
	/** Method to update currentamount
	 * @param ID:String  weight:double
	 **/
	public static void updateCurrentAmount(String machineID, double amount) {
		String sql = "UPDATE transaction SET MoneyRemaining="+amount+" WHERE RcmID='"+machineID+"'";
		executeUpdateStatement(sql);
	 }
	

	 
	/** Method to retreive itemprice for all items*/
	public static ArrayList retrieveItemPrice () {
	
		Connection conn = null;
		 ResultSet rs = null;
		 Statement stmt = null;
		ArrayList<String> itemNameList = new ArrayList<String>();
		String sql= "SELECT Name,Price from recycleitem";
		try {
			conn = getConnection();			
			stmt = conn.createStatement();			
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				itemNameList.add(rs.getString(1));	
				System.out.println(rs.getString(1));
				}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return itemNameList;
}
  
}

