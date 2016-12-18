package DBTests;

import com.netcracker.jaxb.managers.ConnectionType;
import com.netcracker.jaxb.managers.EntityManager;

public class Example {

    EntityManager ent = EntityManager.getInstance(getDbConnectionString(), ConnectionType.DB);

    public void start() {

        TestCase1 testCase1 = new TestCase1(ent);

        //testCase1.testCase1WriteToDb();

        testCase1.testCase1LoadFromDb();

        TestCase2 testCase2 = new TestCase2(ent);

        //testCase2.testCase2WriteToDb();

        //testCase2.testCase2LoadFromDb();

        TestCase3 testCase3 = new TestCase3(ent);

        //testCase3.testCase2WriteToDb();

        //testCase3.testCase2LoadFromDb();
    }



    private String getDbConnectionString(){
        String url = "jdbc:postgresql://localhost:5432/JAXB";
        String name = "postgres";
        String password = "";
        return url.concat(" ").concat(name).concat(" ").concat(password);
    }
}