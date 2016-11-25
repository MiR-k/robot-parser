package model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Dev on 23.10.2016.
 */
public class LocalVariable  extends RobotSuite{

    private static String PREF_VARIABLE = "protected static String %s = ";

    private String nameVariable;

    private String valueVariable;

    public LocalVariable(){
    }

    public LocalVariable(String variable){
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

    public List<LocalVariable> convertLocalVar(List<String> stringVariables) {
        List<LocalVariable> lVariables = new ArrayList<LocalVariable>();

        for (String stringVar : stringVariables) {
            if (stringVar.contains("#")) {
                String temp = stringVar.replaceAll("^#"," //");
                lVariables.add(new LocalVariable(temp));
            } else {
                lVariables.add(dividedVariableValue(stringVar));
            }
        }
        return lVariables;
    }

    public String printLocalVariable(List<LocalVariable> localVarList) {

        if (localVarList == null) {
            return "";
        }
        String result = "";
        result += " //Start Local Variable Section \n";
        for (LocalVariable locVar : localVarList) {
            result += locVar.getNameVariable();
            if (locVar.getValueVariable() != null) {
                result += locVar.getValueVariable();
            }
            result +="\n";
        }
        result += " //End Local Variable Section \n";
        return result;
    }

    //---------- private method
    private LocalVariable dividedVariableValue(String presentString) {
        LocalVariable variable = new LocalVariable();

        presentString = presentString.trim();
        Pattern pattern = Pattern.compile("^\\$\\{.*\\}=?");
        Matcher matcher = pattern.matcher(presentString);
        int start = 0;
        String nameVariable = "";
        String valueVariable = "";
        while (matcher.find(start)) {
            nameVariable = presentString.replaceAll("\\s+","").substring(matcher.start(), matcher.end()).replace("${","").replace("}=", "").replace("}", "");
            nameVariable = inNameNumberToEnd(nameVariable);
            valueVariable = presentString.substring(matcher.end(), presentString.length()).trim().replaceAll("\\s+"," ");
            start = matcher.end();
        }
        variable.setNameVariable(String.format(PREF_VARIABLE, nameVariable));
        variable.setValueVariable("\"" + valueVariable + "\";");
        return variable;
    }
}
