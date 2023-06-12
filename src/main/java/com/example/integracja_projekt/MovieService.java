package com.example.integracja_projekt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Service
public class MovieService {
    MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void importMovies() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Movie[] movies = objectMapper.readValue(new File("movies.json"), Movie[].class);
            List<Movie> movieList = Arrays.asList(movies);

            for (Movie movie : movieList) {
                MovieEntity movieEntity = new MovieEntity();
                movieEntity.setTitle(movie.getTitle());
                movieEntity.setRating(movie.getRating());
                movieEntity.setRank(movie.getRank());
                movieEntity.setThumbnail(movie.getThumbnail());
                movieEntity.setId(movie.getId());
                movieEntity.setYear(movie.getYear());
                movieEntity.setImage(movie.getImage());
                movieEntity.setDescription(movie.getDescription());
                movieEntity.setTrailer(movie.getTrailer());
                movieEntity.setGenres(movie.getGenre());
                movieEntity.setDirector(movie.getDirector());
                movieEntity.setWriters(movie.getWriters());
                movieEntity.setImdbid(movie.getImdbid());

                movieRepository.save(movieEntity);
            }

            System.out.println("Import danych z pliku JSON do bazy danych został zakończony.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
