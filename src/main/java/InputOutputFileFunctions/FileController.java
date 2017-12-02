package InputOutputFileFunctions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileController {

    public static void main(String[] args) throws IOException {

        FileController reader = new FileController();
        reader.createNewTxtFile("Tallinn3");
    }

    public String filePathConstructor (String filenameWithExtention) {
        String file = new File ("C:\\Users\\Tarvo\\IdeaProjects\\"+filenameWithExtention).toString();
        return file;
    }

    public List<String> fileReader (String file) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(file));
        return lines;
    }

    public void createNewTxtFile (String cityName) throws IOException {
        try {
            File file = new File("C:\\Users\\Tarvo\\IdeaProjects\\"+cityName+".txt");
            boolean fvar =file.createNewFile();
            if (fvar){
                System.out.println("File has been created successfully");
            }
            else{
                System.out.println("File already present at the specified location");
            }
        } catch (IOException exception) {
            System.out.println("Something went wrong");
            exception.printStackTrace();
        }
    }

    public  void fileWriter(List<String> content, String cityName ) throws IOException {
        String directory = "C:\\Users\\Tarvo\\IdeaProjects\\";
        FileWriter fw = new FileWriter(new File(directory, cityName+".txt"));
        String newLine = System.getProperty("line.separator");
        fw.write("The maximum and minimum temperatures in " + cityName + " for the next three days are" + newLine);
        for (int i = 0; i < content.size(); i++) {
            fw.write(content.get(i) + newLine);
        }

        fw.close();
    }
}
