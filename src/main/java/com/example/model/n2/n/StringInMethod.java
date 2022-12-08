package com.example.model.n2.n;

import java.util.ArrayList;
import java.util.List;

import static parser.AbstractParser.convertListToString;

public class StringInMethod {

    private String variable;

    private String methodString;

    private List<String> commentsOfMethod = new ArrayList<>();

    private List<Object> argumentsOfMethod = new ArrayList<>();

    public StringInMethod() {
    }

    public StringInMethod(String methodString) {
        this.methodString = methodString;
    }

    public StringInMethod(StringInMethod args) {
        this.variable = args.getVariable();
        this.methodString = args.getMethodString();
        this.argumentsOfMethod = args.getArgumentsOfMethod();
        this.commentsOfMethod = args.getCommentsOfMethod();
    }

    public List<Object> getArgumentsOfMethod() {
        return argumentsOfMethod;
    }

    public String getMethodString() {
        return methodString;
    }

    public List<String> getCommentsOfMethod() {
        return commentsOfMethod;
    }

    public String getVariable() {
        return variable;
    }

    public void setArgumentsOfMethod(List<?> argumentsOfMethod) {
        this.argumentsOfMethod.addAll(argumentsOfMethod);
    }

    public void setMethodString(String methodString) {
        this.methodString = methodString;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public void setCommentsOfMethod(List<String> commentsOfMethod) {
        this.commentsOfMethod.addAll(commentsOfMethod);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (commentsOfMethod != null && !commentsOfMethod.isEmpty()) {
            commentsOfMethod.stream().forEach(it ->
                    builder.append("//" + it)
            );
        }
        if (variable != null) {
            builder.append(variable + '=');
        }
        if (methodString != null) {
            builder.append(methodString);
        }
        if (this.argumentsOfMethod != null) {
            builder.append('(' + convertListToString(this.argumentsOfMethod) + ')');
        } else {
            builder.append("()");
        }
        return builder.toString();
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
