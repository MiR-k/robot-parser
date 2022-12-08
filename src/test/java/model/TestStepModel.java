package model;

import com.example.model.n2.n.Argument;
import com.example.model.n2.n.Step;
import com.example.model.n2.n.Suite;
import org.testng.annotations.Test;

public class TestStepModel {

    @Test
    void testStepToString() {
        Suite suite = new Suite();
        Step step = new Step();
        step.getComments().add("VarName");
        step.getComments().add("VarName");
        step.setMethodName("StepOne");
        step.getArguments().add(new Argument(String.class, "argument"));
        step.setReturnType(String.class);
        suite.addStep(step);
        suite.addStep(new Step(step));
        System.out.println(step.toString());
        step.getComments().clear();
        System.out.println(suite.toString());
    }

}
