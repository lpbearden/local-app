package edu.apsu.csci.local_app.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 4/30/2016.
 */
public class User {
    public int id;
    public String username;
    public String first_name;
    public String last_name;
    public String password;
    public String profilePic_path;
    public ArrayList<Locality> user_list;

    public User(int id, String username, String first_name, String last_name, String password, String profilePic_path) {
        this.id = id;
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.password = password;
        this.profilePic_path = profilePic_path;
    }
}
