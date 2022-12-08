package com.example.model.n;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Dev on 23.10.2016.
 */
public class RobotVariable {

    private static String PREF_VARIABLE = "protected static String %s = ";

    private String nameVariable;
    private String valueVariable;
    private String comment;

    public RobotVariable(){
    }

    public RobotVariable(String variable){
        this.nameVariable = variable;
    }

    public String getValueVariable() {
        return valueVariable;
    }

    public void setValueVariable(String valueVariable) {
        this.valueVariable = valueVariable;
    }

    public String getNameVariable(){
        return this.nameVariable;
    }

    public void setNameVariable(String nameVariable){
        this.nameVariable = nameVariable;
    }

    public void setComment(String commentValue) {
        this.comment = commentValue;
    }

    public List<RobotVariable> convertLocalVar(List<String> stringVariables) {
        List<RobotVariable> lVariables = new ArrayList<RobotVariable>();

        if (stringVariables == null) return lVariables;
        for (String stringVar : stringVariables) {
            if (stringVar.contains("#")) {
                String temp = stringVar.replaceAll("^#"," //");
                lVariables.add(new RobotVariable(temp));
            } else {
                lVariables.add(dividedVariableValue(stringVar));
            }
        }
        return lVariables;
    }

    public static String printLocalVariable(List<RobotVariable> localVarList) {

        if (localVarList.isEmpty()) {
            return "";
        }
        String result = "";
        result += " //Start Local Variable Section\n";
        for (RobotVariable locVar : localVarList) {
            result += locVar.toString();
        }
        result += " //End Local Variable Section\n";
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
    private RobotVariable dividedVariableValue(String presentString) {
        RobotVariable variable = new RobotVariable();

        presentString = presentString.trim();
        Pattern pattern = Pattern.compile("^\\$\\{.*\\}=?");
        Matcher matcher = pattern.matcher(presentString);
        int start = 0;
        String nameVariable = "";
        String valueVariable = "";
        while (matcher.find(start)) {
            nameVariable = presentString.replaceAll("\\s+","").substring(matcher.start(), matcher.end()).replace("${","").replace("}=", "").replace("}", "");
//            nameVariable = inNameNumberToEnd(nameVariable);
            valueVariable = presentString.substring(matcher.end(), presentString.length()).trim().replaceAll("\\s+"," ");
            start = matcher.end();
        }
        variable.setNameVariable(String.format(PREF_VARIABLE, nameVariable));
        variable.setValueVariable("\"" + valueVariable + "\";");
        return variable;
    }
}
