package parser;

import com.example.model.n.RobotVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Dev on 23.10.2016.
 */
public class RobotVariableParser extends RobotSuiteParser {

    private static String PREF_VARIABLE = "protected static String %s = ";

    public List<RobotVariable> parse(List<String> stringVariables) {
        List<RobotVariable> lVariables = new ArrayList<>();

        String comment = null;
        for (String stringVar : stringVariables) {
            if (stringVar.startsWith("#")) {
                comment = stringVar.replaceAll("^#", " //");
            } else {
                lVariables.add(dividedVariableValue(stringVar, comment));
                comment = null;
            }
        }
        return lVariables;
    }

    public String printLocalVariable(List<RobotVariable> localVarList) {

        if (localVarList == null) {
            return "";
        }
        String result = "";
        result += " //Start Local Variable Section \n";
        for (RobotVariable locVar : localVarList) {
            result += locVar.getNameVariable();
            if (locVar.getValueVariable() != null) {
                result += locVar.getValueVariable();
            }
            result += "\n";
        }
        result += " //End Local Variable Section \n";
        return result;
    }

    //---------- private method
    private static RobotVariable dividedVariableValue(String presentString, String comment) {
        RobotVariable variable = new RobotVariable();

        presentString = presentString.trim();
        Pattern pattern = Pattern.compile("^\\$\\{.*\\}=?");
        Matcher matcher = pattern.matcher(presentString);
        int start = 0;
        String nameVariable = "";
        String valueVariable = "";
        while (matcher.find(start)) {
            nameVariable = presentString.replaceAll("\\s+", "").substring(matcher.start(), matcher.end()).replace("${", "").replace("}=", "").replace("}", "");
            nameVariable = inNameNumberToEnd(nameVariable);
            valueVariable = presentString.substring(matcher.end(), presentString.length()).trim().replaceAll("\\s+", " ");
            start = matcher.end();
        }
        variable.setNameVariable(String.format(PREF_VARIABLE, nameVariable));
        variable.setValueVariable("\"" + valueVariable + "\";");
        variable.setComment(comment);
        return variable;
    }
}
