import com.netcracker.jaxb.ApplicationContext;
import com.netcracker.jaxb.annotations.db.WriteToDb;
import com.netcracker.jaxb.jdbc.*;
import com.netcracker.jaxb.templates.ClassA;

import java.lang.reflect.InvocationTargetException;

public class Example {
    @WriteToDb
    ClassA classA;


    public void start() throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {


        classA = new ClassA();
        classA.setName("test class 45");
        classA.setX(100);
        classA.setY(200);

 //
        ApplicationContext.getInstance().checkFields(this);
    }
}