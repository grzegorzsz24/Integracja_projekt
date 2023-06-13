package com.example.integracja_projekt.soap;

import com.example.integracja_projekt.model.MovieEntity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = ""
)
@XmlRootElement(
        name = "GetAllMoviesResponse"
)
public class GetAllMoviesResponse {
    private List<MovieEntity> movies;

    public GetAllMoviesResponse() {
    }

    public void setMovies(List<MovieEntity> movies) {
        this.movies = movies;
    }

    public List<MovieEntity> getMovies() {
        return movies;
    }
}