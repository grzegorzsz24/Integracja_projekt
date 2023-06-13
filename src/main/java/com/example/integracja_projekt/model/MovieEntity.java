package com.example.integracja_projekt.model;

import jakarta.persistence.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MovieEntity", namespace = "http://yournamespace.com/movies")
@Data
public class MovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entityId;
    private int rank;
    private String title;
    private String thumbnail;
    private String rating;
    private String id;
    private int releaseYear;
    private String image;
    private String description;
    private String trailer;
    @ElementCollection
    private List<String> genres;
    @ElementCollection
    private List<String> director;
    @ElementCollection
    private List<String> writers;
    private String imdbid;

}
