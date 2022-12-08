package com.example.model.n2.n;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.util.ArrayList;
import java.util.List;

import static parser.AbstractParser.convertListToString;

public class Method {

    private final String METHODMETAFILE = "/templates/MethodTemplateFile.twig";
    private Class returnType = void.class;
    private String methodName = this.getClass().getSimpleName().toLowerCase();
    private List<String> comments = new ArrayList<>();
    private List<Argument> argumentsLists = new ArrayList<>();
    private List<StringInMethod> body = new ArrayList<>();

    Method() {
    }

    public Method(Method method) {
        this.setReturnType(method.getReturnType());
        this.setMethodName(method.getMethodName());
        this.setComments(method.getComments());
        this.setArgumentsLists(method.getArguments());
    }

    public Class getReturnType() {
        return this.returnType;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public List<String> getComments() {
        return this.comments;
    }

    public List<Argument> getArguments() {
        return this.argumentsLists;
    }

    public List<StringInMethod> getBody() {
        return this.body;
    }

    public void setReturnType(Class returnType) {
        this.returnType = returnType;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setComments(List<String> comments) {
        this.comments.addAll(comments);
    }

    public void setArgumentsLists(List<Argument> argumentsLists) {
        this.argumentsLists.addAll(argumentsLists);
    }

    public void setBody(List<StringInMethod> body) {
        this.body.addAll(body);
    }

    @Override
    public String toString() {
        return generateClassFromTemplate(METHODMETAFILE);
    }

    public String generateClassFromTemplate(String templateName) {
        JtwigModel model = new JtwigModel().with("object", this);
        model.with("printArgument", convertListToString(this.argumentsLists));
        return JtwigTemplate.classpathTemplate(templateName).render(model);
    }

}
