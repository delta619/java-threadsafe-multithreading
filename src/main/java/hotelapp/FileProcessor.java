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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;


public class FileProcessor {
    private ThreadSafeReviewHandler threadSafeReviewHandler;
    private ExecutorService poolOfThreads;
    private Phaser phaser;
    FileProcessor(ThreadSafeReviewHandler threadSafeReviewHandler, int numberOfThreads){
        this.threadSafeReviewHandler = threadSafeReviewHandler;
        poolOfThreads = Executors.newFixedThreadPool(numberOfThreads);
//        poolOfThreads = Executors.newFixedThreadPool(1);

        phaser = new Phaser();
    }

    class Worker implements Runnable{
        Path path;
        public Worker(Path path){
            this.path = path;
        }

        @Override
        public void run(){
            try{
                parseReviews(this.path.toString());
            }
            finally {
                phaser.arriveAndDeregister();
            }
        }
    }

    public void shutDownThreads() throws InterruptedException{
        try{
            phaser.awaitAdvance(phaser.getPhase());
        } finally {
            poolOfThreads.shutdown();
            poolOfThreads.awaitTermination(3, TimeUnit.SECONDS);
        }
    }

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

//    ArrayList<String> reviewPaths = new ArrayList<>();

    public void traverseReviewFiles(String directory) {
        if(directory == null)return;
        Path p = Paths.get(directory);
        try (DirectoryStream<Path> pathsInDir = Files.newDirectoryStream(p)) {
            for (Path path : pathsInDir) {
                System.out.println(path);

                if(Files.isDirectory(path)){
                    traverseReviewFiles(path.toString());
                }
                if (path.toString().endsWith(".json")){
                    Worker worker = new Worker(path);
                    phaser.register();
                    poolOfThreads.submit(worker);
//                    parseReviews(path.toString());
//                    reviewPaths.add(path.toString());
                }
            }
        } catch (IOException e) {
            System.out.println("Can not open directory: " + directory);
        }
    }

    public void initiateReviewInsertion(String parentReviewPath){
        traverseReviewFiles(parentReviewPath);
    }

    public void parseReviews(String currReviewPath){

//        if(reviewPath == null){
//            return reviews;
//        }
//        traverseReviewFiles(reviewPath);

//        for(String s: reviewPaths){
//            Gson gson = new Gson();
        ArrayList<Review> reviews = new ArrayList<>();

            String reviewsFilePath = new File(currReviewPath).getAbsolutePath();

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

            this.threadSafeReviewHandler.insertReviews(reviews);
    }

}
