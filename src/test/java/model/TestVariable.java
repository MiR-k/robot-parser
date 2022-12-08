package model;

import com.example.model.n.MethodBody;
import com.example.model.n2.n.Suite;
import com.example.model.n2.n.Variable;
import org.testng.annotations.Test;

import java.net.URL;

import static org.junit.Assert.assertThat;


public class TestVariable {

    @Test
    public void variableTest() {
        Variable variable = new Variable();
        variable.setVariableName("VarName");
        variable.setVariableValue("1111");
        variable.setVariableType(int.class);
        Suite suite = new Suite();
        URL url = MethodBody.class.getResource("");
        String resourcesPath = url.getPath();
        suite.addSuiteVariable(new Variable(variable));
        assertThat(suite, null);
    }

}