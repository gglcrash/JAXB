package com.netcracker.jaxb.jdbc;

import com.netcracker.jaxb.templates.case1.Ship;
import com.netcracker.jaxb.templates.case2.Contacts;
import com.netcracker.jaxb.templates.case2.Rector;
import com.netcracker.jaxb.templates.case2.University;

import java.sql.*;

public class MyConnection {

    private static Connection connection = null;
    private String url;
    private String name;
    private String password;
    private static MyConnection instance = null;
    public static MyConnection getInstance(String conn){
        if(instance == null){
            instance = new MyConnection(conn);
        }
        return instance;
    }

    private MyConnection(String conn){
        String[] splited = conn.split("\\s+");

        url = splited[0];
        name = splited[1];
        if(splited.length>2) {
            password = splited[2];
        }
    }

    private void setConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, name, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertShipIntoDb(String name, int x, int y){
        try {
            setConnection();

            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement(
                    "insert into ships(name,x,y) values(?,?,?)");
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, x);
            preparedStatement.setInt(3, y);
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

            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM ships where name = ?");

            preparedStatement.setString(1, name);
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

    public void insertUniversityIntoDb(University university){
        try {
            setConnection();

            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement(
                    "insert into universities(name,id_rector) values(?,?)");
            preparedStatement.setString(1, university.getName());
            int rector_id = insertRectorIntoDb(university.getRector());
            preparedStatement.setInt(2, rector_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            dropConnection();
        }
    }

    private int insertRectorIntoDb(Rector rector){
        try {
            setConnection();

            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement(
                    "insert into rectors(name,surname,id_contacts) values(?,?,?) returning id_rector");
            preparedStatement.setString(1, rector.getName());
            preparedStatement.setString(2, rector.getSurname());
            int contacts_id = insertContactsIntoDb(rector.getContacts());
            preparedStatement.setInt(3, contacts_id);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            dropConnection();
        }
        return 0;
    }

    private int insertContactsIntoDb(Contacts contacts){
        try {
            setConnection();

            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement(
                    "insert into contacts(mobile_number,email) values(?,?) returning id_contacts");
            preparedStatement.setString(1, contacts.getMobile_number());
            preparedStatement.setString(2, contacts.getEmail());
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            dropConnection();
        }
        return 0;
    }

    public University getUniversityFromDb(String name){
        University university = new University().setName("noname").setRector(
                new Rector().setName("norector").setSurname("nosurname").setContacts(
                        new Contacts().setMobile_number("nonumber").setEmail("nomail")));
        try {
            setConnection();

            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM universities where name = ?");

            preparedStatement.setString(1, name);
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()) {
                Rector rector = getRectorFromDb(result.getInt("id_rector"));
                university.setName(result.getString("name")).setRector(rector);
            } else {
                return university;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dropConnection();
        }
        return university;
    }

    private Rector getRectorFromDb(int id_rector){
        Rector rector = new Rector();
        try {
            setConnection();

            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM rectors where id_rector = ?");

            preparedStatement.setInt(1, id_rector);
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()) {
                Contacts cont = getContactsFromDb(result.getInt("id_contacts"));
                rector.setName(result.getString("name")).setSurname(result.getString("surname")).setContacts(cont);
            } else {
                return rector;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dropConnection();
        }
        return rector;
    }

    private Contacts getContactsFromDb(int id_contacts){
        Contacts cont = new Contacts();
        try {
            setConnection();

            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM contacts where id_contacts = ?");

            preparedStatement.setInt(1, id_contacts);
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()) {
                cont.setMobile_number(result.getString("mobile_number")).setEmail(result.getString("email"));
            } else {
                return cont;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dropConnection();
        }
        return cont;
    }

    private void dropConnection(){
        connection = null;
    }
}
