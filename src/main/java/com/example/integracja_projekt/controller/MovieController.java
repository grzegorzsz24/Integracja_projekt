package com.example.integracja_projekt.controller;

import com.example.integracja_projekt.model.MovieEntity;
import com.example.integracja_projekt.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MovieController {
    private final MovieService movieService;

    @GetMapping("/movies/import/{type}")
    public ResponseEntity<String> importMoviesJson(@PathVariable String type) {
        movieService.importMovies(type);
        return new ResponseEntity<String>("Import danych z pliku do bazy danych rozpoczęty.", HttpStatus.OK);
    }

    @GetMapping("/movies")
    public ResponseEntity<?> getAllMovies() throws InterruptedException {
        return new ResponseEntity<List<MovieEntity>>(movieService.getAll(), HttpStatus.OK);
    }

    @PutMapping("/movies/updateTitle")
    public ResponseEntity<String> updateMovieTitle() {
        movieService.updateTitle();
        return ResponseEntity.ok("Tytuł filmu został zaktualizowany");
    }
    @GetMapping("/movies/export/{type}")
    public ResponseEntity<String> exportDatabase(@PathVariable String type) {
        movieService.exportMovies(type);
        return ResponseEntity.ok("Pomyślnie wyeksportowano bazę danych do pliku sql");
    }

    @GetMapping("/movies/sorted")
    public ResponseEntity<?> getAllMoviesByGenre(@RequestParam(defaultValue = "Drama") String genre) throws InterruptedException {
        return new ResponseEntity<List<MovieEntity>>(movieService.getMoviesByGenres(genre), HttpStatus.OK);
    }

}
