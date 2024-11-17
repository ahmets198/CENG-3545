package msku.ceng.madlab.week6;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private String name;
    private String director;
    private int year;
    private List<String> stars =new ArrayList<>();
    private String description;
}
public Movie(String name, String director,int year,List<String> stars,String description){
    this.name=name;
    this.director=director;
    this.year=year;
    this.stars=stars;
    this.description=description;
}
public String getName(){
    return name;
}
