package org.geektimes.projects.user.web.listener.work;

import org.geektimes.projects.user.repository.work.manager.MyDBConnectionManager;
import org.geektimes.projects.user.sql.DBConnectionManager;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Objects;

/**
 * {@link }
 *
 * @description: DB数据初始化监听器
 * @author: magic_json
 * @create: 2021-03-03 20:17
 * @since 1.0
 **/
@WebListener
public class DBConnectionInitializerLister implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            InitialContext initialContext = new InitialContext();
            Context context = (Context) initialContext.lookup("java:/comp/env");
            DataSource dataSource = (DataSource) context.lookup("jdbc/UserPlatformDB");
            MyDBConnectionManager.setDataSource(dataSource);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        MyDBConnectionManager.releaseConnectionByJNDI();
    }
}

