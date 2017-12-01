package InputOutputFileFunctions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class UserInputController {


    public static void main(String[] args) throws IOException {
        UserInputController controller = new UserInputController();
        controller.userInputTaker();
    }


    public List<String> userInputTaker () throws IOException {
        System.out.println("Do you want to enter the city name or read it from a file ?");
        System.out.println("Enter 1 if you want to enter the name yourself or enter 2 if you want to read from a file");
        Scanner scanner = new Scanner(System.in);
        String method = scanner.next();
        List<String> cities = new ArrayList<String>();

        if (Objects.equals(method, "1")) {
            Scanner scanner1 = new Scanner(System.in);
            System.out.println("Enter the city name: ");
            String city2 = scanner1.next();
            cities.add(city2);

        }else if (Objects.equals(method, "2")){
            FileController controller = new FileController();
            System.out.println("Enter the file name with extention");
            Scanner scanner1 = new Scanner(System.in);
            String fileName = scanner1.next();
            cities = controller.fileReader(controller.filePathConstructor(fileName));
        }else{
            System.out.println(method.getClass());
            userInputTaker();
        }
        System.out.println(cities);
        return cities;
    }
}
