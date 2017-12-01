package InputOutputFileFunctions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileController {

    public static void main(String[] args) throws IOException {

        FileController reader = new FileController();
        String file = reader.filePathConstructor("input.txt");
        System.out.println(reader.fileReader(file).get(2));
    }

    public String filePathConstructor (String filenameWithExtention) {
        String file = new File ("C:\\Users\\Tarvo\\IdeaProjects\\"+filenameWithExtention).toString();
        return file;
    }

    public List<String> fileReader (String file) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(file));
        return lines;
    }

}
