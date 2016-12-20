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
public class XmlUnmarshallingTest {

    @Test
    public void unmarshallXmlToSimpleClassPerson(){
        EntityManager entityManager = EntityManager.getInstance("example.xml", ConnectionType.XML);
        Person person = new Person();
        entityManager.unmarshall(person);
        System.out.println("");
    }

    @Test
    public void unmarshallXmlToCollection(){
        EntityManager entityManager = EntityManager.getInstance("exampleCollection.xml", ConnectionType.XML);
        List<Person> personList = new ArrayList<Person>();
        entityManager.unmarshall(personList);
        int i = 0;
    }

    @Test
    public void unmarshallingClassWithCollectionfromXml(){
        AutoPark autopark = new AutoPark();
        EntityManager entityManager = EntityManager.getInstance("exampleClassWithCollections.xml", ConnectionType.XML);
        entityManager.unmarshall(autopark);
        int i = 0;
    }

}
