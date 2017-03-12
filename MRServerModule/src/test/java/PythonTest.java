import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.junit.Test;
import org.python.core.PyException;
import org.python.util.PythonInterpreter;

/**
 * Created by Kray on 2017/3/12.
 */
public class PythonTest {

    @Test
    public void testPython() throws PyException {
        try {
            System.out.println("!!");
            PythonInterpreter interpreter = new PythonInterpreter();
            interpreter.execfile("../main/resources/python/Test.py");
            System.out.println(interpreter.getClass());
            System.out.println("??");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
