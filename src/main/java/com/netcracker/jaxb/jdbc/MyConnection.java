package com.netcracker.jaxb.jdbc;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MyConnection {
    java.sql.Connection connection = null;
    String url = "jdbc:postgresql://localhost:5432/JAXB";
    String name = "postgres";
    String password = "";
    PreparedStatement preparedStatement = null;
    private static MyConnection instance = null;
    public static MyConnection getConnection(){
        if(instance == null){
            instance = new MyConnection();
        }
        return instance;
    }

    private void setConnection() {
        try {
            Class.forName("org.postgresql.Driver");
         //   System.out.println("Драйвер подключен");
            connection = DriverManager.getConnection(url, name, password);
         //   System.out.println("Соединение установлено");

            preparedStatement = connection.prepareStatement(
                    "insert into classa(x,y,name) values(?,?,?)");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertIntoDb(int x, int y, String name){
        setConnection();
        try {
            preparedStatement.setInt(1, x);
            preparedStatement.setInt(2, y);
            preparedStatement.setString(3, name);
            //выполняем запрос
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
