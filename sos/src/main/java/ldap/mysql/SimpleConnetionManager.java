package ldap.mysql;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import ldap.util.Config;
import ldap.util.IOUtil;

public class SimpleConnetionManager
{
  private static final String DB_CONFIG_FILE_PATH = "classes/jdbc2.properties";
  private static Properties properties = new Properties();
  
  static
  {
    String configPath = Config.getConfigPath();
    String filePath = configPath + "classes/jdbc2.properties";
    InputStream is = null;
    try
    {
      is = new FileInputStream(filePath);
      properties.load(is);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    finally
    {
      IOUtil.closeIS(is);
    }
  }
  
  private static String driverClassName = properties.getProperty("driverClassName");
  private static String url = properties.getProperty("url");
  private static String username = properties.getProperty("username");
  private static String password = properties.getProperty("password");
  
  public static Connection getConnection()
  {
    Connection conn = null;
    try
    {
      Class.forName(driverClassName);
      conn = DriverManager.getConnection(url, username, password);
    }
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
    return conn;
  }
  
  public static void main(String arg[]) throws Exception{
	  Class.forName("com.mysql.jdbc.Driver");
	  Connection conn = DriverManager.getConnection("jdbc:mysql://90.0.7.198:3306/gboss?useUnicode=true&characterEncoding=utf-8", 
    		  "gboss", "123456"); 
	  File file = new File("E:/sim.txt");
	  FileReader fileReader = new FileReader(file);
	  BufferedReader br = new BufferedReader(fileReader);
	  
	  PreparedStatement  st = conn.prepareStatement(" DELETE FROM t_ba_sim  WHERE barcode =  (?)");

	  while(br.read() != -1){
	      String  str = br.readLine(); 
	      System.out.println("str "+str);
	      st.setString(1, "0"+str);
		  st.executeUpdate();
	  }
  }
}
