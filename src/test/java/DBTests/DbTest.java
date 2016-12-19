package DBTests;

import com.netcracker.jaxb.managers.ConnectionType;
import com.netcracker.jaxb.managers.EntityManager;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;

public class DbTest {

    private java.util.Properties properties;
    EntityManager ent = EntityManager.getInstance(getDbConnectionString(), ConnectionType.DB);
    TestCase1 testCase1 = new TestCase1(ent);
    TestCase2 testCase2 = new TestCase2(ent);
    TestCase3 testCase3 = new TestCase3(ent);

    @Test
    public void testCase1Write(){
        testCase1.testCase1WriteToDb();
    }
    @Test
    public void testCase1Load(){
        testCase1.testCase1LoadFromDb();
    }
    @Test
    public void testCase2Write(){
        testCase2.testCase2WriteToDb();
    }
    @Test
    public void testCase2Load(){
        testCase2.testCase2LoadFromDb();
    }
    @Test
    public void testCase3Write(){
        testCase3.testCase2WriteToDb();
    }
    @Test
    public void testCase3Load(){
        testCase3.testCase2LoadFromDb();
    }


    private String getDbConnectionString(){
        properties = new java.util.Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/jdbcConfig.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = properties.getProperty("URL");
        String user = properties.getProperty("USER");
        String password = properties.getProperty("PASSWORD");
        return url.concat(" ").concat(user).concat(" ").concat(password);
    }
}