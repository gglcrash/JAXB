import com.netcracker.jaxb.ApplicationContext;
import com.netcracker.jaxb.annotations.RootElement;
import com.netcracker.jaxb.managers.ConnectionType;
import com.netcracker.jaxb.managers.EntityManager;
import com.netcracker.jaxb.templates.Ship;

import java.lang.reflect.InvocationTargetException;

public class Example {
    Ship writeShip1;

    Ship writeShip2;

    @RootElement(name = "Fastest")
    Ship loadShip;

    public void start() {

        writeShip1 = new Ship().setName("Fast").setX(15).setY(25);
        writeShip2 = new Ship().setName("Fastest2").setX(47).setY(40);

        EntityManager ent = EntityManager.getInstance(getDbConnectionString(), ConnectionType.DB);
        ent.unmarshall(this);
     //   ent.marshall(this);

        System.out.println("Ship name: "+loadShip.getName()+"\n");
        System.out.println("X coordinate: "+loadShip.getX()+"\n");
        System.out.println("Y coordinate: "+loadShip.getY()+"\n");

    }

    private String getDbConnectionString(){
        String url = "jdbc:postgresql://localhost:5432/JAXB";
        String name = "postgres";
        String password = "";
        return url.concat(" ").concat(name).concat(" ").concat(password);
    }
}