package com.seeyon.apps.caict.wx.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.log4j.Logger;

import com.seeyon.ctp.common.AppContext;

public class CallCenterOracleFactory {
	private static Logger LOGGER = Logger.getLogger(CallCenterOracleFactory.class);

	private static String oracleDriverName 	= AppContext.getSystemProperty("appealAccept.jdbcOracle.driver");
	private static String oracleUrl 		= AppContext.getSystemProperty("appealAccept.jdbcOracle.url");
	private static String oracleUserName 	= AppContext.getSystemProperty("appealAccept.jdbcOracle.username");
	private static String oraclePassword 	= AppContext.getSystemProperty("appealAccept.jdbcOracle.password");

	public static Connection getConnection() {
		LOGGER.info("进入CallCenterOracleFactory:  执行getConnection()");
		Connection oracleConn = null;
		try {
			Class.forName(oracleDriverName);
			oracleConn = DriverManager.getConnection(oracleUrl, oracleUserName,
					oraclePassword);
			LOGGER.info("获取oracel数据库的连接 = " + oracleConn);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage(),e);
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage(),e);
		}
		return oracleConn;

	}

	/**
	 * 在一个数据库连接上执行一个静态SQL语句查询
	 * 
	 * @param conn
	 *            数据库连接
	 * @param staticSql
	 *            静态SQL语句字符串
	 * @return 返回查询结果集ResultSet对象
	 */
	public static ResultSet executeQuery(Connection conn, String staticSql) {
		ResultSet rs = null;
		try {
			// 创建执行SQL的对象
			Statement stmt = conn.createStatement();
			// 执行SQL，并获取返回结果
			rs = stmt.executeQuery(staticSql);
		} catch (SQLException e) {
			LOGGER.error("#ERROR# :执行SQL语句出错，请检查！\n" + staticSql, e);
		}
		return rs;
	}

	/**
	 * 在一个数据库连接上执行一个静态SQL语句
	 * 
	 * @param conn
	 *            数据库连接
	 * @param staticSql
	 *            静态SQL语句字符串
	 */
	public static void executeSQL(Connection conn, String staticSql) {
		try {
			// 创建执行SQL的对象
			Statement stmt = conn.createStatement();
			// 执行SQL，并获取返回结果
			stmt.execute(staticSql);
		} catch (SQLException e) {
			LOGGER.error("#ERROR# :执行SQL语句出错，请检查！\n" + staticSql, e);
		}
	}

	/**
	 * 在一个数据库连接上执行一批静态SQL语句
	 * 
	 * @param conn
	 *            数据库连接
	 * @param sqlList
	 *            静态SQL语句字符串集合
	 */
	public static void executeBatchSQL(Connection conn, List<String> sqlList) {
		try {
			// 创建执行SQL的对象
			Statement stmt = conn.createStatement();
			for (String sql : sqlList) {
				stmt.addBatch(sql);
			}
			// 执行SQL，并获取返回结果
			stmt.executeBatch();
		} catch (SQLException e) {
			LOGGER.error("#ERROR# :执行批量SQL语句出错，请检查！", e);
		}
	}

	public static void closeConnection(Connection conn) {
		if (conn == null)
			return;
		try {
			if (!conn.isClosed()) {
				// 关闭数据库连接
				conn.close();
			}
		} catch (SQLException e) {
			LOGGER.error("#ERROR# :关闭数据库连接发生异常，请检查！", e);
		}
	}
}
