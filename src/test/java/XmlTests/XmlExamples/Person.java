package XmlTests.XmlExamples;

import com.netcracker.jaxb.annotations.Component;
import com.netcracker.jaxb.annotations.JaxbElement;

/**
 * Created by Никита on 18.12.2016.
 */
@JaxbElement
@Component("person")
public class Person {
    @JaxbElement
    private String firstName;
    @JaxbElement
    private String middleName;
    @JaxbElement
    private String lastName;
    @JaxbElement
    private int age;


    public Person(String firstName, String middleName, String lastName, int age) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.age = age;
    }
}
