import java.io.*;
import java.util.*;
public class main {

    private static void loadFile(BufferedReader reader){
        try{
            String line = reader.readLine();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String filePath;

        System.out.println("Saisissez le chemin vers le csv:");
        filePath = input.nextLine();

        FileReader fileReader = new FileReader(filePath);
        BufferedReader fileBuffer = new BufferedReader(fileReader);

        loadFile(fileBuffer);


    }
}