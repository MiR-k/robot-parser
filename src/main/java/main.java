import model.RobotSuite;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * Created by Dev on 26.10.2016.
 */
public class main {

    static List<String> fileInList = new ArrayList<String>();

    static ReaderWriter readerWriter = new ReaderWriter();
    static RobotSuite robotSuite = new RobotSuite();

    static String AUTOR = "Anton.Tatur";
    static String FEATURES = "\"UFM BIS CFG\"";
    static String BASECLASS = "BaseTest";

    private static String pathToFile = "D:\\WS\\robot-parser\\src\\main\\resources\\test.txt";

    public static void main(String[] args) {

        robotSuite.setSource(pathToFile);

        fileInList = readerWriter.reader(pathToFile);
        robotSuite.setDescriptionForSuite(AUTOR, FEATURES, BASECLASS);
        robotSuite.convertToRobotSuite(fileInList);

        try {
            readerWriter.writer(robotSuite.printRobotSuite(), pathToFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
