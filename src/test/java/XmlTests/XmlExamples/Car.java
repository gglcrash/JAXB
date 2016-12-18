package XmlTests.XmlExamples;

import com.netcracker.jaxb.annotations.Component;
import com.netcracker.jaxb.annotations.JaxbElement;

/**
 * Created by Никита on 18.12.2016.
 */
@Component("car")
public class Car {
    @JaxbElement
    private String name;

    public Car(String name){
        this.name = name;
    }
}
