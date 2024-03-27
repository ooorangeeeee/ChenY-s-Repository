package cn.stum.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MysqlHelper {
	private static Connection conn = null;
	private static PreparedStatement pstmt = null;
	private static ResultSet rs = null;
	
	static void getConnection() throws Exception{
		Class.forName("com.mysql.cj.jdbc.Driver");
//												jdbc:mysql://localhost:3306/stum serverTimezone=GMT%2B8
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/stum?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&useUnicode=true&characterEncoding=utf8", "root", "123456");
	}
	
	public static List<HashMap<String, Object>> query(String sql, Object[] p) {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
		try {
			getConnection();
			pstmt = conn.prepareStatement(sql);
			if (p!=null) {
				for (int i = 0; i < p.length; i++) {
					pstmt.setObject(i+1, p[i]);
				}
				System.out.println(pstmt.toString());
				rs = pstmt.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				while (rs.next()) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					for (int i = 0; i < rsmd.getColumnCount(); i++) {
						map.put(rsmd.getColumnName(i+1),rs.getObject(i+1));
					}
					list.add(map);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}finally {
			myClose();
		}
		return list;
	}
	
	public static int update(String sql, Object[] p) {
		int result = 0;
		try {
			getConnection();
			pstmt = conn.prepareStatement(sql);
			if (p!=null) {
				for (int i = 0; i < p.length; i++) {
					pstmt.setObject(i+1, p[i]);
				}
				result = pstmt.executeUpdate();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			myClose();
		}
		return result;
	}

	private static void myClose() {
		// TODO Auto-generated method stub
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			if (pstmt != null) {
				pstmt.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
