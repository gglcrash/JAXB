package XMLJAXBTests.TestClasses;

import com.netcracker.jaxb.annotations.XMLRootElement;
import com.netcracker.jaxb.annotations.XmlType;

/**
 * Created by Никита on 11.12.2016.
 */

@XMLRootElement
@XmlType(propOrder = {}, name = "employee")
public class Employee {
    private String firstName;
    private String midName;
    private String lastName;

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String firstName){
        this.firstName = firstName;
    }



}
