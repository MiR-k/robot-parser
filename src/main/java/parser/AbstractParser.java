package parser;

import org.apache.commons.lang3.text.WordUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static parser.ObjectType.Method;

/**
 * Created by Dev on 23.10.2016.
 */
public abstract class AbstractParser {

    static String AUTOR = "Ivan.Ivanov";
    static String FEATURES = "\"UFM\"";
    static String BASECLASS = "BaseTest";

    //    public abstract List<?> parse(List<String> fileInList);
    protected Map<ObjectType, List> objectsHashtable = new Hashtable<ObjectType, List>();

    //
    private String source;
    private String name;
    //

    public AbstractParser() {
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    protected String formatSource(String sting) {
        String fileName = sting.substring(sting.lastIndexOf("\\") + 1);
        fileName = fileName.replaceAll("\\d+_", "").replaceAll("_", " ").replaceAll(".*\\\\test\\\\", "").replaceAll(".robot", "");
        fileName = WordUtils.capitalize(fileName).replaceAll(" ", "");
        fileName = fileName.replaceAll("'", "").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("-", "");
        return fileName;
    }

    //need review
    @Deprecated
    private String fileName() {
        return this.getSource();
    }

    void convertToObjectsMap(List<String> fileInList) {
        ObjectType objectType = null;

        for (String oneString : fileInList) {
            if (oneString.length() == 0) continue;
            if (oneString.startsWith("*** ")) {
                if (oneString.startsWith("*** Variables ***")) {
                    objectType = ObjectType.Variable;
                } else if (oneString.startsWith("*** Keywords ***")) {
                    objectType = Method;
                } else if (oneString.startsWith("*** Test Cases ***") || oneString.startsWith("*** Testcases ***")) {
                    objectType = ObjectType.Test;
                }

            } else {
                if (objectType == null) continue;
                if (objectsHashtable.get(objectType) == null) {
                    objectsHashtable.put(objectType, new ArrayList<String>());
                }
                objectsHashtable.get(objectType).add(oneString);
            }
        }
    }

    //#section protected
    protected static String inNameNumberToEnd(String presentString) {

        Pattern pattern = Pattern.compile("^\\d*");
        Matcher matcher = pattern.matcher(presentString);
        int start = 0;
        if (Pattern.matches("^\\d+\\w*", presentString)) {
            while (matcher.find(start)) {
                String startString = presentString.substring(matcher.start(), matcher.end());
                String endString = presentString.substring(matcher.end(), presentString.length());
                start = matcher.end();
                presentString = endString + startString;
            }
        }
        return presentString;
    }

    protected String formatMethodName(String name) {
        if (name.contains("keywords")) {
            name = name.split("keywords")[1];
        }
        if (name.contains("ufm_control_functions")) {
            name = name.replaceAll("ufm_control_functions", "");
        }
        if (name.length() > 0) {
            name = name.replaceAll("\\.", " ").replaceAll("_", " ").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("-", "").replace("'", "");
            String methodName = WordUtils.capitalize(name).replaceAll(" ", "").replaceAll("'", "").replace("=", "Equally")
                    .replaceAll("<", "Less").replaceAll(">", "Greater").replaceAll(",", "");
            if (methodName.length() > 0) {
                return Character.toLowerCase(methodName.charAt(0)) + methodName.substring(1);
            }
        }
        return name;
    }

    protected Class typeOfVariable(String nameVariable) {
        String[] stringVariables = {
                "testId", "customerId", "serviceId", "subscriberId", "personalCustomerId", "type",
                "operationDate", "numGroup", "contractId", "secondContractId", "thirdContractId", "payId",
                "amount", "virtId", "vrtp", "vrct", "deleted", "end_date", "start_date", "secondVirtId",
                "VirtId", "sysdate", "deldate", "cbRTPL_ID", "ratePlanId", "prefixExternalPhone", "CB", "prefix",
                "MSISDN", "msisdn", "balance", "spent", "payments", "casId", "СasId", "SUBS_ID", "newRatePlanId",
                "newCustomerId", "timeBeforeUpdate", "timeAfterUpdate", "lastChangeDate", "Threshold", "subsCount"
        };
        String[] intVariable = {
                "stepId", "step", "defaultSetId", "defaultSet", "setId", "sanctionId", "UFM_ID", "UFM_INSTANCE_ONE"
        };
        String[] byteVariable = {
                "res"
        };
        for (String item : stringVariables) {
            if (nameVariable.contains(item)) {
                return String.class;
            }
        }
        for (String item : byteVariable) {
            if (nameVariable.contains(item)) {
                return byte.class;
            }
        }
        for (String item : intVariable) {
            if (nameVariable.contains(item)) {
                return Integer.class;
            }
        }
        if (nameVariable.contains("List")) {
            return List.class;
        }
        if (nameVariable.contains("Date")) {
            return Date.class;
        }
        return String.class;
    }


    public static String convertListToString(List inputList) {
        return inputList.toString().replaceAll("(^\\[)|(]$)", "");
    }

    public static String convertListToString(String inputListString) {
        return inputListString.replaceAll("(^\\[)|(]$)", "");
    }

}
