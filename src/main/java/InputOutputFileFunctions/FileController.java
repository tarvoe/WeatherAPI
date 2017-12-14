package InputOutputFileFunctions;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileController {

    public static void main(String[] args) throws IOException {

    }

    public String filePathConstructor (String filenameWithExtention) {
        return new File ("C:\\Users\\Tarvo\\IdeaProjects\\"+filenameWithExtention).toString();
    }

    public List<String> fileReader (String filePath) throws IOException {
        return Files.readAllLines(Paths.get(filePath));
    }

    public void fileWriter(List<String> content, String cityName ) throws IOException {
        String directory = "C:\\Users\\Tarvo\\IdeaProjects\\";
        FileWriter fw = new FileWriter(new File(directory, cityName+".txt"));
        String newLine = System.getProperty("line.separator");
        fw.write("The maximum and minimum temperatures in " + cityName + " for the next three days are" + newLine);
        for (String aContent : content) {
            fw.write(aContent + newLine);
        }
        fw.close();
    }
}
