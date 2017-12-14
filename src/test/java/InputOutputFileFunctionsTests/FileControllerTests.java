package InputOutputFileFunctionsTests;

import InputOutputFileFunctions.FileController;
import org.junit.Test;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FileControllerTests {

    private FileController fileController = new FileController();
    @Test
    public void didItReturnACorrectFilePath(){
        String fileName = "input.txt";
        final String file = fileController.filePathConstructor(fileName);
        assertEquals("C:\\Users\\Tarvo\\IdeaProjects\\"+ fileName, file);
    }

    @Test
    public void didItReadAFile () throws IOException {
        String fileLocation = "C:\\Users\\Tarvo\\IdeaProjects\\testDidItReadAFile.txt";
        List<String> linesInFile = fileController.fileReader(fileLocation);
        assertEquals("Tallinn", linesInFile.get(0));
    }
}
