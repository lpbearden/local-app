package edu.apsu.csci.local_app.Models;

/**
 * Created by PC on 4/30/2016.
 */
public class Review {
    public int id;
    public String date;
    public int rating;
    public String review_text;
    public Locality locality;
    public User user;


    public Review(int id, String date, int rating, String review_text, Locality locality, User user) {
        this.id = id;
        this.date = date;
        this.rating = rating;
        this.review_text = review_text;
        this.locality = locality;
        this.user = user;
    }
}
