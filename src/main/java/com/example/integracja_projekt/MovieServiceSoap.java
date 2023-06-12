package com.example.integracja_projekt;

import org.springframework.stereotype.Service;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Service
@Endpoint
public class MovieServiceSoap {
//    private static final String NAMESPACE_URI = "http://yournamespace.com/films";
//
//    private final MovieRepository movieRepository;
//
//    public MovieServiceSoap(MovieRepository movieRepository) {
//        this.movieRepository = movieRepository;
//    }
//
//    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetFilmByTitleRequest")
//    @ResponsePayload
//    public GetFilmByTitleResponse getFilmByTitle(@RequestPayload GetFilmByTitleRequest request) {
//        GetFilmByTitleResponse response = new GetFilmByTitleResponse();
//        List<Film> films = filmRepository.findByTitle(request.getTitle());
//        response.getFilms().addAll(films);
//        return response;
//    }
//
//    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetFilmByReleaseYearRequest")
//    @ResponsePayload
//    public GetFilmByReleaseYearResponse getFilmByReleaseYear(@RequestPayload GetFilmByReleaseYearRequest request) {
//        GetFilmByReleaseYearResponse response = new GetFilmByReleaseYearResponse();
//        List<Film> films = filmRepository.findByReleaseYear(request.getReleaseYear());
//        response.getFilms().addAll(films);
//        return response;
//    }
}