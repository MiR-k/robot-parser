package model;

import com.example.model.n2.n.StringInMethod;
import org.testng.annotations.Test;

import java.util.Collections;

public class TestStringInMethod {

    @Test
    public static void stringTest() {
        StringInMethod string = new StringInMethod();
        string.setCommentsOfMethod(Collections.singletonList("VarName"));
        string.setVariable("String var");
        string.setArgumentsOfMethod(Collections.singletonList("\"1\""));
        StringInMethod string1 = new StringInMethod(string);
        string.setMethodString("addUser");
        string.getArgumentsOfMethod().add(2);
    }

}
