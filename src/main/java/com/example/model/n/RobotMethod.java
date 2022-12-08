package com.example.model.n;

import com.example.model.Argument;
import org.apache.commons.text.StringSubstitutor;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.apache.commons.io.FileUtils.readFileToString;
import static parser.AbstractParser.convertListToString;
import static parser.RobotConstants.VOIDTYPE;

/**
 * Created by Dev on 23.10.2016.
 */
public class RobotMethod {

    final String METHODMETAFILE = "/com/example/model/StepMetaInformation.tpl";

    String returnType = VOIDTYPE;
    String methodName;
    String comment;
    List<Argument> arguments = new LinkedList<>();
    MethodBody body = new MethodBody();

    public RobotMethod() {
    }

    public RobotMethod(String returnType) {
        this.returnType = returnType;
    }

    public RobotMethod(RobotMethod robotMethod) {
        this.methodName = robotMethod.getName();
        this.arguments = robotMethod.getArguments();
        this.body = robotMethod.getBody();
        this.returnType = robotMethod.getReturnType();
    }

    public RobotMethod(String methodName, List<Argument> arguments, MethodBody body) {
        this.methodName = methodName;
        this.arguments = arguments;
        this.body = body;
    }

    public String getReturnType() {
        return this.returnType;
    }

    public String getName() {
        return this.methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public MethodBody getBody() {
        return this.body;
    }
    public void setBody(MethodBody body) {
        this.body = body;
    }

    public List<Argument> getArguments() {
        return arguments;
    }

    public String toString() {
        return buildMetaInformation() + buildMethodBody();
    }

    protected String buildMethodBody() {
        return getBody().toString();
    }

    protected String buildMetaInformation() {
        Map<String, Object> valuesMap = new HashMap<>();

        valuesMap.put("returnType", getReturnType());
        valuesMap.put("methodName", getName());
        valuesMap.put("printArgument", getArguments() == null ? "" : convertListToString(getArguments()));
        return getFromFile(valuesMap);
    }

    String getFromFile(Map valuesMap) {
        try {
            return new StringSubstitutor(valuesMap).replace(readFileToString(new File(getClass().getResource(METHODMETAFILE).getFile())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
