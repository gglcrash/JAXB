package DBTests;

import com.netcracker.jaxb.annotations.JaxbElement;
import com.netcracker.jaxb.managers.EntityManager;
import com.netcracker.jaxb.templates.case2.Contacts;
import com.netcracker.jaxb.templates.case2.Rector;
import com.netcracker.jaxb.templates.case2.University;

public class TestCase2 {
    EntityManager ent;
    public TestCase2(EntityManager entity){
        ent = entity;
    }

    @JaxbElement
    public University univer;
    public void testCase2WriteToDb(){
        univer = new University().setName("VSTU").setRector(new Rector().setName("Vasisualii").setSurname("Memk").setContacts(
                new Contacts().setMobile_number("8-9101521-332").setEmail("newmail@yandex.ru")));

        ent.marshall(this);
    }

    @JaxbElement(name = "VSU")
    public University loadUniver=null;
    public void testCase2LoadFromDb(){
        ent.unmarshall(this);

        System.out.println("University: "+loadUniver.getName());
        System.out.println("Rector name: "+loadUniver.getRector().getName());
        System.out.println("Rector surname: "+loadUniver.getRector().getSurname());
        System.out.println("Rector number: "+loadUniver.getRector().getContacts().getMobile_number());
        System.out.println("Rector email: "+loadUniver.getRector().getContacts().getEmail());
    }
}
