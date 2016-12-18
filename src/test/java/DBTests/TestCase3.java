package DBTests;

import com.netcracker.jaxb.annotations.JaxbElement;
import com.netcracker.jaxb.managers.EntityManager;
import com.netcracker.jaxb.templates.case3.AbstractFigure;
import com.netcracker.jaxb.templates.case3.Circle;
import com.netcracker.jaxb.templates.case3.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class TestCase3 {
    EntityManager ent;
    public TestCase3(EntityManager entity){
        ent = entity;
    }

    @JaxbElement
    List<AbstractFigure> list = new ArrayList<AbstractFigure>();
    public void testCase2WriteToDb(){
        list.add(new Circle().setRadius(3).setX(2).setY(55));
        list.add(new Rectangle().setHeight(32).setWeight(35).setX(12).setY(42));
        list.add(new Circle().setRadius(37).setX(14).setY(22));
        list.add(new Rectangle().setHeight(27).setWeight(16).setX(23).setY(74));
        ent.marshall(this);
    }

    @JaxbElement
    List<AbstractFigure> loadList;
    public void testCase2LoadFromDb(){
        ent.unmarshall(this);

        for (AbstractFigure elem : loadList) {
            showFigureStats(elem);
        }
    }

    private void showFigureStats(AbstractFigure figure) {
        if (figure.getClass().isAssignableFrom(Circle.class)) {
            System.out.println("This is circle:");
            System.out.println("Coordinate X: "+figure.getX());
            System.out.println("Coordinate Y: "+figure.getY());
            System.out.println("Radius: "+((Circle)figure).getRadius()+"\n");
        } else if (figure.getClass().isAssignableFrom(Rectangle.class)) {
            System.out.println("This is rectangle:");
            System.out.println("Coordinate X: "+figure.getX());
            System.out.println("Coordinate Y: "+figure.getY());
            System.out.println("Height: "+((Rectangle)figure).getHeight());
            System.out.println("Weight: "+((Rectangle)figure).getWeight()+"\n");
        }
    }
}
