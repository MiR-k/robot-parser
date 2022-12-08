package com.example.model.n2.n;

public class Variable {

    private static String PREF_VARIABLE = "protected static %s %s = \"%s\"";

    private Class variableType = String.class;
    private String variableName;
    private String variableValue;
    private String comment;

    public Variable() {
    }

    public Variable(String variable) {
        this.variableName = variable;
    }

    public Variable(Variable variable) {
        this.variableType = variable.getVariableType();
        this.variableName = variable.getVariableName();
        this.variableValue = variable.getVariableValue();
        this.comment = variable.getComment();
    }

    public Class getVariableType() {
        return this.variableType;
    }

    public String getVariableValue() {
        return this.variableValue;
    }

    public String getVariableName() {
        return this.variableName;
    }

    public String getComment() {
        return this.comment;
    }

    public void setVariableValue(String variableValue) {
        this.variableValue = variableValue;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public void setVariableType(Class type) {
        this.variableType = type;
    }

    @Override
    public String toString() {
        if (variableType.equals(String.class)) {
            return String.format(PREF_VARIABLE, this.variableType.getSimpleName(), this.variableName, this.variableValue);
        } else {
            return String.format(PREF_VARIABLE, this.variableType.getSimpleName(), this.variableName, this.variableValue).replace("\"", "");
        }
    }
}