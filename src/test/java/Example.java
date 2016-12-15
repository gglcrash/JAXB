import com.netcracker.jaxb.annotations.RootElement;
import com.netcracker.jaxb.managers.ConnectionType;
import com.netcracker.jaxb.managers.EntityManager;
import com.netcracker.jaxb.templates.case1.Ship;
import com.netcracker.jaxb.templates.case2.Contacts;
import com.netcracker.jaxb.templates.case2.Rector;
import com.netcracker.jaxb.templates.case2.University;

public class Example {
    Ship writeShip1;

    Ship writeShip2;

    Ship loadShip;

    University univer;

    @RootElement(name = "VSU")
    University loadUiver;

    public void start() {

        /*writeShip1 = new Ship().setName("Fast").setX(15).setY(25);
        writeShip2 = new Ship().setName("Fastest2").setX(47).setY(40);
*/

        univer = new University().setName("VSU").setRector(new Rector().setName("Andrey").setSurname("Petrov").setContacts(
                new Contacts().setMobile_number("8-919-108-33-28").setEmail("lala@yandex.ru")));


        EntityManager ent = EntityManager.getInstance(getDbConnectionString(), ConnectionType.DB);
        ent.unmarshall(this);
        //ent.marshall(this);
        System.out.println("University: "+loadUiver.getName());
        System.out.println("Rector name: "+loadUiver.getRector().getName());
        System.out.println("Rector surname: "+loadUiver.getRector().getSurname());
        System.out.println("Rector number: "+loadUiver.getRector().getContacts().getMobile_number());
        System.out.println("Rector email: "+loadUiver.getRector().getContacts().getEmail());

      /*  System.out.println("Ship name: "+loadShip.getName()+"\n");
        System.out.println("X coordinate: "+loadShip.getX()+"\n");
        System.out.println("Y coordinate: "+loadShip.getY()+"\n");*/

    }

    private String getDbConnectionString(){
        String url = "jdbc:postgresql://localhost:5432/JAXB";
        String name = "postgres";
        String password = "";
        return url.concat(" ").concat(name).concat(" ").concat(password);
    }
}