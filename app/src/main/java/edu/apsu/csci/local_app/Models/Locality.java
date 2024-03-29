package edu.apsu.csci.local_app.Models;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 4/30/2016.
 */
public class Locality implements Serializable {
    public int id;
    public String name;
    public String description;
    public String street;
    public String city;
    public String state;
    public String zip;
    public String [] img_paths;
    public String [] types;
    public Review [] reviews;
    public int reviewCount;

    public Locality () {

    }

    public Locality (String name, String description, String street, String city, String state, String zip, String [] img_paths, String [] types) {
        this.name = name;
        this.description = description;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.img_paths = img_paths;
        this.types = types;
        this.reviewCount = 0;
        this.reviews = new Review[10];
    }

    public int getId() {
        return id;
    }

    public String getName() { return name; }

    public String printTypes() {
        String typeString = types[0];
        for (int i = 1; i < types.length; i++) {
            if (types[i] == null)
                break;
            else
                typeString += ", " + types[i];
        }
        return typeString;
    }

}
