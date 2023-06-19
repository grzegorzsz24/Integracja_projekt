package com.example.integracja_projekt.service;

import com.example.integracja_projekt.model.Movie;
import com.example.integracja_projekt.model.MovieEntity;
import com.example.integracja_projekt.repository.MovieRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final JdbcTemplate jdbcTemplate;

    public void importMovies(String importType) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Movie> movies = new ArrayList<>();

            if (importType.equals("json")) {
                movies = List.of(objectMapper.readValue(new File("movies.json"), Movie[].class));
            } else if (importType.equals("xml")) {
                XmlMapper xmlMapper = new XmlMapper();
                movies = List.of(xmlMapper.readValue(new File("movies.xml"), Movie[].class));
            } else {
                throw new IllegalArgumentException("Zły typ");
            }

            for (Movie movie : movies) {
                MovieEntity movieEntity = new MovieEntity();
                movieEntity.setTitle(movie.getTitle());
                movieEntity.setRating(movie.getRating());
                movieEntity.setRank(movie.getRank());
                movieEntity.setThumbnail(movie.getThumbnail());
                movieEntity.setId(movie.getId());
                movieEntity.setReleaseYear(movie.getYear());
                movieEntity.setImage(movie.getImage());
                movieEntity.setDescription(movie.getDescription());
                movieEntity.setTrailer(movie.getTrailer());
                movieEntity.setGenres(movie.getGenre());
                movieEntity.setDirector(movie.getDirector());
                movieEntity.setWriters(movie.getWriters());
                movieEntity.setImdbid(movie.getImdbid());

                movieRepository.save(movieEntity);
            }

            System.out.println("Import danych z pliku XML/JSON do bazy danych został zakończony.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exportMovies(String exportType) {
        if (StringUtils.isEmpty(exportType)) {
            throw new IllegalArgumentException("Export type must be specified.");
        }

        List<String> tableNames = List.of("MOVIE_ENTITY", "MOVIE_ENTITY_DIRECTOR", "MOVIE_ENTITY_GENRES", "MOVIE_ENTITY_WRITERS"); // Przykładowe nazwy tabel
        try (FileWriter writer = new FileWriter("./moviesExport.".concat(exportType))) {
            if (exportType.equalsIgnoreCase("sql")) {
                exportToSql(writer, tableNames);
            } else if (exportType.equalsIgnoreCase("json")) {
                exportToJson(writer, tableNames);
            } else {
                throw new IllegalArgumentException("Unsupported export type.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exportToSql(FileWriter writer, List<String> tableNames) throws IOException {
        for (String tableName : tableNames) {
            String sqlQuery = "SELECT * FROM " + tableName;
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(sqlQuery);

            writer.write("-- TABLE: " + tableName + "\n");
            for (Map<String, Object> row : rows) {
                String rowString = row.values().stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(", "));

                writer.write(rowString + "\n");
            }
            writer.write("\n");
        }
    }

    private void exportToJson(FileWriter writer, List<String> tableNames) throws IOException {
        for (String tableName : tableNames) {
            String sqlQuery = "SELECT * FROM " + tableName;
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(sqlQuery);

            writer.write("{\"" + tableName + "\": [\n");
            for (int i = 0; i < rows.size(); i++) {
                Map<String, Object> row = rows.get(i);
                String jsonRow = convertRowToJson(row);

                writer.write(jsonRow);
                if (i < rows.size() - 1) {
                    writer.write(",\n");
                }
            }
            writer.write("]}\n\n");
        }
    }

    private String convertRowToJson(Map<String, Object> row) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(row);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }


    public void updateTitle() {
        movieRepository.updateTtitle();
    }
    public List<MovieEntity> getAll() throws InterruptedException {
        return movieRepository.findAll();
    }

    public List<MovieEntity> getMoviesByGenres(String genre) {
        return movieRepository.findMoviesByGenresOrderByRatingDesc(genre);
    }
}
