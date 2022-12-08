package parser;

import com.example.model.n.StringInMethod;
import com.example.model.n.RobotMethod;
import com.example.model.n.RobotSuite;
import com.example.model.n.RobotTest;
import com.example.model.n.RobotVariable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.example.model.n.RobotTest.printTest;
import static com.example.model.n.RobotVariable.printLocalVariable;
import static parser.ObjectType.*;
import static parser.RobotMethodParser.printMethod;

//import static model.LocalVariable.convertLocalVar;

/**
 * Created by Dev on 23.10.2016.
 */
public class RobotSuiteParser extends AbstractParser {

    static String AUTOR = "Ivan.Ivanov";
    static String FEATURES = "\"UFM\"";
    static String BASECLASS = "BaseTest";

    public void setDescriptionForSuite(String autor, String features, String baseClass) {
        this.AUTOR = autor;
        this.FEATURES = features;
        this.BASECLASS = baseClass;
    }

    static Date date = new Date();
    static SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

    private RobotVariableParser robotVariable;
    private RobotMethodParser method;
    private RobotTestParser test;

    //
    private String source;
    private String name;
    private List<RobotVariable> robotVariables = null;
    private List<RobotMethod> methods = null;
    private List<RobotTest> tests = null;
    //

    public RobotSuiteParser() {
        super();
    }

    public List<RobotMethod> getMethods() {
        return methods;
    }

    public void setMethods(List<RobotMethod> methods) {
        this.methods = methods;
    }

    public void addMethod(RobotMethod value) {
        methods.add(value);
    }

    public void setRobotVariables(List<RobotVariable> robotVariables) {
        this.robotVariables = robotVariables;
    }

    public void addLocalVariables(RobotVariable robotVariable) {
        this.robotVariables.add(robotVariable);
    }

    public List<RobotVariable> getRobotVariables() {
        return robotVariables;
    }

    public String getForIndexLocalVariables(int index) {
        return robotVariables.get(index - 1).getNameVariable();
    }

    public List<RobotTest> getTests() {
        return tests;
    }

    public void setTests(List<RobotTest> tests) {
        this.tests = tests;
    }

    public RobotSuite convertToRobotSuite(List<String> fileInList) {
        convertToObjectsMap(fileInList);

        //its work

//        setTests();
        return new RobotSuite(
                new RobotVariableParser().parse(objectsHashtable.get(Variable)),
                new RobotMethodParser().parse(objectsHashtable.get(Method)),
                new RobotTestParser().parse(objectsHashtable.get(Test))
        );
    }

    public String printRobotSuite() {
        return printRobotClass();
    }

//    public List<?> parse(List<String> strings) {
//        return strings;
//    }

    private String printRobotClass() {
        String printResult = "";
        if (getRobotVariables() != null) {
            printResult += printLocalVariable(getRobotVariables());
        }
        if (getTests() != null) {
            printResult += printTest(getTests());
        }
        if (getMethods() != null) {
            printResult += printMethod(getMethods());
        }
        return "package com.peterservice.ufm.test;\n" +
                "\n" +
                "import org.testng.annotations.*;\n" +
                "import ru.yandex.qatools.allure.annotations.*;\n" +
                "import ru.yandex.qatools.allure.model.SeverityLevel;\n" +
                "\n" +
                "import java.util.concurrent.TimeUnit;\n" +
                "\n" +
                "\n" +
                "import static java.util.Arrays.*;" +
                "\n" +
                "import static org.hamcrest.CoreMatchers.equalTo;\n" +
                "import static org.hamcrest.MatcherAssert.assertThat;\n" +
                "import static org.testng.Assert.assertEquals;\n" +
                "import static org.testng.Assert.assertNotEquals;\n" +
                "\n" +
                "/**\n" +
                " * Created by " + AUTOR + " on " + formatter.format(date) + "\n" +
                " */\n" +
                "//old path - " + getSource() + "\n" +
                "\n" +
                "@Features({" + FEATURES + "})//todo : place all check result in methods\n" +
                "@Stories({\"" + formatSource(getSource()) + "\"})\n" +
                "public class " + formatSource(getSource()) + " extends " + BASECLASS + " {\n" +
                "\n" +
                "" + printResult + "\n}";
    }

    //need review
    @Deprecated
    private String fileName() {
        return this.getSource();
    }

    static StringInMethod stringInMethod = new StringInMethod();
}
