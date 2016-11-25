package model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.text.WordUtils;

/**
 * Created by Dev on 23.10.2016.
 */
public class RobotSuite {

    boolean methodDetect = false;
    String regNameMethod = "(^[A-Z]{1}(\\w|\\d)*(\\s(\\'|\\w|\\d)*)*$)";
    String regexNameTest = "(^(С|C)\\d+\\:.*$)|(Configure Ufm Instance One)";
    String regexIdTest = "(^(С|C)\\d*\\:)";
    String regexArgument = "(^\\s*\\[Arguments\\](.*)$)";
    String regexComent = "(^((\\s*\\#\\s)|(\\s*\\#-))).*";
    String regexDescription = "(^\\s*\\[Documentation\\](.*)$)";
    String regexFollowString = "(^\\s*(\\.)+)";

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

    private Methods method;
    private Tests test;
    private LocalVariable localVariable;


    static List<String> stringVariableList = new LinkedList<String>();
    static List<String> stringMethodList = new LinkedList<String>();
    static List<String> stringTestList = new LinkedList<String>();

    //
    private String source;
    private String name;
    private List<LocalVariable> localVariables = null;
    private List<Methods> methods = null;
    private List<Tests> tests = null;
    //

    public RobotSuite() {
    }

    public RobotSuite(String id, String name) {
        this.source = id;
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    private String formatSource(String sting) {
        String fileName = sting.substring(sting.lastIndexOf("\\") + 1);
        fileName = fileName.replaceAll("\\d+_", "").replaceAll("_", " ").replaceAll(".*\\\\test\\\\", "").replaceAll(".robot", "");
        fileName = WordUtils.capitalize(fileName).replaceAll(" ", "");
        fileName = fileName.replaceAll("'", "").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("-", "");
        return fileName;
    }

    public List<Methods> getMethods() {
        return methods;
    }

    public void setMethods(List<Methods> methods) {
        this.methods = methods;
    }

    public void addMethod(Methods value) {
        methods.add(value);
    }

    public void setLocalVariables(List<LocalVariable> localVariables) {
        this.localVariables = localVariables;
    }

    public void addLocalVariables(LocalVariable localVariable) {
        this.localVariables.add(localVariable);
    }

    public List<LocalVariable> getLocalVariables() {
        return localVariables;
    }

    public String getForIndexLocalVariables(int index) {
        return localVariables.get(index - 1).getNameVariable();
    }

    public List<Tests> getTests() {
        return tests;
    }

    public void setTests(List<Tests> tests) {
        this.tests = tests;
    }

    public RobotSuite convertToRobotSuite(List<String> fileInList) {
        localVariable = new LocalVariable();
        method = new Methods();
        test = new Tests();
        convertToLists(fileInList);

        //its work
        setLocalVariables(localVariable.convertLocalVar(stringVariableList));
        setMethods(method.parseToMethod(stringMethodList));

        setTests(test.parseToTest(stringTestList));
        return this;
    }

    public String printRobotSuite() {
        return printRobotClass();
    }

    private String printRobotClass() {
        localVariable = new LocalVariable();
        test = new Tests();
        method = new Methods();
        String printResult = "";
        if (!getLocalVariables().isEmpty()) {
            printResult += localVariable.printLocalVariable(getLocalVariables());
        }
        if (!getTests().isEmpty()) {
            printResult += test.printTest(getTests());
        }
        if (!getMethods().isEmpty()) {
            printResult += method.printMethod(getMethods());
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
                "\n" +
                "import static org.hamcrest.CoreMatchers.equalTo;\n" +
                "import static org.hamcrest.MatcherAssert.assertThat;\n" +
                "import static org.testng.Assert.assertEquals;\n" +
                "import static org.testng.Assert.assertNotEquals;\n" +
                "\n" +
                "/**\n" +
                " * Created by " + AUTOR + " on " + formatter.format(date) + "\n" +
                " */\n" +
                "\n" +
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


    private void convertToLists(List<String> fileInList) {
        boolean isVariable = false;
        boolean isMethod = false;
        boolean isTest = false;

        for (String oneString : fileInList) {

            if (oneString.length() != 0) {
                if (oneString.startsWith("*** Variables ***")) {
                    isVariable = true;
                    isMethod = false;
                    isTest = false;
                } else if (oneString.startsWith("*** Keywords ***")) {
                    isMethod = true;
                    isVariable = false;
                    isTest = false;
                } else if (oneString.startsWith("*** Test Cases ***") || oneString.startsWith("*** Testcases ***")) {
                    isTest = true;
                    isMethod = false;
                    isVariable = false;
                }
                if (isVariable) {
                    if (!(oneString.startsWith("*** Variables ***"))) {
                        stringVariableList.add(oneString);
                    }
                }
                if (isMethod) {
                    if (!oneString.startsWith("*** Keywords ***")) {
                        stringMethodList.add(oneString);
                    }
                }
                if (isTest) {
                    if (!oneString.startsWith("*** Test Cases ***") || oneString.startsWith("*** Testcases ***")) {
                        stringTestList.add(oneString);
                    }
                }
            }
        }
    }

    static StringInMethod stringInMethod = new StringInMethod();

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

    protected String formatMethodName(String name) {
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

    protected String typeOfVariable(String nameVariable) {
        String[] stringVariables = {
                "testId", "customerId", "serviceId", "subscriberId", "personalCustomerId", "type",
                "operationDate", "numGroup", "contractId", "secondContractId", "thirdContractId", "payId",
                "amount", "virtId", "vrtp", "vrct", "deleted", "end_date", "start_date", "secondVirtId",
                "VirtId", "sysdate", "deldate", "cbRTPL_ID", "ratePlanId", "prefixExternalPhone", "CB", "prefix",
                "MSISDN", "msisdn", "balance", "spent", "payments", "casId", "СasId", "SUBS_ID", "newRatePlanId",
                "newCustomerId", "timeBeforeUpdate", "timeAfterUpdate", "lastChangeDate", "Threshold", "subsCount"
        };
        String[] intVariable = {
                "stepId", "step", "defaultSetId", "defaultSet", "setId", "sanctionId", "UFM_ID", "UFM_INSTANCE_ONE"
        };
        String[] byteVariable = {
                "res"
        };
        for (String item : stringVariables) {
            if (nameVariable.contains(item)) {
                return "String ";
            }
        }
        for (String item : byteVariable) {
            if (nameVariable.contains(item)) {
                return "byte ";
            }
        }
        for (String item : intVariable) {
            if (nameVariable.contains(item)) {
                return "int ";
            }
        }
        if (nameVariable.contains("List")) {
            return nameVariable;
        }
        return "String ";
    }


}
