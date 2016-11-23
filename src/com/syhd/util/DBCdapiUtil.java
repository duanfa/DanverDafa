package com.syhd.util;

import java.sql.Connection;
import java.sql.SQLException;

import com.alibaba.druid.pool.DruidDataSource;

public class DBCdapiUtil {
	private static DruidDataSource cpds = null;

	public static AttrManager jdbcManager = new AttrManager("jdbc.properties");

	public static void init() {
		// 建立数据库连接池
		String DRIVER_NAME = jdbcManager.getStr("jdbc.driverClass"); // 驱动器
		String DATABASE_URL = jdbcManager.getStr("jdbc.url"); // 数据库连接url
		String DATABASE_USER = jdbcManager.getStr("jdbc.username"); // 数据库用户名
		String DATABASE_PASSWORD = jdbcManager.getStr("jdbc.password"); // 数据库密码
		int Min_PoolSize = jdbcManager.getInt("cpool.minPoolSize");
		int Max_PoolSize = jdbcManager.getInt("cpool.maxPoolSize");
		int Acquire_Increment = jdbcManager.getInt("cpool.acquireIncrement");
		int Initial_PoolSize = jdbcManager.getInt("cpool.initPoolSize");
		int Idle_Test_Period = jdbcManager
				.getInt("cpool.idleConnectionTestPeriod");
		// 每次连接验证连接是否可用
		String Validate = jdbcManager.getStr("cpool.testConnectionOnCheckout");
		if (Validate.equals("")) {
			Validate = "false";
		}
		String autoCommitOnClose = jdbcManager.getStr("jdbc.autoCommitOnClose");
		String breakAfterAcquireFailure = jdbcManager
				.getStr("cpool.breakAfterAcquireFailure");

		try {

			cpds = new DruidDataSource();

			cpds.setDriverClassName(DRIVER_NAME);
			cpds.setUrl(DATABASE_URL);
			cpds.setUsername(DATABASE_USER);
			cpds.setPassword(DATABASE_PASSWORD); // 密码
			cpds.setInitialSize(Initial_PoolSize);
			cpds.setMinIdle(Min_PoolSize);
			cpds.setMaxActive(Max_PoolSize);
			cpds.setTimeBetweenEvictionRunsMillis(Idle_Test_Period * 1000l);
			cpds.setTestOnBorrow(Boolean.parseBoolean(Validate));

			cpds.setDefaultAutoCommit(Boolean.parseBoolean(autoCommitOnClose));
			cpds.setMaxWait(jdbcManager.getInt("cpool.checkoutTimeout") * 1000l);
			cpds.setBreakAfterAcquireFailure(Boolean
					.parseBoolean(breakAfterAcquireFailure));
			cpds.setMinEvictableIdleTimeMillis(jdbcManager
					.getInt("cpool.maxIdleTime") * 1000l);

			cpds.setValidationQuery("SELECT 'x'");
			cpds.setTestWhileIdle(true);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static Connection getConnection() {
		Connection connection = null;
		try {
			if (cpds == null) {
				synchronized (DBCdapiUtil.class) {
					if (cpds == null) {
						init();
					}
				}
			}
			connection = cpds.getConnection();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return connection;
	}

	public static void release() {
		try {
			if (cpds != null) {
				cpds.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
