package hotelapp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.concurrent.RecursiveAction;

public class Helper {

    public static int countWords(String line, String checkWord){
        line = line.replaceAll("\\p{Punct}", " ");
        int cnt = 0;
        for(String word: line.split(" ")){
            if(word.equalsIgnoreCase(checkWord)){
                cnt++;
            }
        }
        return cnt;
    };

    public static void writeFile(String filename, String str){
        try {
            FileWriter myWriter = new FileWriter(filename, true);
            myWriter.append(str);
            myWriter.close();
        }catch (Exception e){
            System.out.println("Error occurred while writing.");
        }
    }
    public static void createOutputFiles(String output1_file){

        File myObj1 = new File(output1_file);

        try {
            myObj1.createNewFile();

            PrintWriter writer1 = new PrintWriter(output1_file);
            writer1.close();


        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void displayReviews(TreeSet<Review> reviews){
        for(Review review: reviews){
            System.out.println(review);
        }
    }
    public static void displayHotel(Hotel hotel){
        if(hotel == null){
            System.out.println("No hotel found");
        }
        String result = "";

        result += ("The hotel details are: "+System.lineSeparator());
        result += hotel.getName() + System.lineSeparator();
        result += hotel+System.lineSeparator();
        System.out.println(result);

    }
}


