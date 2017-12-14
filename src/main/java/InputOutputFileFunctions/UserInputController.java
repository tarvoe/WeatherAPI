package InputOutputFileFunctions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class UserInputController {


    public static void main(String[] args) throws IOException {

        FileController controller2 = new FileController();
        UserInputController controller = new UserInputController();
        controller.useUserInput(controller2);
    }

    private String readUserEnteredCity(){
        Scanner scanner1 = new Scanner(System.in);
        System.out.println("Enter the city name: ");
        return scanner1.next();
    }

    private String readUserEnteredFileName(){
        Scanner scanner1 = new Scanner(System.in);
        System.out.println("Enter the file name with extention: ");
        return scanner1.next();
    }

    private List<String> useUserInput(FileController controller) throws IOException {
        System.out.println("Do you want to enter the city name or read it from a file ?");
        System.out.println("Enter 1 if you want to enter the name yourself or enter 2 if you want to read from a file");
        Scanner scanner = new Scanner(System.in);
        String method = scanner.next();
        List<String> cities = new ArrayList<>();
        if (Objects.equals(method,"1")){
            readUserEnteredCity();
            cities.add(readUserEnteredCity());
        }else if (Objects.equals(method, "2")){
            String fileName = readUserEnteredFileName();
            List<String> fileRead = controller.fileReader(controller.filePathConstructor(fileName));
            cities.addAll(fileRead);
        }else{
            useUserInput(controller);
        }
        System.out.println(cities);
        return cities;
    }

}
