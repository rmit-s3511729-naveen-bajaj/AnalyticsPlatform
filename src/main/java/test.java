import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bda.analyticsplatform.utils.BDAException;
import com.google.gson.Gson;

public class test {
	Map<String,List<String>> tableColumns = new HashMap<String,List<String>>();
	public void printtt(){
		tableColumns.put("asda", null);
		System.out.println(tableColumns);
		System.out.println(new Gson().toJson(this));
	}
	public static void main(String[] args) throws BDAException, Exception {
		try {
			Class.forName("com.cloudera.hive.jdbc41.HS2Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
 
		Connection con = DriverManager.getConnection(
				"jdbc:hive://54.197.15.104:10000/default", "", "");
		Statement stmt = con.createStatement();
 
//		String tableName = "empdata";
//		stmt.executeQuery("drop table " + tableName);
//		ResultSet res = stmt.executeQuery("create table " + tableName
//				+ " (id int, name string, dept string)");
 
		// show tables
		String sql = "show tables";
		System.out.println("Running: " + sql);
		ResultSet res = stmt.executeQuery(sql);
		if (res.next()) {
			System.out.println(res.getString(1));
		}
	}

}
