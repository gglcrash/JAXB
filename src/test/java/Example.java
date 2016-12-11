import com.netcracker.jaxb.annotations.LoadToDb;
import com.netcracker.jaxb.jdbc.*;
import com.netcracker.jaxb.templates.ClassA;

import java.sql.*;
import java.sql.Connection;

public class Example {
    @LoadToDb
    ClassA classA;
    MyConnection connection;

    public void start() {
        connection = MyConnection.getConnection();

        classA = new ClassA();
        classA.setName("test class");
        classA.setX(15);
        classA.setY(20);

        connection.insertIntoDb(classA.getX(),classA.getY(),classA.getName());
    }
}