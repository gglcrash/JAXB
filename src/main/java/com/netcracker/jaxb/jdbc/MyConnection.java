package com.netcracker.jaxb.jdbc;

import com.netcracker.jaxb.templates.Ship;

import java.sql.*;

public class MyConnection {

    private static Connection connection = null;
    private String url = "jdbc:postgresql://localhost:5432/JAXB";
    private String name = "postgres";
    private String password = "";
    private PreparedStatement preparedStatement = null;
    private static MyConnection instance = null;
    public static MyConnection getInstance(){
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
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertShipIntoDb(String name, int x, int y){
        try {
            setConnection();
            preparedStatement = connection.prepareStatement(
                    "insert into ships(name,x,y) values(?,?,?)");
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, x);
            preparedStatement.setInt(3, y);
            //выполняем запрос
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            dropConnection();
        }
    }

    public Ship getShipFromDb(String name){
        Ship ship = new Ship().setName("noname").setX(0).setY(0);
        try {
            setConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM ships where name = ?");

            preparedStatement.setString(1, name);
            //выполняем запрос
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()) {
                return ship.setName(result.getString("name")).setX(result.getInt("x")).setY(result.getInt("y"));
            } else {
                return ship;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dropConnection();
        }
        return ship;
    }

    private void dropConnection(){
        connection = null;
    }
}
