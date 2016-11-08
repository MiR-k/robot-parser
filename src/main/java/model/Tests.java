package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Solit on 24.10.2016.
 */
public class Tests extends RobotSuite {

    private StringInMethod stringInMethod;

    private int id;
    private String name;
    private String descrition;
    private List<StringInMethod> strings = null;

    private boolean isNewTest = true;
    private List<StringInMethod> stringsList = new LinkedList<StringInMethod>();
    List<Tests> testsList = new LinkedList<Tests>();

    private int numberTest = 0;

    public Tests() {
    }

    public Tests(String name) {
        this.name = name;
    }

    public Tests(Tests tests) {
        this.name = tests.getName();
        this.descrition = tests.getDescrition();
        this.strings = tests.getStrings();
        this.id = tests.getId();
    }

    public Tests(String name, String argument, List<StringInMethod> strings) {
        this.name = name;
        this.descrition = argument;
        this.strings = strings;
    }

    public Tests(int id, String name) {
        this.id = id;
        this.name = name;
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


    public void setDescrition(String descrition) {
        this.descrition = descrition;
    }

    public String getDescrition() {
        return descrition;
    }

    public void setId(int Id) {
        this.id = Id;
    }

    public int getId() {
        return id;
    }

    public String printTest(List<Tests> localTestList) {
        String result = "";
        result += " //#---Start Tests Section \n";
        for (Tests oneTest : localTestList) {
            String meta = testMeta(oneTest);
            result += meta + "\n";
            result += printStringInMethod(oneTest);
            result += "}\n\n";
        }
        result += " //#---End Test Section \n";
        return result;
    }

    public List<Tests> convertToTestlist(List<String> strTestsList) {
        List<Tests> testList = new ArrayList<Tests>();

        for (String stringVar : strTestsList) {
            String temp;
            if (stringVar.contains("#")) {
                temp = stringVar.replace("#-", " //-").replace("# ", " //");
            } else {
                stringVar = stringVar.replaceAll("[\\s]{2,}", "");
                temp = stringVar.replace("${", "private static String ").replace("}", " = \"");
                temp += "\";";
            }
            testList.add(new Tests(temp));
        }
        return testList;
    }

    public List<Tests> parseToTest(List<String> strTestsList) {

        for (String oneString : strTestsList) {
            workWithString(oneString);
        }
        setTestList();
        return testsList;
    }

    private void workWithString(String string) {
        StringInMethod stringInMethod = new StringInMethod();

        if (Pattern.matches(regexNameTest, string)) {
            if (isNewTest) {
                isNewTest = false;
            } else {
                if (getName().length() != 0 || !testsList.isEmpty()) {
                    setTestList();
                }
            }
            setName(formatMethodName(getName(string)));
            setId(getId(string));
        } else if (Pattern.matches(regexDescription, string)) {
            setDescrition(formatDescription(string));
        } else {
            stringsList.add(stringInMethod.convertStringMethod(string));
        }
    }


    private void setTestList() {
        if (!stringsList.isEmpty()) {
            List<StringInMethod> tempList = new LinkedList<StringInMethod>(stringsList);
            setStrings(tempList);
            testsList.add(new Tests(this));
            stringsList.clear();
            numberTest++;
        }
    }

    public String testsToString(List<Tests> testsOfList) {


        return null;
    }

    // private methods

    private String printStringInMethod(Tests t) {
        stringInMethod = new StringInMethod();
        String result = "";
        result += stringInMethod.printStringsInMethod(t.getStrings());
        return result;
    }

    private String testMeta(Tests t) {
        String description = "";
        if (t.getDescrition() != null) {
            description = t.getDescrition();
        } else {
            description = getName();
        }
        return "@Test\n" +
                "    @TestCaseId(\"C" + t.getId() + "\")\n" +
                "    @Severity(SeverityLevel.NORMAL)\n" +
                "    @Title(\"" + t.getName() + "\")\n" +
                "    @Description(\"" + description + "\")\n" +
                "    public void " + t.getName() + "(){";
    }


    private int getId(String oneString) {
        Pattern pattern = Pattern.compile("^(С|C)\\d+");
        Matcher matcher = pattern.matcher(oneString);
        int start = 0;
        int result = 0;
        if (!oneString.contains("Configure Ufm Instance One")) {
            while (matcher.find(start)) {
                String value = oneString.substring(matcher.start() + 1, matcher.end());
                result = Integer.parseInt(value);
                start = matcher.end();
            }
        } else {
            result = 0;
        }
        return result;
    }

    private String getName(String oneString) {
        String result = "";
        result = oneString.replaceAll(regexIdTest, "").trim();
        return result;
    }

}
