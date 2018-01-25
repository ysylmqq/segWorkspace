package cc.chinagps.lib.util;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ConnectionManager {
    static private ConnectionManager instance;   
    
    static synchronized public ConnectionManager getInstance() {   
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }
    
    private ConnectionManager() {
        this.init();
    }

    private void init(){
    	
    }
    
    public Connection getConnection() throws Exception{
    	Connection conn = null;
		String driverName = Config.getJdbcProperties().getProperty("jdbc.driverClassName");
		String url = Config.getJdbcProperties().getProperty("jdbc.url");
		String username = Config.getJdbcProperties().getProperty("jdbc.username"); 
		String password = Config.getJdbcProperties().getProperty("jdbc.password");
		Class.forName(driverName);
		conn = DriverManager.getConnection(url,username,password);	
		return conn;
    }
   
    public void freeConnection(String name, Connection con) {
        try{
        	 if (con != null) {
                 try {
                	 if(!con.isClosed()){
                		 con.close();
                	 }
                	 con = null;
                 } catch (SQLException e) {
                 }
             }
        }catch(Exception ex){
        	ex.printStackTrace();
        }
    }

    public void ClosePstam(ResultSet rs,PreparedStatement pstam,String dbconn,Connection conn){
        if (rs != null) {
            try {
                rs.close();
                rs = null;
            } catch (SQLException e) {
            }
        }
        if (pstam != null) {
            try {
                pstam.close();
                pstam = null;
            } catch (SQLException e) {
            }
        }
        try {
          freeConnection(dbconn, conn);
        }catch (Exception e) {}
      
    }

    public void CloseCstam(ResultSet rs,CallableStatement cstam,String dbconn,Connection conn){
        if (rs != null) {
            try {
                rs.close();
                rs = null;
            } catch (SQLException e) {
            }
        }
        if (cstam != null) {
            try {
                cstam.close();
                cstam = null;
            } catch (SQLException e) {
            }
        }
        try {
          freeConnection(dbconn, conn);
        }catch (Exception e) {}
      
    }

    public void CloseStam(ResultSet rs,Statement stam,String dbconn,Connection conn){
        if (rs != null) {
            try {
                rs.close();
                rs = null;
            } catch (SQLException e) {
            }
        }
        if (stam != null) {
            try {
                stam.close();
                stam = null;
            } catch (SQLException e) {
            }
        }
        try {
          freeConnection(dbconn, conn);
        }catch (Exception e) {}
    }
    
    public void release() {}
    public static void main(String[] args) {
		ConnectionManager connectionManager = new ConnectionManager();
		try {
			System.out.println(connectionManager.getConnection());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
