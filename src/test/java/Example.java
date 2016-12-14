import com.netcracker.jaxb.ApplicationContext;
import com.netcracker.jaxb.annotations.db.LoadFromDb;
import com.netcracker.jaxb.annotations.db.WriteToDb;
import com.netcracker.jaxb.managers.ConnectionType;
import com.netcracker.jaxb.managers.EntityManager;
import com.netcracker.jaxb.templates.Ship;

import java.lang.reflect.InvocationTargetException;

public class Example {
    @WriteToDb
    Ship writeShip1;

    @WriteToDb
    Ship writeShip2;

    @LoadFromDb(name = "Swimmer")
    Ship loadShip;

    public void start() throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {

        writeShip1 = new Ship().setName("Fast").setX(15).setY(25);
        writeShip2 = new Ship().setName("Fastest").setX(77).setY(70);

        ApplicationContext.getInstance().checkFields(this);

        System.out.println("Ship name: "+loadShip.getName()+"\n");
        System.out.println("X coordinate: "+loadShip.getX()+"\n");
        System.out.println("Y coordinate: "+loadShip.getY()+"\n");

        EntityManager ent = EntityManager.getInstance("", ConnectionType.DB);
        ent.marshall();
    }
}