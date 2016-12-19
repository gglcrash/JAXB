package DBTests;

import com.netcracker.jaxb.annotations.JaxbElement;
import com.netcracker.jaxb.managers.EntityManager;
import com.netcracker.jaxb.templates.case1.Ship;

public class TestCase1 {
    EntityManager ent;
    public TestCase1(EntityManager entity){
        ent = entity;
    }


    @JaxbElement
    public Ship writeShip;
    public void testCase1WriteToDb(){
        writeShip = new Ship().setName("Forward").setX(20).setY(35);

        ent.marshall(this);
    }

    @JaxbElement(name="Swimmer")
    public Ship loadShip;
    public void testCase1LoadFromDb(){
        ent.unmarshall(this);

        System.out.println("Ship name: "+loadShip.getName()+"\n");
        System.out.println("X coordinate: "+loadShip.getX()+"\n");
        System.out.println("Y coordinate: "+loadShip.getY()+"\n");
    }


}
