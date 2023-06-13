package com.example.integracja_projekt.model;


import lombok.Data;

import java.util.List;

@Data
public class Movie {
    private int rank;
    private String title;
    private String thumbnail;
    private String rating;
    private String id;
    private int year;
    private String image;
    private String description;
    private String trailer;
    private List<String> genre;
    private List<String> director;
    private List<String> writers;
    private String imdbid;
}
