package com.example.integracja_projekt.soap;

import com.example.integracja_projekt.model.Movie;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import jakarta.xml.bind.JAXBException;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Response;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Endpoint
public class MovieEndpoint {
    private static final String NAMESPACE_URI = "http://example.com/integracja-projekt/soap";
    private final String apiUrl = "https://imdb-top-100-movies.p.rapidapi.com/";
    private final String apiKey = "12188ae4ecmsha48db8f3bf2cbeep17749ejsn9b1f0617ac80";
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ExportMoviesRequest")
    @ResponsePayload
    public ExportMoviesResponse getCountry(@RequestPayload ExportMoviesRequest request) throws IOException, JAXBException {
        ExportMoviesResponse response = new ExportMoviesResponse();
        String filePath = "movies.xml";
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setMessage("Plik XML został wygenerowany.");
        return response;
    }
}
