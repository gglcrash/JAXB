package XmlTests.XmlExamples;

import com.netcracker.jaxb.annotations.Component;
import com.netcracker.jaxb.annotations.JaxbElement;

import java.util.List;

/**
 * Created by Никита on 18.12.2016.
 */
@Component("autopark")
public class AutoPark {
    @JaxbElement
    List<Person> drivers;
    @JaxbElement
    List<Car> cars;

    public AutoPark(){

    }

    public AutoPark(List<Person> drivers, List<Car> cars){
        this.cars = cars;
        this.drivers = drivers;
    }
}
