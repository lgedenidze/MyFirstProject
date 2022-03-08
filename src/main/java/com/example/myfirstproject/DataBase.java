package com.example.myfirstproject;

import java.sql.*;

public class DataBase {
    public  Connection con;
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
    public Connection getConnections() {

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

}

