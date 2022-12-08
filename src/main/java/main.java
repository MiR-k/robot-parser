//import model.n.RobotSuite;

import parser.FreemarkerProcessor;
import parser.RobotSuiteParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

/**
 * Created by Dev on 26.10.2016.
 */
public class main {

    static List<String> fileInList = new ArrayList<String>();

    static ReaderWriter readerWriter = new ReaderWriter();
    static RobotSuiteParser robotSuite = new RobotSuiteParser();

    static String AUTOR = "Anton.Tatur";
    static String FEATURES = "\"UFM BIS CFG\"";
    static String BASECLASS = "BaseTest";
    static FreemarkerProcessor processor = new FreemarkerProcessor(main.class, "/templates/");

    private static String pathToFile = "C:\\WorkSpace\\Git\\robot-parser\\src\\main\\resources\\test.txt";

    public static void main(String[] args) throws IOException {
        String object = "{\"user\": {\"name\": \"antn\" }}";
        Configuration conf = Configuration.builder().jsonProvider(new GsonJsonProvider())
                .options(Option.SUPPRESS_EXCEPTIONS).build();
        DocumentContext context = JsonPath.using(conf).parse(object);
        String path = "$.user.sureName1.sureName2.sureName3";
        String putElement = "Name3";
//        JsonElement element = context.read(path, JsonElement.class);
//        if (element == null) {
//            for (String key : path.split("\\.")) {
//                if (key.equals("$")) continue;
//                element = context.read(path.substring(0, path.indexOf(key) + key.length()), JsonElement.class);
//                if (element == null) {
//                    context.put("$.user.sureName5.sureName4", "key", new JsonObject());
//                }
//            }
//        }
//        context.set(path, putElement);
//        context.json();
        Collection ordered = Arrays.asList("a1", "a2", "a2", "a3", "a1", "a2", "a2");
        System.out.println(ordered.stream().collect(Collectors.groupingBy("a1"::equals)));
    }

}
