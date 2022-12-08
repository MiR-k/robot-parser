package model;

import com.example.model.n2.n.RobotTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;

public class TestModel {

    RobotTest test;

    @Test
    void testStepToString() {
        test = new RobotTest();
        test.setTestId(123);
        test.setTestTitle("123");
        test.setArgumentsLists(new ArrayList<>());
        test.setComments(Collections.singletonList("/////"));
        test.setBody(new ArrayList<>());
        System.out.println(test.toString());
    }

}
