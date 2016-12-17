package com.netcracker.jaxb.jdbc;

import com.netcracker.jaxb.templates.case1.Ship;
import com.netcracker.jaxb.templates.case2.Contacts;
import com.netcracker.jaxb.templates.case2.Rector;
import com.netcracker.jaxb.templates.case2.University;
import com.netcracker.jaxb.templates.case3.AbstractFigure;
import com.netcracker.jaxb.templates.case3.Circle;
import com.netcracker.jaxb.templates.case3.Rectangle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MyConnection {

    private static Connection connection = null;
    private String url;
    private String name;
    private String password;
    private static MyConnection instance = null;

    public static MyConnection getInstance(String conn) {
        if (instance == null) {
            instance = new MyConnection(conn);
        }
        return instance;
    }

    private MyConnection(String conn) {
        String[] splited = conn.split("\\s+");

        url = splited[0];
        name = splited[1];
        if (splited.length > 2) {
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

    private void dropConnection() {
        connection = null;
    }


    public void insertShipIntoDb(Ship ship) {
        try {
            setConnection();

            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement(
                    "insert into ships(name,x,y) values(?,?,?)");
            preparedStatement.setString(1, ship.getName());
            preparedStatement.setInt(2, ship.getX());
            preparedStatement.setInt(3, ship.getY());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dropConnection();
        }
    }

    public Ship getShipFromDb(String name) {
        Ship ship = new Ship().setName("noname").setX(0).setY(0);
        try {
            setConnection();

            PreparedStatement preparedStatement;

            if (!name.equals("")) {
                preparedStatement = connection.prepareStatement(
                        "SELECT * FROM ships where name = ?");

                preparedStatement.setString(1, name);
            } else {
                preparedStatement = connection.prepareStatement("SELECT * FROM ships " +
                        "where id_ship = (select max(id_ship) from ships )");
            }
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
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

    public void insertUniversityIntoDb(University university) {
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
        } finally {
            dropConnection();
        }
    }

    private int insertRectorIntoDb(Rector rector) {
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
        } finally {
            dropConnection();
        }
        return 0;
    }

    private int insertContactsIntoDb(Contacts contacts) {
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
        } finally {
            dropConnection();
        }
        return 0;
    }

    public University getUniversityFromDb(String name) {
        University university = new University().setName("noname").setRector(
                new Rector().setName("norector").setSurname("nosurname").setContacts(
                        new Contacts().setMobile_number("nonumber").setEmail("nomail")));
        try {
            setConnection();

            PreparedStatement preparedStatement;
            if (!name.equals("")) {
                preparedStatement = connection.prepareStatement(
                        "SELECT * FROM universities where name = ?");

                preparedStatement.setString(1, name);
            } else {
                preparedStatement = connection.prepareStatement("SELECT * FROM universities " +
                        "where id_university = (select max(id_university) from universities )");
            }
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
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

    private Rector getRectorFromDb(int id_rector) {
        Rector rector = new Rector();
        try {
            setConnection();

            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM rectors where id_rector = ?");

            preparedStatement.setInt(1, id_rector);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
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

    private Contacts getContactsFromDb(int id_contacts) {
        Contacts cont = new Contacts();
        try {
            setConnection();

            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM contacts where id_contacts = ?");

            preparedStatement.setInt(1, id_contacts);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
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

    public void insertCircleIntoDb(Circle circle) {
        try {
            setConnection();

            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement(
                    "insert into circles(x,y,radius) values(?,?,?)");
            preparedStatement.setInt(1, circle.getX());
            preparedStatement.setInt(2, circle.getY());
            preparedStatement.setInt(3, circle.getRadius());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dropConnection();
        }
    }

    public void insertRectangleIntoDb(Rectangle rect) {
        try {
            setConnection();

            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement(
                    "insert into rectangles(x,y,height,weight) values(?,?,?,?)");
            preparedStatement.setInt(1, rect.getX());
            preparedStatement.setInt(2, rect.getY());
            preparedStatement.setInt(3, rect.getHeight());
            preparedStatement.setInt(4, rect.getWeight());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dropConnection();
        }
    }

    public ArrayList<AbstractFigure> getListAbstractFigureFromDb() {
        ArrayList<AbstractFigure> myList = new ArrayList<AbstractFigure>();
        getCirclesFromDb(myList);
        getRectanglesFromDb(myList);
        return myList;
    }

    private void getRectanglesFromDb(ArrayList<AbstractFigure> myList) {
        try {
            setConnection();

            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM rectangles");
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                myList.add(new Rectangle().setHeight(result.getInt("height")).setWeight(result.getInt("weight" +
                        "")).setX(result.getInt("x")).setY(result.getInt("y")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dropConnection();
        }
    }

    private void getCirclesFromDb(ArrayList<AbstractFigure> myList) {
        try {
            setConnection();

            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM circles");

            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                myList.add(new Circle().setRadius(result.getInt("radius")).setX(result.getInt("x")).setY(result.getInt("y")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dropConnection();
        }
    }
}
