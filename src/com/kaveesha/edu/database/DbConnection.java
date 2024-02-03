package com.kaveesha.edu.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    private static DbConnection dbConnection = null;
    private Connection connection;

    public DbConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/eduSmart",
                "root","Kavee@95");
    }

    public static DbConnection getInstance() throws SQLException, ClassNotFoundException {
        if(dbConnection == null){
            dbConnection = new DbConnection();
        }
        return dbConnection;
    }

    public Connection getConnection(){
        return connection;
    }

}
