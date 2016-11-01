import org.apache.commons.lang3.text.WordUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dev on 26.10.2016.
 */
public class ReaderWriter {

    static List<String> fileInList = new ArrayList<String>();

    private String pathToSaveFile = "target/tests";

    public List<String> reader(String pathToFile) {

        File file = new File(pathToFile);
        try {
            BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
            try {
                String s;
                while ((s = in.readLine()) != null) {
                    fileInList.add(s.replace("\t","   "));
                }
            } finally {
                in.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileInList;
    }

    public void writer(String writeString, String pathToFile) throws IOException {

        BufferedWriter writer;

            File file = new File(pathToSaveFile + prepareSuitePath(pathToFile));
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
            }
            String fileName = file.getName();
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(writeString);
            writer.close();
            writer = null;
    }

    private static String prepareSuitePath(String source) {
        String fileName = source.substring(source.lastIndexOf("\\") + 1);
        fileName = fileName.replaceAll("\\d+_", "").replaceAll("_", " ").replaceAll(".*\\\\test\\\\", "").replaceAll(".txt", ".java").replaceAll(".robot", ".java");
        fileName = WordUtils.capitalize(fileName).replaceAll(" ", "");
        fileName = fileName.replaceAll("'", "")
                .replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("-", "");
        String path = source.substring(0, source.lastIndexOf("\\") + 1).replaceAll("^\\w\\:","");
        path = path.replaceAll("\\d+_", "");
        return path + fileName;
    }

}
