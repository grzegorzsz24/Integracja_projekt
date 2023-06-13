package com.example.integracja_projekt.soap;

import com.example.integracja_projekt.repository.MovieRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.ws.server.EndpointAdapter;
import org.springframework.ws.server.endpoint.adapter.PayloadEndpointAdapter;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@Component
public class MovieEndpoint{
    private static final String NAMESPACE_URI = "http://yournamespace.com/movies";

    private MovieRepository movieRepository;

    public MovieEndpoint(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllMoviesRequest")
    @ResponsePayload
    public GetAllMoviesResponse getAllMovies(@RequestPayload GetAllMoviesRequest request) {
        GetAllMoviesResponse response =  new GetAllMoviesResponse();
        response.setMovies(movieRepository.findAll());
        return response;
    }
    @Bean
    public EndpointAdapter messageEndpointAdapter() {
        return new PayloadEndpointAdapter();
    }
}