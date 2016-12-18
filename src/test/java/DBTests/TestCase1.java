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
    Ship writeShip1;
    @JaxbElement
    Ship writeShip2;
    public void testCase1WriteToDb(){
        writeShip1 = new Ship().setName("Forward").setX(20).setY(35);
        writeShip2 = new Ship().setName("Swim2").setX(25).setY(12);

        ent.marshall(this);
    }

    @JaxbElement(name="Fast")
    Ship loadShip;
    public void testCase1LoadFromDb(){
        ent.unmarshall(this);

        System.out.println("Ship name: "+loadShip.getName()+"\n");
        System.out.println("X coordinate: "+loadShip.getX()+"\n");
        System.out.println("Y coordinate: "+loadShip.getY()+"\n");
    }


}
