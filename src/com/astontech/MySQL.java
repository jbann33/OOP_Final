package com.astontech;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class MySQL {
    protected static String dbHost = "localhost";
    protected static String dbName = "OOP_Final";
    protected static String dbUser = "root";
    protected static String dbPassword = "BANjo33-";
    protected static String useSSL = "false";
    protected static String procBod = "true";

    protected static Connection connection = null;

    final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(MySQL.class);

    protected static final int GET_BY_ID = 10;
    protected static final int GET_COLLECTION = 20;
    protected static final int GET_FIVE_LARGEST = 19;
    protected static final int GET_FILE_TYPE_TEXT = 11;
    protected static final int GET_FILE_TYPE_APP = 12;
    protected static final int GET_FILE_TYPE_VIDEO = 13;
    protected static final int GET_FILE_TYPE_IMAGE = 14;
    protected static final int INSERT = 10;
    protected static final int UPDATE = 20;
    protected static final int DELETE = 30;
    protected static final int DELETE_ALL = 5;

    protected static void Connect() {
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//        } catch (ClassNotFoundException ex) {
//            logger.error("MySQL Driver not found! " + ex);
//        }
//
//        logger.info("MySQL Driver Registered");


        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + dbHost + ":3306/" + dbName + "?useSSL=" + useSSL + "&noAccessToProcedureBodies=" + procBod, dbUser, dbPassword);
        } catch (SQLException ex) {
            logger.error("Connection failed! " + ex);
        }

//        if(connection != null) {
//            logger.info("Successfully connected to MySQL database");
//        } else {
//            logger.info("Connection failed!");
//        }
    }
}
