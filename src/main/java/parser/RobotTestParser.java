package parser;

import com.example.model.StringInMethod;
import com.example.model.Tests;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static parser.RobotConstants.*;

/**
 * Created by Solit on 24.10.2016.
 */
public class RobotTestParser extends RobotMethodParser {

    private StringInMethod stringInMethod;

    private boolean isNewTest = true;
    List<Tests> robotTests = new LinkedList<>();
    private Tests oneTest;

    public RobotTestParser() {
    }

//    public List<RobotTest> convertToTestlist(List<String> strTestsList) {
//        List<RobotTest> testList = new ArrayList<RobotTest>();
//
//        for (String stringVar : strTestsList) {
//            String temp;
//            if (stringVar.contains("#")) {
//                temp = stringVar.replace("#-", " //-").replace("# ", " //");
//            } else {
//                stringVar = stringVar.replaceAll("[\\s]{2,}", "");
//                temp = stringVar.replace("${", "private static String ").replace("}", " = \"");
//                temp += "\";";
//            }
//            testList.add(new RobotTest(temp));
//        }
//        return testList;
//    }

    public List parse(List<String> strTestsList) {
        if (strTestsList == null) {
            return new ArrayList<>();
        }
        for (String oneString : strTestsList) {
            workWithString(oneString);
        }
        robotTests.add(oneTest);
        return robotTests;
    }

    private void workWithString(String string) {
        oneTest = new Tests();
        if (Pattern.matches(regexNameTest, string)) {
            if (isNewTest) {
                isNewTest = false;
            } else {
                if (getName(string).length() != 0 || !robotTests.isEmpty()) {
                    robotTests.add(oneTest);
                }
            }
            oneTest.setName(formatMethodName(getName(string)));
            oneTest.setId(getId(string));
        } else if (Pattern.matches(regexDescription, string)) {
            oneTest.setDescrition(formatDescription(string));
        } else {
            oneTest.getStrings().add(new StringInMethod().convertStringMethod(string));
        }
    }

    // private methods

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

    private static String formatDescription(String name) {
        if (name.length() > 0) {
            name = name.replaceAll("[\\(\\)\\-'(\\[Documentation\\])]", "").trim().replace("}", ") {")
                    .replaceAll("\\.", " ").replaceAll("_", " ").replace("\"", "\\\"");
        }
        return name;
    }

}
