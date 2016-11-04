package model;

import com.google.common.base.Strings;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Dev on 23.10.2016.
 */
public class Methods extends RobotSuite {

    private boolean isReturned = false;
    private String name;
    private String argument;
    private List<StringInMethod> strings = null;

    private int numberMethod = 0;
    private boolean isNewMethod = true;
    private List<StringInMethod> stringsList = new LinkedList<StringInMethod>();
    List<Methods> methodsList = new LinkedList<Methods>();

    public Methods() {
    }

    public Methods(String name) {
        this.name = name;
    }

    public Methods(Methods methods) {
        this.name = methods.getName();
        this.argument = methods.getArgument();
        this.strings = methods.getStrings();
        this.isReturned = methods.getReturned();
    }

    public Methods(String name, String argument, List<StringInMethod> strings) {
        this.name = name;
        this.argument = argument;
        this.strings = strings;
    }

    public void setReturned(boolean value) {
        this.isReturned = value;
    }

    public boolean getReturned() {
        return this.isReturned;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getName() {
        return this.name;
    }

    public void setStrings(List<StringInMethod> strings) {
        this.strings = strings;
    }

    public List<StringInMethod> getStrings() {
        return this.strings;
    }

    public String getValue() {
        return this.name;
    }

    public void setValue(String val) {
        this.name = name;
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }

    public String getArgument() {
        return argument;
    }


    public List<Methods> parseToMethod(List<String> methodList) {

        for (String oneString : methodList) {
            if (oneString.contains("[Return]")) {
                isReturned = true;
            }
            workWithString(oneString);
        }
        setMethodsList();
        return methodsList;
    }

    private void workWithString(String string) {
        StringInMethod stringInMethod = new StringInMethod();

        if (Pattern.matches(regNameMethod, string)) {
            if (isNewMethod) {
                isNewMethod = false;
            } else {
                if (!Strings.isNullOrEmpty(getName()) || !methodsList.isEmpty()) {
                    setMethodsList();
                }
            }
            setName(formatMethodName(string));
        } else if (Pattern.matches(regexArgument, string)) {
            setArgument(convertArgument(string));
        } else {
            stringsList.add(stringInMethod.convertStringMethod(string));
        }
    }

    private void setMethodsList() {
        if (!stringsList.isEmpty()) {
            List<StringInMethod> tempList = new LinkedList<StringInMethod>(stringsList);
            setStrings(tempList);
            methodsList.add(new Methods(this));
            stringsList.clear();
            isReturned = false;
            this.argument = null;
            numberMethod++;
        }
    }


    private String convertArgument(String stringArguments) {

        String result = "(";
        stringArguments = stringArguments.replaceAll("^\\s+\\[Arguments\\]", "").replace("${", "").replace("}", "").trim();

        while (stringArguments.contains(" ")) {
            String string = stringArguments.substring(0, stringArguments.indexOf(" "));
            stringArguments = stringArguments.substring(stringArguments.indexOf(" "), stringArguments.length()).trim();
            result += String.format("%s%s,", typeOfVariable(string), string);
            if (!stringArguments.contains(" ")) {
                result += String.format(" %s%s) {", typeOfVariable(stringArguments), stringArguments);
            }
        }
        if (stringArguments.length() != 0 && result.length() == 1) {
            result += String.format("%s%s) {", typeOfVariable(stringArguments), stringArguments);
        }
        return result;
    }

    public String printMethod(List<Methods> methodList) {
        String result = "";
        result += " //#---Start Methods Section \n";
        for (Methods oneTest : methodList) {
            String meta = methodMeta(oneTest);
            result += meta + "\n";
            result += printStringInMethod(oneTest);
            result += "}\n\n";
        }
        result += " //#---End Methods Section \n";
        return result;
    }

    private String printStringInMethod(Methods methods) {
        stringInMethod = new StringInMethod();
        String result = "";
        result += stringInMethod.printStringsInMethod(methods.getStrings());
        return result;
    }

    private String methodMeta(Methods t) {
        String strRetun = "void";
        if (t.getReturned()) {
            strRetun = "String";
        }
        String printArgument = "";
        if(t.getArgument() == null || t.getArgument().length() == 0){
            printArgument = "() {";
        } else {
            printArgument = t.getArgument();
        }
        return "@Step\n" +
                "    public " + strRetun + " " + t.getName() + printArgument;

    }
}
