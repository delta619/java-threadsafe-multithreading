package hotelapp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Review implements Comparable<Review> {
    private String hotelId;
    private String reviewId;
    private double ratingOverall;
    private String title;
    private String reviewText;
    private String userNickname;
    private LocalDate reviewSubmissionTime;

    public Review(String hotelId, String reviewId, double ratingOverall, String title, String reviewText, String userNickname, String reviewSubmissionTime){
        this.hotelId = hotelId;
        this.reviewId = reviewId;
        this.ratingOverall = ratingOverall;
        this.title = title;
        this.reviewText = reviewText;
        this.userNickname = userNickname;
        this.reviewSubmissionTime = LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(reviewSubmissionTime));
    }

    public String getReviewId(){
        return this.reviewId;
    }

    public String getHotelId(){
        return this.hotelId;
    }

    public String[] getReviewTextWords(boolean... punctuationRemoved){

        if(punctuationRemoved.length == 1 && punctuationRemoved[0]){
            return this.reviewText.replaceAll("\\p{Punct}", " ").split(" ");
        }
        return this.reviewText.split(" ");
    }
    public String getUserNickname(){
        if(this.userNickname == null){
            return "Anonymous";
        }
        return this.userNickname;
    }
    public LocalDate getReviewSubmissionTime(){
        return this.reviewSubmissionTime;
    }
    @Override
    public String toString() {
        return String.join(System.lineSeparator()+"\t", "\tHotel id - "+ this.getHotelId(), "Review id -"+this.getReviewId(), "Overall Rating - "+ this.ratingOverall,"Title - " + this.title,"Review text - " + this.reviewText,"Nickname - " + this.getUserNickname(),"Submission time - "+this.reviewSubmissionTime);
    }

    public double getRatingOverall() {
        return ratingOverall;
    }

    public String getTitle() {
        return title;
    }

    public String getReviewText() {
        return this.reviewText;
    }


    @Override
    public int compareTo(Review o) {
        return getReviewText().compareTo(o.getReviewText());
    }
}
