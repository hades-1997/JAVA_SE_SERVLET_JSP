package com.ray.books.helpers;

import com.ray.books.dbmodels.DBManager;
import com.ray.books.dbmodels.IConnectionBehavior;
import com.ray.books.dbmodels.MssqlConnectionBehavior;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class DBManagerSetup implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    private DBManager dbm = null;

    public DBManagerSetup() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String uid = sce.getServletContext().getInitParameter("dbuserid");
        String pwd = sce.getServletContext().getInitParameter("dbuserpwd");
        String cat = sce.getServletContext().getInitParameter("dbinitcat");

        // Option 1: Connect to MySQL
        //IConnectionBehavior icb = new MySQLConnectionBehavior(uid,pwd,cat);

        // Option 2: Connect to MS SQLServer
        IConnectionBehavior icb = new MssqlConnectionBehavior(uid,pwd,cat);

        dbm = new DBManager(icb);
        sce.getServletContext().setAttribute("WorldDBManager", dbm);
        System.out.println("WorldDBManager has been created!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (dbm != null)
        {
            if (dbm.isConnected())
                dbm.closeConnection(false);
        }
        dbm = null;
    }
}
