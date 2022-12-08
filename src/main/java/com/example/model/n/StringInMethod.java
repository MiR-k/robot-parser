package com.example.model.n;

import java.util.List;

/**
 * Created by Solit on 24.10.2016.
 */
public class StringInMethod {

    private String variable;

    private String methodString;

    private List<String> methodComment = null;

    private List<String> argumentOfMethod = null;

    public StringInMethod() {
    }

    public StringInMethod(String methodString) {
        this.methodString = methodString;
    }

    public StringInMethod(StringInMethod args) {
        this.methodString = args.getMethodString();
        this.argumentOfMethod = args.getArgumentOfMethod();
    }

    public List<String> getArgumentOfMethod() {
        return argumentOfMethod;
    }

    public void setArgumentOfMethod(List<String> argumentOfMethod) {
        this.argumentOfMethod = argumentOfMethod;
    }

    public String getMethodString() {
        return methodString;
    }

    public void setMethodString(String methodString) {
        this.methodString = builtInFormat(methodString);
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public List<String> getMethodComment() {
        return methodComment;
    }

    public void setMethodComment(List<String> methodComment) {
        this.methodComment = methodComment;
    }

    private String builtInFormat(String name) {
        if (name.contains("shouldBeEqual")) {
            name = "assertEquals";
        }
        if (name.contains("shouldNotBeEqual")) {
            name = "assertNotEquals";
        }
        if (name.contains("Sleep")) {
            name = "waitABit";
        }
        return name;
    }
}
