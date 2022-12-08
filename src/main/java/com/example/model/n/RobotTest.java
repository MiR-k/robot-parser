package com.example.model.n;

import org.apache.commons.text.StringSubstitutor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.io.FileUtils.readFileToString;

/**
 * Created by Solit on 24.10.2016.
 */
public class RobotTest extends RobotMethod {

    final String METHODMETAFILE = "/com/example/model/TestMetaInformation.tpl";

    protected int testId;
    private String description;
    public String Severity = "NORMAL";

    private boolean isNewTest = true;
    List<RobotTest> robotTestList = new LinkedList<RobotTest>();

    public RobotTest() {
    }

    public RobotTest(RobotTest robotTest) {
        this.testId = robotTest.getTestId();
        this.description = robotTest.getDescription();
        this.body = robotTest.getMethodBody();
        this.methodName = robotTest.getName();
    }

    public MethodBody getMethodBody() {
        return this.body;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }


    public static String printTest(List<RobotTest> localTestList) {
        if (localTestList.isEmpty()) {
            return "";
        }
        StringBuilder stringOfTest = new StringBuilder();
        stringOfTest.append("\\s{4}//#---Start Tests Section \n");
        for (RobotTest oneTest : localTestList) {
            stringOfTest.append(oneTest.toString());
        }
        stringOfTest.append("\\s{4}//#---End Test Section \n");
        return stringOfTest.toString();
    }

    public String toString() {
        StringBuilder stringOfTest = new StringBuilder();
        stringOfTest.append(buildMetaInformation());
        stringOfTest.append(buildMethodBody());
        return stringOfTest.toString();
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

    protected String buildMetaInformation() {
        Map<String, Object> valuesMap = new HashMap();
        valuesMap.put("testId", getTestId());
        valuesMap.put("methodName", getName());
        valuesMap.put("description", getDescription());
        return getFromFile(valuesMap);
    }
}
