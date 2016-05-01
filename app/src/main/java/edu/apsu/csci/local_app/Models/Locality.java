package edu.apsu.csci.local_app.Models;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 4/30/2016.
 */
public class Locality {
    public int id;
    public String name;
    public String description;
    public String street;
    public String city;
    public String state;
    public String zip;
    public ArrayList<String> img_paths;
    public ArrayList<String> types;

    public Locality () {

    }

    public Locality (int id, String name, String description, String street, String city, String state, String zip, ArrayList<String> img_paths, ArrayList<String> types) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.img_paths = img_paths;
        this.types = types;
    }

    public int getId() {
        return id;
    }

}