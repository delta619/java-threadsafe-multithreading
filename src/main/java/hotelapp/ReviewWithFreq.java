package hotelapp;

import java.time.LocalDateTime;

public class ReviewWithFreq implements Comparable<ReviewWithFreq> {

    private String word;
    private int frequency;
    private Review review;

    public ReviewWithFreq(Review review, String word){
        this.review = review;
        this.word = word;
        this.frequency = Helper.countWords(review.getReviewText(), word);
    }

    public int getFrequency() {
        return frequency;
    }

    public String getReviewText() {
        return review.getReviewText();
    }

    public String getWord() {
        return word;
    }

    public LocalDateTime getReviewSubmissionTime(){
        return this.review.getReviewSubmissionTime();
    }
    public String toString() {
        return String.join(System.lineSeparator()+"\t", "\tHotel id - "+ this.review.getHotelId(), "Review id -"+this.review.getReviewId(), "Overall Rating - "+ this.review.getRatingOverall(),"Title - " + this.review.getTitle(),"Review text - " + this.getReviewText(),"Nickname - " + this.review.getUserNickname(),"Submission time - "+ this.review.getReviewSubmissionTime());
    }

    @Override
    public int compareTo(ReviewWithFreq o) {
        return getReviewText().compareTo(o.getReviewText());
    }
}
