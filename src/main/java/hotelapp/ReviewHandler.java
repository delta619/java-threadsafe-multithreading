package hotelapp;

import java.io.*;
import java.util.*;

public class ReviewHandler {
    private  Map<String, TreeSet<Review>> hotelReviewMap = new HashMap<>();
    private  Map<String, TreeSet<ReviewWithFreq>> wordToReviews = new HashMap<>();

    public ReviewHandler(ArrayList<Review> reviews){
        insertReviews(reviews);
        setUpWords();
    }
    public void insertReviews(ArrayList<Review> reviews){
        for (Review review : reviews) {

            if (!hotelReviewMap.containsKey(review.getHotelId())) {
                TreeSet<Review> temp = new TreeSet<>((r1, r2)-> r2.getReviewSubmissionTime().compareTo(r1.getReviewSubmissionTime()));
                hotelReviewMap.put(review.getHotelId(), temp);
            }
            hotelReviewMap.get(review.getHotelId()).add(review);
        }

        this.setUpWords();

    }
    public void setUpWords(){

        HashSet<String> stopWords = getStopWords();

        for (String hotelId: hotelReviewMap.keySet()){
            TreeSet<Review> hotelReviews = hotelReviewMap.get(hotelId);
            for(Review currReview: hotelReviews){
                for (String word: currReview.getReviewTextWords(true)) {
                    word = word.toLowerCase(); // storing all the keys in lowercase.
                    if (stopWords.contains(word)) {
                        continue;
                    }
                    if (!wordToReviews.containsKey(word)) {
                        TreeSet<ReviewWithFreq> emptyReviewsTree = new TreeSet<>(new Comparator<ReviewWithFreq>() {
                            public int compare(ReviewWithFreq r1, ReviewWithFreq r2) {
                                if (r1.getFrequency() == r2.getFrequency()) {
                                    return r2.getReviewSubmissionTime().compareTo(r1.getReviewSubmissionTime());
                                }
                                return r2.getFrequency()- r1.getFrequency();
                            }
                        });
                        wordToReviews.put(word, emptyReviewsTree);
                    }
                    wordToReviews.get(word).add(new ReviewWithFreq(currReview, word));
                }
            }
        }
    }
    public void findReviewsByHotelId(String hotelId){
        if(!hotelReviewMap.containsKey(hotelId)){
            System.out.println("No Reviews for hotelID - "+hotelId);
            return;
        }
        TreeSet<Review> reviews = hotelReviewMap.get(hotelId);
            for(Review review: reviews){
                System.out.println(review);
                System.out.println("**********************");
            }
    }

    public static HashSet<String> getStopWords(){
        HashSet<String> m = new HashSet<>();
         String WORD_FILE = "input/stop_words.txt";
        try {
            File file = new File(WORD_FILE);
            BufferedReader bf = new BufferedReader(new FileReader(file));
            String line;
            while ((line = bf.readLine()) != null) {
                for (String word : line.trim().split("\\s")) {
                    if (!word.isEmpty())
                        m.add(word.toLowerCase());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return m;
    }
    public void findWords(String word){
        word = word.toLowerCase(); // convert to lowercase before findind in hashmap because keys are stored in lowercase
        if(!wordToReviews.containsKey(word)){
            System.out.println("No reviews found with word "+word);
            return;
        }

        for(ReviewWithFreq review: wordToReviews.get(word)){
            System.out.println(review);
            System.out.println("\tWord count of "+word+" - "+review.getFrequency());
            System.out.println("*****************");
        }
    }

    public int countWords(String line, String checkWord){
        line = line.replaceAll("\\p{Punct}", " ");
        int cnt = 0;
        for(String word: line.split(" ")){
            if(word.equalsIgnoreCase(checkWord)){
                cnt++;
            }
        }
        return cnt;
    };

}