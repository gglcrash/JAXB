package XmlTests;

import XmlTests.XmlExamples.AutoPark;
import XmlTests.XmlExamples.Car;
import XmlTests.XmlExamples.Person;
import com.netcracker.jaxb.managers.ConnectionType;
import com.netcracker.jaxb.managers.EntityManager;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Никита on 18.12.2016.
 */
public class XmlMarshallingTest {

    @Test
    public void marshallingElementtoXml(){
        Person person = new Person("Nikita", "Sergeevich", "Ryaskin", 19);
        EntityManager entityManager = EntityManager.getInstance("example.xml", ConnectionType.XML);
        entityManager.marshall(person);
    }

    @Test
    public void marshallingCollectionToXml(){

        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Nikita", "Sergeevich", "Ryaskin", 19));
        personList.add(new Person("Vadim", "Kek", "Ligin", 20));
        EntityManager entityManager = EntityManager.getInstance("exampleCollection.xml", ConnectionType.XML);
        entityManager.marshall(personList);
    }

    @Test
    public void marshallingClassWithCollectionToXml(){
        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Nikita", "Sergeevich", "Ryaskin", 19));
        personList.add(new Person("Vadim", "Kek", "Ligin", 20));
        personList.add(new Person("Random", "Person", "Name", 40));
        List<Car> cars = new ArrayList<Car>();
        cars.add(new Car("BMV"));
        cars.add(new Car("BMV"));
        cars.add(new Car("Rolls Roys"));
        AutoPark autopark = new AutoPark(personList, cars);
        EntityManager entityManager = EntityManager.getInstance("exampleClassWithCollections.xml", ConnectionType.XML);
        entityManager.marshall(autopark);
    }
}
