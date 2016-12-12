import java.lang.reflect.InvocationTargetException;

public class DbTest {
    public static void main (String[] args){
        Example example = new Example();

        try {
            example.start();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
