package com.example.myfirstproject;

import java.sql.*;

public class DataBase {
    public static Connection con;
    public Connection getConnection() {

      try {
          Class.forName("com.mysql.cj.jdbc.Driver");

         con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/employees",
                "root",
                "Kikoliko123!"
        );
      }catch (Exception e){
          e.printStackTrace();
          e.getCause();
      }
        return  con;

    }
    public static Connection getConnections() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/classicmodels",
                    "root",
                    "Kikoliko123!"
            );
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
        return  con;

    }

    public static ResultSet getResultSet(String Query) throws SQLException {
        con=DataBase.getConnections();
        Statement stm=con.createStatement();
        return stm.executeQuery(Query);
    }

    public static void updateSelect (String query) throws SQLException{
        con=DataBase.getConnections();
        Statement stm=con.createStatement();
        int countOfUpdate=stm.executeUpdate(query);
    }

}

