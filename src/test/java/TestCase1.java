import com.netcracker.jaxb.annotations.RootElement;
import com.netcracker.jaxb.managers.EntityManager;
import com.netcracker.jaxb.templates.case1.Ship;

public class TestCase1 {
    EntityManager ent;
    public TestCase1(EntityManager entity){
        ent = entity;
    }


    @RootElement
    Ship writeShip1;
    @RootElement
    Ship writeShip2;
    public void testCase1WriteToDb(){
        writeShip1 = new Ship().setName("Fast").setX(15).setY(25);
        writeShip2 = new Ship().setName("Fastest2").setX(47).setY(40);

        ent.marshall(this);
    }

    @RootElement(name="Swimmer")
    Ship loadShip;
    public void testCase1LoadFromDb(){
        ent.unmarshall(this);

        System.out.println("Ship name: "+loadShip.getName()+"\n");
        System.out.println("X coordinate: "+loadShip.getX()+"\n");
        System.out.println("Y coordinate: "+loadShip.getY()+"\n");
    }


}
