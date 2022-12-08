package parser;

import com.example.model.LocalVariable;
import com.example.model.n.RobotVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Dev on 23.10.2016.
 */
public class LocalVariableParser extends AbstractParser {

    private static String PREF_VARIABLE = "protected static String %s = ";

    private String nameVariable;

    private String valueVariable;

    private String comment;

    public LocalVariableParser() {
    }

    public LocalVariableParser(String variable) {
        this.nameVariable = variable;
    }

    public String getValueVariable() {
        return valueVariable;
    }

    public void setValueVariable(String valueVariable) {
        this.valueVariable = valueVariable;
    }

    public String getNameVariable() {
        return this.nameVariable;
    }

    public void setNameVariable(String nameVariable) {
        this.nameVariable = nameVariable;
    }

    public void setComment(String commentValue) {
        this.comment = commentValue;
    }

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

    public static String printLocalVariable(List<LocalVariable> localVarList) {

        String result = "";
        result += "//Start Local Variable Section\n";
        for (LocalVariable locVar : localVarList) {
            result += locVar.toString();
        }
        result += "//End Local Variable Section\n";
        return result;
    }

    public String toString() {

        StringBuilder builder = new StringBuilder();
        if (comment != null) {
            builder.append('\n' + this.comment + '\n');
        }
        builder.append(this.nameVariable);
        builder.append(this.valueVariable + '\n');
        return builder.toString();
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
