package com.kaveesha.edu.dao;

import com.kaveesha.edu.database.DbConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CurdUtil {

    //--> new method
    public static <T> T execute(String sql, Object... params) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        
        for(int i=0; i< params.length; i++){
            statement.setObject((i + 1),params[i]);
        }

        if(sql.startsWith("SELECT")){
            return (T) statement.executeQuery();
        }

        return (T)(Boolean)(statement.executeUpdate() > 0);
    }



    //--> old methods

//    public static PreparedStatement execute(String sql, Object... params) throws SQLException, ClassNotFoundException {
//        PreparedStatement statement = DbConnection.getInstance().getConnection().prepareStatement(sql);
//
//        for(int i=0; i < params.length; i++){
//            statement.setObject((i + 1), params[i]);
//        }
//        return statement;
//    }
//
// public static  boolean executeUpdate(String sql, Object... params) throws SQLException, ClassNotFoundException {
//        return execute(sql, params).executeUpdate() > 0;
// }
//
//    public static ResultSet executeQuery(String sql, Object... params) throws SQLException, ClassNotFoundException {
//        return execute(sql, params).executeQuery();
//    }
}
