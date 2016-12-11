import com.netcracker.jaxb.annotations.LoadToDb;
import com.netcracker.jaxb.templates.ClassA;

public class Example {
    @LoadToDb
    ClassA classA;

    public void start(){
        classA.setName("test class");
        classA.setX(15);
        classA.setY(20);
    }
}
