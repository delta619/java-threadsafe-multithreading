package hotelapp;

import com.google.gson.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileProcessor {

    public Hotel[] parseHotels(String filepath){

        Gson gson = new Gson();
        String hotelsFilePath = new File(filepath).getAbsolutePath();

        try (FileReader br = new FileReader(hotelsFilePath)) {
            JsonParser parser = new JsonParser();
            JsonObject jo = (JsonObject) parser.parse(br);
            JsonArray jsonArr = jo.getAsJsonArray("sr");
            Hotel[] hotels = gson.fromJson(jsonArr, Hotel[].class);

        return hotels;

        } catch (IOException e) {
            System.out.println("Could not read the file:" + e);

        }

     return null;
    }

    ArrayList<String> reviewPaths = new ArrayList<>();
    public void findReviewFiles(String directory) {
        Path p = Paths.get(directory);
        try (DirectoryStream<Path> pathsInDir = Files.newDirectoryStream(p)) {
            for (Path path : pathsInDir) {

                if(Files.isDirectory(path)){
                    findReviewFiles(path.toString());
                }
                if (path.toString().endsWith(".json")){
                    reviewPaths.add(path.toString());
                }
            }
        } catch (IOException e) {
            System.out.println("Can not open directory: " + directory);
        }
    }

    public ArrayList<Review> parseReviews(String reviewPath, int threads){


        ArrayList<Review> reviews= new ArrayList<>();
        if(reviewPath == null){
            return reviews;
        }
        findReviewFiles(reviewPath);

        for(String s: reviewPaths){
            Gson gson = new Gson();
            String reviewsFilePath = new File(s).getAbsolutePath();

            try (FileReader br = new FileReader(reviewsFilePath)) {
                JsonParser parser = new JsonParser();
                JsonObject jo = (JsonObject) parser.parse(br);
                JsonArray jsonArr = jo.getAsJsonObject("reviewDetails").getAsJsonObject("reviewCollection").getAsJsonArray("review");

                for(JsonElement e: jsonArr){
                    JsonObject review = e.getAsJsonObject();
                    String hotelId = review.get("hotelId").getAsString();
                    String reviewId = review.get("reviewId").getAsString();
                    int ratingOverall = review.get("ratingOverall").getAsInt();
                    String title = review.get("title").getAsString();
                    String reviewText = review.get("reviewText").getAsString();
                    String nickname = review.get("userNickname").getAsString();
                    String reviewSubmissionTime = review.get("reviewSubmissionTime").getAsString();

                    Review reviewObj = new Review(hotelId, reviewId, ratingOverall, title, reviewText, nickname, reviewSubmissionTime);

                    reviews.add(reviewObj);
                }
            } catch (IOException e) {
                System.out.println("Could not read the file:" + e);
            }

        }
        return reviews;
    }

}
