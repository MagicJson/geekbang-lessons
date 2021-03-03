package org.geektimes.projects.user.repository.work.manager;


import org.apache.derby.jdbc.EmbeddedDriver;
import org.geektimes.projects.user.repository.work.MyDBUserRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

/**
 * 数据连接管理器
 * {@link MyDBUserRepository}
 *
 * @author: magic_json
 * @create: 2021-03-02 21:05
 * @since 1.0
 **/
public class MyDBConnectionManager {

    private static String databaseUrl = "jdbc:derby:db/user-platform;create=true";

    private final static ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();

    private Connection connection;

    private static DataSource dataSource;


    public MyDBConnectionManager() {
    }

    public MyDBConnectionManager(Connection connection) {
        this.connection = connection;
    }

    public MyDBConnectionManager(String databaseUrl, Connection connection) {
        this.databaseUrl = databaseUrl;
        this.connection = connection;
    }


    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public static Connection getConnectionByJNDI(){
        Connection connection = connectionThreadLocal.get();
        if(Objects.isNull(connection)){
            try {
                connection =  dataSource.getConnection();
                connectionThreadLocal.set(connection);
            } catch (SQLException e) {
                return getConnection();
            }
        }
        return connection;
    }

    public static Connection getConnection() {
//        DriverManager初始化时加载
//        System.setProperty("jdbc.drivers","org.apache.derby.jdbc.EmbeddedDriver");
        Connection connection = tryGetConnection();
        if (Objects.isNull(connection)) {
            try {
                Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            } catch (ClassNotFoundException e) {
                try {
                    DriverManager.registerDriver(new EmbeddedDriver());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

        }
        return connection;
    }

    public static Connection tryGetConnection() {
        try {
            return DriverManager.getDriver(databaseUrl).connect(databaseUrl, new Properties());
        } catch (SQLException e) {
            return null;
        }
    }

    public static void releaseConnectionByJNDI(){
        Connection connection = connectionThreadLocal.get();
        if(Objects.nonNull(connection)){
            releaseConnection(connection);
            connectionThreadLocal.remove();
        }
    }

    public static void releaseConnection(Connection connection) {
        if (Objects.nonNull(connection)) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public static void setDataSource(DataSource dataSource) {
        MyDBConnectionManager.dataSource = dataSource;
    }
}

