package com.example.integracja_projekt.controller;

import com.example.integracja_projekt.model.Movie;
import com.example.integracja_projekt.model.MovieEntity;
import com.example.integracja_projekt.repository.MovieRepository;
import com.example.integracja_projekt.service.MovieService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.AllArgsConstructor;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MovieController {
    MovieService movieService;
    MovieRepository movieRepository;
    private final String apiUrl = "https://imdb-top-100-movies.p.rapidapi.com/";
    private final String apiKey = "12188ae4ecmsha48db8f3bf2cbeep17749ejsn9b1f0617ac80";

    @GetMapping("/movies/xml")
    public ResponseEntity<String> exportTopMoviesXml() throws IOException {
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        CompletableFuture<String> futureResponse = client.prepareGet(apiUrl)
                .addHeader("X-RapidAPI-Key", apiKey)
                .addHeader("X-RapidAPI-Host", "imdb-top-100-movies.p.rapidapi.com")
                .execute()
                .toCompletableFuture()
                .thenApply(Response::getResponseBody);

        String xml = futureResponse.join();
        client.close();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Movie> movies = objectMapper.readValue(xml, new TypeReference<List<Movie>>() {});

            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.writeValue(new File("movies.xml"), movies);

            return new ResponseEntity<String>("Plik XML został wygenerowany.", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("Wystąpił błąd podczas generowania pliku XML.", HttpStatus.OK);
        }
    }

    @GetMapping("/movies/import")
    public ResponseEntity<String> importMovies() {
        movieService.importMovies();
        return new ResponseEntity<String>("Import danych z pliku JSON do bazy danych rozpoczęty.", HttpStatus.OK);
    }

    @GetMapping("/movies")
    public ResponseEntity<?> getAllMovies() {
        return new ResponseEntity<List<MovieEntity>>(movieRepository.findAll(), HttpStatus.OK);
    }
}
