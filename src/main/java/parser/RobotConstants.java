package parser;

public class RobotConstants {

    final static String REGMETHOD = "(^[A-Z]{1}(\\w|\\d)*(\\s(\\'|\\w|\\d)*)*$)";
    final static String regexNameTest = "(^(С|C)\\d+\\:.*$)|(Configure Ufm Instance One)";
    final static String regexIdTest = "(^(С|C)\\d*\\:)";
    final static String regexArgument = "(^\\s*\\[Arguments\\](.*)$)";
    final static String regexComent = "(^((\\s*\\#\\s)|(\\s*\\#-))).*";
    final static String regexDescription = "(^\\s*\\[Documentation\\](.*)$)";
    final static String regexFollowString = "(^\\s*(\\.)+)";

    public final static String VOIDTYPE = "void";
    public final static String STRINGTYPE = "String";

    private final static String[] stringVariables = {
            "testId", "customerId", "serviceId", "subscriberId", "personalCustomerId", "type",
            "operationDate", "numGroup", "contractId", "secondContractId", "thirdContractId", "payId",
            "amount", "virtId", "vrtp", "vrct", "deleted", "end_date", "start_date", "secondVirtId",
            "VirtId", "sysdate", "deldate", "cbRTPL_ID", "ratePlanId", "prefixExternalPhone", "CB", "prefix",
            "MSISDN", "msisdn", "balance", "spent", "payments", "casId", "СasId", "SUBS_ID", "newRatePlanId",
            "newCustomerId", "timeBeforeUpdate", "timeAfterUpdate", "lastChangeDate", "Threshold", "subsCount"
    };
    private final static String[] intVariable = {
            "stepId", "step", "defaultSetId", "defaultSet", "setId", "sanctionId", "UFM_ID", "UFM_INSTANCE_ONE"
    };
    private final static String[] byteVariable = {
            "res"
    };

    static String typeOfVariable(String nameVariable) {
        for (String item : stringVariables) {
            if (nameVariable.contains(item)) {
                return "String ";
            }
        }
        for (String item : byteVariable) {
            if (nameVariable.contains(item)) {
                return "byte ";
            }
        }
        for (String item : intVariable) {
            if (nameVariable.contains(item)) {
                return "int ";
            }
        }
        if (nameVariable.contains("List")) {
            return nameVariable;
        }
        if(nameVariable.contains("Date")){
            return "Date ";
        }
        return "String ";
    }
}
