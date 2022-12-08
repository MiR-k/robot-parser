package com.example.model.n;

import com.example.model.StringInMethod;
import org.apache.commons.lang3.text.WordUtils;
import parser.ObjectType;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static parser.ObjectType.*;

/**
 * Created by Dev on 23.10.2016.
 */
public class RobotSuite {

    static String AUTOR = "Ivan.Ivanov";
    static String FEATURES = "\"UFM\"";
    static String BASECLASS = "BaseTest";

    //
    private String stringPackage = "com.peterservice.ufm.test";
    private List<RobotVariable> robotVariables = null;
    private List<RobotMethod> methods = new ArrayList<RobotMethod>();
    private List<RobotTest> tests = null;
    //

    public RobotSuite(List<RobotVariable> robotVariables, List<RobotMethod> methods, List<RobotTest> tests) {
        this.robotVariables = robotVariables;
        this.methods = methods;
        this.tests = tests;
    }

    static Date date = new Date();
    static SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

    private String formatSource(String sting) {
        String fileName = sting.substring(sting.lastIndexOf("\\") + 1);
        fileName = fileName.replaceAll("\\d+_", "").replaceAll("_", " ").replaceAll(".*\\\\test\\\\", "").replaceAll(".robot", "");
        fileName = WordUtils.capitalize(fileName).replaceAll(" ", "");
        fileName = fileName.replaceAll("'", "").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("-", "");
        return fileName;
    }

    public List<RobotMethod> getMethods() {
        return methods;
    }

    public List<RobotVariable> getListOfLocalVariables() {
        return robotVariables;
    }

    public String getForIndexLocalVariables(int index) {
        return robotVariables.get(index - 1).getNameVariable();
    }

    public List<RobotTest> getListOfTests() {
        return tests;
    }

    public void setDescriptionForSuite(String autor, String features, String baseClass) {
        this.AUTOR = autor;
        this.FEATURES = features;
        this.BASECLASS = baseClass;
    }

//    public RobotSuite convertToRobotSuite(List<String> fileInList) {
//        Map<ObjectType, List> objectsHashtable = convertToObjectsMap(fileInList);
//
//        this.robotVariables = new RobotVariable().convertLocalVar(objectsHashtable.get(Variable));
//        this.methods = new RobotMethod().parseToMethod(objectsHashtable.get(Method));
//        this.tests = new RobotTest().parseToTest(objectsHashtable.get(Test));
//        return this;
//    }
//
//    public List<RobotTest> parseToTest(List<String> strTestsList) {
//        for (String oneString : strTestsList) {
//            workWithString(oneString);
//        }
//        setTestList();
//        return robotTestList;
//    }
//
//    public String printRobotSuite() {
//        return printRobotClass();
//    }
//
//    private String printRobotClass() {
//        robotVariable = new RobotVariable();
//        test = new RobotTest();
//        method = new RobotMethod();
//        String printResult = "";
//        if (!getListOfLocalVariables().isEmpty()) {
//            printResult += robotVariable.printLocalVariable(getListOfLocalVariables());
//        }
//        if (!getListOfTests().isEmpty()) {
//            printResult += test.printTest(getListOfTests());
//        }
//        if (!getMethods().isEmpty()) {
//            printResult += method.printMethod(getMethods());
//        }
//        return "package com.peterservice.ufm.test;\n" +
//                "\n" +
//                "import org.testng.annotations.*;\n" +
//                "import ru.yandex.qatools.allure.annotations.*;\n" +
//                "import ru.yandex.qatools.allure.model.SeverityLevel;\n" +
//                "\n" +
//                "import java.util.concurrent.TimeUnit;\n" +
//                "\n" +
//                "\n" +
//                "import static java.util.Arrays.*;" +
//                "\n" +
//                "\n" +
//                "import static org.hamcrest.CoreMatchers.equalTo;\n" +
//                "import static org.hamcrest.MatcherAssert.assertThat;\n" +
//                "import static org.testng.Assert.assertEquals;\n" +
//                "import static org.testng.Assert.assertNotEquals;\n" +
//                "\n" +
//                "/**\n" +
//                " * Created by " + AUTOR + " on " + formatter.format(date) + "\n" +
//                " */\n" +
//                "\n" +
//                "//old path - " + getTestId() + "\n" +
//                "\n" +
//                "@Features({" + FEATURES + "})//todo : place all check result in methods\n" +
//                "@Stories({\"" + formatSource(getTestId()) + "\"})\n" +
//                "public class " + formatSource(getTestId()) + " extends " + BASECLASS + " {\n" +
//                "\n" +
//                "" + printResult + "\n}";
//    }

    static com.example.model.StringInMethod stringInMethod = new StringInMethod();

    //#section protected
    protected String inNameNumberToEnd(String presentString) {

        Pattern pattern = Pattern.compile("^\\d*");
        Matcher matcher = pattern.matcher(presentString);
        int start = 0;
        if (Pattern.matches("^\\d+\\w*", presentString)) {
            while (matcher.find(start)) {
                String startString = presentString.substring(matcher.start(), matcher.end());
                String endString = presentString.substring(matcher.end(), presentString.length());
                start = matcher.end();
                presentString = endString + startString;
            }
        }
        return presentString;
    }

    public static String formatMethodName(String name) {
        if (name.contains("keywords")) {
            name = name.split("keywords")[1];
        }
        if (name.contains("ufm_control_functions")) {
            name = name.replaceAll("ufm_control_functions", "");
        }
        if (name.length() > 0) {
            name = name.replaceAll("\\.", " ").replaceAll("_", " ").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("-", "").replace("'", "");
            String methodName = WordUtils.capitalize(name).replaceAll(" ", "").replaceAll("'", "").replace("=", "Equally")
                    .replaceAll("<", "Less").replaceAll(">", "Greater").replaceAll(",", "");
            if (methodName.length() > 0) {
                return Character.toLowerCase(methodName.charAt(0)) + methodName.substring(1);
            }
        }
        return name;
    }

    protected static String formatDescription(String name) {
        if (name.length() > 0) {
            name = name.replace("[Documentation]", "").trim().replace("}", ") {").replaceAll("\\.", " ").replaceAll("_", " ")
                    .replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("-", "").replace("'", "").replace("\"", "\\\"");
        }
        return name;
    }

    private Map<ObjectType, List> convertToObjectsMap(List<String> fileInList) {
        ObjectType objectType = null;
        Map<ObjectType, List> objectsHashtable = new Hashtable<ObjectType, List>();

        for (String oneString : fileInList) {
            if (oneString.length() == 0) continue;
            if (oneString.startsWith("*** ")) {
                if (oneString.startsWith("*** Variables ***")) {
                    objectType = Variable;
                } else if (oneString.startsWith("*** Keywords ***")) {
                    objectType = Method;
                } else if (oneString.startsWith("*** Test Cases ***") || oneString.startsWith("*** Testcases ***")) {
                    objectType = Test;
                }

            } else {
                if (objectType == null) continue;
                if (objectsHashtable.get(objectType) == null) {
                    objectsHashtable.put(objectType, new ArrayList<String>());
                }
                objectsHashtable.get(objectType).add(oneString);
            }
        }
        return objectsHashtable;
    }


}
