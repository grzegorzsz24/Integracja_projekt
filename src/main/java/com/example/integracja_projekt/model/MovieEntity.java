package com.example.integracja_projekt.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MovieEntity", namespace = "http://yournamespace.com/movies")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entityId;
    @NotNull
    private int rank;
    @NotNull
    private String title;
    @NotNull
    private String thumbnail;
    @NotNull
    private String rating;
    @NotNull
    private String id;
    @NotNull
    private int releaseYear;
    @NotNull
    private String image;
    @Size(max = 300)
    @NotNull
    private String description;
    @NotNull
    private String trailer;
    @NotNull
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> genres;
    @NotNull
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> director;
    @NotNull
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> writers;
    @NotNull
    private String imdbid;
}
