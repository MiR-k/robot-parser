package model;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Arrays.asList;

/**
 * Created by Solit on 24.10.2016.
 */
public class StringInMethod extends RobotSuite {

    private String variable;

    private String methodString;

    private List<String> methodComment = null;

    private List<String> argumentOfMethod = null;

    public StringInMethod() {

    }

    public StringInMethod(String methodString) {
        this.methodString = methodString;
    }

    public StringInMethod(StringInMethod args) {
        this.methodString = args.getMethodString();
        this.argumentOfMethod = args.getArgumentOfMethod();
    }

    public List<String> getArgumentOfMethod() {
        return argumentOfMethod;
    }

    public void setArgumentOfMethod(List<String> argumentOfMethod) {
        this.argumentOfMethod = argumentOfMethod;
    }

    public String getMethodString() {
        return methodString;
    }

    public void setMethodString(String methodString) {
        this.methodString = builtInFormat(methodString);
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public List<String> getMethodComment() {
        return methodComment;
    }

    public void setMethodComment(List<String> methodComment) {
        this.methodComment = methodComment;
    }


    public String printStringsInMethod(List<StringInMethod> listStringMethod) {
        String result = "";
        boolean isExist = false;
        for (StringInMethod oneString : listStringMethod) {
            if (oneString.getVariable() != null && !checkExistence(result, oneString.getVariable())) {
                isExist = true;
            } else {
                isExist = false;
            }
            result += printOneString(oneString, isExist) + " \n";
        }
        return result;
    }

    private boolean checkExistence(String checkedString, String var) {
        return checkedString.contains(var.replace("${", "").replace("}", ""));
    }

    public String printOneString(StringInMethod stringMethod, boolean isExist) {
        String result = "";
        if (stringMethod.getVariable() != null && isExist) {
            result += typeOfVariable(stringMethod.getVariable());
        }
        if (stringMethod.getVariable() != null) {
            if (!stringMethod.getVariable().contains("List")) {
                result += String.format("%s = ", formatVariable(stringMethod.getVariable()));
            }
        }
        if (stringMethod.getMethodString() != null) {
            if (stringMethod.getMethodString().equals("setVariable") || stringMethod.getMethodString().contains("catenate")
                    || stringMethod.getMethodString().contains("evaluate") || stringMethod.getMethodString().contains("return")) {

                result += printWithoutMethod(stringMethod.getArgumentOfMethod(), stringMethod.getMethodString());
                if (result.startsWith("int") || result.startsWith("long")) {
                    result = result.replace("\"", "");
                }
                return result + "\n";
            } else {
                result += String.format("%s ", stringMethod.getMethodString());
                if (stringMethod.getArgumentOfMethod() == null && !stringMethod.getMethodString().startsWith(" //")) {
                    result += "(); \n";
                }
            }
            if (stringMethod.getArgumentOfMethod() != null) {
                result += printMethodVariable(stringMethod.getArgumentOfMethod());
            }
        }
        if (stringMethod.getArgumentOfMethod() != null && stringMethod.getMethodString() == null) {
            result += printMethodVariable(stringMethod.getArgumentOfMethod());
        }
        if (result.contains("convertTestId")){
            result = result.replace("convertTestId", "calcCommands.convertTestId");
        }
        return result;
    }

    private String printMethodVariable(List<String> variables) {
        String result = "";

        for (int i = 0; i < variables.size(); i++) {
            if (variables.get(i).startsWith("//") && variables.size() == 1) {
                return " " + variables.get(i);
            }
            if ((variables.size() == 1)) {
                result += String.format("(%s); ", formatPrintString(variables.get(i)));
            } else if (i == 0) {
                result += String.format("(%s,", formatPrintString(variables.get(i)));
            } else if (i == variables.size() - 1) {
                result += String.format(" %s);", formatPrintString(variables.get(i)));
            } else {
                result += String.format(" %s,", formatPrintString(variables.get(i)));
            }
        }
        return formatStep(result);
    }

    private String formatStep(String presentString) {
        StringBuffer result = new StringBuffer(presentString);
        Pattern pattern = Pattern.compile(".*(\\( testId, \\\"\\d{1,2}\\\",?).*");
        Matcher matcher = pattern.matcher(result);

        int start = 0;
        String firstString = "";
        String secondString = "";
        while (matcher.find(start)) {
            firstString = result.substring(0, result.indexOf("\""));
            secondString = result.substring(result.indexOf("\"") + 1, result.length());
            if(secondString.length() != 0){
                firstString += secondString.substring(0, secondString.indexOf("\""));
                secondString = secondString.substring(secondString.indexOf("\"") + 1, secondString.length());
            }
            start = matcher.end();
            result = new StringBuffer(firstString + secondString);
        }
        start = 0;
        return result.toString();
    }


    private String printWithoutMethod(List<String> variables, String methodName) {
        String result = "";
        String formated = "";
        if (methodName.equals("setVariable") || methodName.equals("evaluate")) {
            formated = "%s";
        } else if (methodName.contains("SEPARATOR=")) {
            formated = "%s +";
        } else if (methodName.equals("catenate")) {
            formated = "%s +\" \"+";
        } else if (methodName.equals("return")) {
            formated = "return %s";
        }

        for (int i = 0; i < variables.size(); i++) {
            if (i == variables.size() - 1) {
                if (formated.contains("+")) {
                    formated = formated.replaceAll(" \\+", "").replaceAll("\"\\s\"\\+", "");
                }
                result += String.format(formated + ";", formatPrintString(variables.get(i)));
            } else {

                result += String.format(formated, formatPrintString(variables.get(i)));
            }
        }
        return result.replaceAll("(\\\\\\\"\\s\\+){1}", "\" +").replaceAll("\\s\\+\\\\\"", " + \"");
    }

    //
    private String formatPrintString(String variable) {
        if (variable.startsWith("[{") && variable.endsWith("]")) {
            variable = String.format("\"%s\"", variable.replace(": ${", ": ").replace("}, ", ",").replace("}}", "}"));
        } else if (variable.contains("${") && variable.contains("}")) {
            //String temp = variable;
            //variable = "";
            //need review
            //while (Pattern.matches("(.*\\s)?\\$\\{\\w*\\}(\\s.*)?", temp)){
            //    String first = temp.substring(0, temp.indexOf("${"));
            //    String second = temp.substring(temp.indexOf("${")+2, temp.indexOf("}"));
            //    String three = temp.substring(temp.indexOf("}")+1, temp.length());
            //    variable += "\"" + first + "\" +" + second;
            //    temp = three;
            //}
            variable = String.format(" %s", variable.replace("${", "").replace("}", ""));
        } else if (variable.contains("@{") && variable.contains("}")) {
            variable = String.format(" %s", variable.replace("@{", "").replace("}", ""));
        } else if (variable.contains("[") || variable.endsWith("]")) {
            variable = String.format("%s", variable);
        } else if (variable.contains("{") || variable.endsWith("}")) {
            variable = String.format("%s", variable);
        } else {
            variable = String.format("\"%s\"", variable);
        }
        return variable;
    }

    private String finalFormatString(String variable) {
        return variable.replace("\"", "\\\"").replace("\'", "\\\'").trim();
    }

    protected static String formatVariable(String name) {
        if (name.contains("keywords")) {
            name = name.split("keywords")[1];
        }
        if (name.contains("ufm_control_functions")) {
            name = name.replaceAll("ufm_control_functions", "");
        }
        if (name.length() > 0) {
            name = name.replace("${", "").replace("}", "");
            if (name.length() > 0) {
                return Character.toLowerCase(name.charAt(0)) + name.substring(1);
            }
        }
        return name;
    }

    public StringInMethod convertStringMethod(String oneString) {
        convertToStringMethod(oneString);
        return this;
    }

    private StringInMethod convertToStringMethod(String oneString) {
        String variable = "";
        String nextString = "";
        oneString = checkSetVar(oneString.trim());
        if (oneString.startsWith("#")) {
            setMethodString(oneString.replaceAll("^\\#", " //"));
        } else {
            if (oneString.contains("Return")) {
                parseReturn(oneString);
            } else if (oneString.startsWith("${")) {
                variable = oneString.substring(0, oneString.indexOf("="));
                nextString = oneString.substring(oneString.indexOf("=") + 1, oneString.length());
                setVariable(variable);
                setMethodString(findMethodAndArgument(nextString));
            } else if (oneString.startsWith("@{")) {
                variable = oneString.substring(0, oneString.indexOf("}")).replace("@{", "");
                nextString = oneString.substring(oneString.indexOf("}") + 1, oneString.length());
                setVariable(String.format("List<String> %s =", variable));
                setMethodString(findMethodAndArgument(nextString));
            } else if (!oneString.contains("}") && !oneString.contains("${")) {
                setMethodString(oneString.replace(" ", ""));
            } else {
                setMethodString(findMethodAndArgument(oneString));
            }
        }
        return this;
    }

    private String checkSetVar(String oneString) {
        if (Pattern.matches("^\\$\\{(\\w|\\d)*\\}\\s+Set Variable.*", oneString)) {
            String tempString = oneString.substring(0, oneString.indexOf("}"));
            oneString = tempString + "}=" + oneString.substring(oneString.indexOf("}") + 1, oneString.length());
        }
        return oneString;
    }

    private String findMethodAndArgument(String oneString) {
        oneString = oneString.trim();
        Pattern pattern = Pattern.compile("^((\\w+\\s{1})*\\s+(SEPARATOR=)?)");
        Matcher matcher = pattern.matcher(oneString);
        String methodName = "";
        int start = 0;
        if (!convertLogicalExpression(oneString)) {
            while (matcher.find(start)) {
                methodName = oneString.substring(matcher.start(), matcher.end());

                String valueVariable = oneString.substring(matcher.end(), oneString.length()).trim();
                start = matcher.end();
                if (methodName.trim().equals("Set Variable") && (valueVariable.contains("${") && valueVariable.contains("}"))) {
                    valueVariable = valueVariable.replace("${", "   ${").replace("}", "}   ").replace("}   ]", "}]");
                }
                parseString(valueVariable);
            }
            if (methodName.length() == 0 && Pattern.matches(".*(\\s\\w+)*$", oneString)) {
                methodName = oneString;
            }
        }
        if (methodName.equals("createList")) {
            methodName = "asList";
        }
        return formatMethodName(methodName);
    }

    private void parseString(String oneString) {
        List<String> argument = new LinkedList<String>();
        String varName = oneString.trim();
        if (varName.contains("\"") || varName.contains("\'")) {
            varName = String.format("\"%s\"", finalFormatString(varName));
        }
        String nextSting = "";
        do {
            while (Pattern.matches(".*\\s{2}.*", varName)) {
                nextSting = varName;
                varName = varName.substring(0, varName.indexOf("  "));
                nextSting = nextSting.substring(nextSting.indexOf("  "), nextSting.length()).trim();
                argument.add(varName);
                varName = nextSting;
            }
        } while (nextSting.contains("  "));
        if (argument.size() > 0 && nextSting.length() > 0) {
            argument.add(nextSting);
        }
        if (oneString.length() != 0 && argument.isEmpty()) {
            argument.add(varName);
        }
        setArgumentOfMethod(argument);
    }

    private boolean convertLogicalExpression(String oneString) {
        boolean result = true;
        String stringForLogic = "// %s";
        List<String> tempList = new LinkedList<String>();
        oneString = oneString.trim();
        if (oneString.contains("Run Keyword If") || oneString.contains("Set Variable If")) {
            tempList.add(String.format(stringForLogic, "is IF " + oneString).trim());
        } else if (oneString.contains("ELSE")) {
            tempList.add(String.format(stringForLogic, "is IF " + oneString.replace("...", "").trim()));
        } else if (oneString.startsWith("...")) {
            tempList.add(String.format(stringForLogic, "is NEXT STRING " + oneString.replace("...", "").trim()));
        } else if (oneString.startsWith("\\")) {
            tempList.add(String.format(stringForLogic, oneString.replace("\\", " is FOR \\").trim()));
        } else if (oneString.contains(": FOR")) {
            tempList.add(String.format(stringForLogic, oneString));
        } else {
            result = false;
        }
        if (!tempList.isEmpty()) {
            setArgumentOfMethod(tempList);
        }
        return result;
    }

    private void parseReturn(String string) {
        String variable = string.substring(0, string.indexOf("]") + 1).trim();
        String[] varReturned = string.substring(string.indexOf("]") + 1, string.length()).trim().split("\n");
        setMethodString(variable.replaceAll("^\\[Return\\]", "return"));
        setArgumentOfMethod(asList(varReturned));
    }

    public void cutter(String oneString) {
        String temp = "";
        String nextString = "";
        if (oneString.length() != 0 && oneString.contains("${")) {
            temp = oneString.substring(0, oneString.indexOf("${") - 1).replace(" ", "");
            setMethodString(temp);
            nextString = oneString.substring(oneString.indexOf("${"), oneString.length());
            while (nextString.contains(" ")) {
                temp = nextString.substring(0, nextString.indexOf(" "));
                argumentOfMethod.add(temp);
                nextString = nextString.substring(nextString.indexOf(" ") + 1, nextString.length());
            }
            if (nextString.length() > 0) {
                argumentOfMethod.add(nextString);
                return;
            }
        }
        if (oneString.length() > 0) {
            argumentOfMethod.add(oneString.replace(" ", ""));
        }
    }

    private String builtInFormat(String name) {
        if (name.contains("shouldBeEqual")) {
            name = "assertEquals";
        }
        if (name.contains("shouldNotBeEqual")) {
            name = "assertNotEquals";
        }
        if (name.contains("Sleep")) {
            name = "waitABit";
        }
        return name;
    }
}
