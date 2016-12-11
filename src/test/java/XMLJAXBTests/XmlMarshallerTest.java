package XMLJAXBTests;

import XMLJAXBTests.TestClasses.Employee;
import com.netcracker.jaxb.context.JAXBContext;
import org.junit.Test;

/**
 * Created by Никита on 11.12.2016.
 */

public class XmlMarshallerTest {

    @Test
    public void MarshallingClassToXMLTest(){
        JAXBContext ctx = new JAXBContext(Employee.class);
    }
}
