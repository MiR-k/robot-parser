package parser;

import com.google.common.base.Strings;
import com.example.model.Argument;
import com.example.model.n.StringInMethod;
import com.example.model.n.RobotMethod;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import static parser.RobotConstants.*;

/**
 * Created by Dev on 23.10.2016.
 */
public class RobotMethodParser extends RobotSuiteParser {

    private int numberMethod = 0;
    private boolean isNewMethod = true;
    private List<StringInMethodParser> stringsList = new LinkedList<>();
    List<RobotMethod> robotMethodList = new LinkedList<RobotMethod>();
    private RobotMethod oneMethod;

    public RobotMethodParser() {
    }

    public List<RobotMethod> parse(List<String> methodsList) {
        if (methodsList == null) {
            return new ArrayList<>();
        }
        oneMethod = new RobotMethod();
        for (String oneString : methodsList) {
            if (oneString.contains("[Return]")) {
                oneMethod.setReturnType(STRINGTYPE);
            }
            workWithString(oneString);
        }
        setMethodsList();
        return robotMethodList;
    }

    private void workWithString(String string) {
        StringInMethod stringInMethod = new StringInMethod();

        if (string.startsWith("#")) {
            oneMethod.setComment(string.replaceAll("^(\\s+)?#", "//"));
            return;
        }
        if (Pattern.matches(REGMETHOD, string)) {
            if (isNewMethod) {
                isNewMethod = false;
            } else {
                if (!Strings.isNullOrEmpty(oneMethod.getName()) || !robotMethodList.isEmpty()) {
                    setMethodsList();
                }
            }
            oneMethod.setMethodName(formatMethodName(string));
        } else if (Pattern.matches(regexArgument, string)) {
            oneMethod.getArguments().addAll(convertArgument(string));
        } else {
            stringsList.add(new StringInMethodParser().convertStringMethod(string));
        }
    }

    private void setMethodsList() {
//        oneMethod.getBody().getStringsList().addAll(stringsList);
        oneMethod.getBody().toString();
        stringsList.clear();
        robotMethodList.add(oneMethod);
        oneMethod = new RobotMethod();
        isNewMethod = true;
        numberMethod++;
    }

    private List<Argument> convertArgument(String stringArguments) {

        List<Argument> result = new ArrayList<>();
        stringArguments = stringArguments.replaceAll("(^\\s+\\[Arguments\\])|(\\$\\{|})", "").replaceAll("", "").trim();
        for (String argument : stringArguments.split("\\s+")) {
            result.add(new Argument(typeOfVariable(argument), argument));
        }
        return result;
    }

    public static String printMethod(List<RobotMethod> methodList) {
        String result = "";
        if (methodList.isEmpty()) {
            return result;
        }
        result += " //#---Start Methods Section \n";
        for (RobotMethod oneTest : methodList) {
            result += oneTest.toString();
        }
        result += " //#---End Methods Section \n";
        return result;
    }

}
