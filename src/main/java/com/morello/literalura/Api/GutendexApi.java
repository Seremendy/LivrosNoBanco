package com.morello.literalura.Api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.morello.literalura.DTO.GutendexResponse;
import com.morello.literalura.Model.Livro;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GutendexApi {

    public List<Livro> buscarLivrosPorTitulo(String titulo) {
        try {
            String url = "https://gutendex.com/books?search=" + URLEncoder.encode(titulo, StandardCharsets.UTF_8);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpClient client = HttpClient.newBuilder()
                    .followRedirects(HttpClient.Redirect.ALWAYS)
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            GutendexResponse resultado = mapper.readValue(response.body(), GutendexResponse.class);

            return resultado.getResults().stream()
                    .map(dto -> {
                        Livro livro = new Livro();
                        livro.setTitulo(dto.getTitle());
                        livro.setAutor(dto.getFirstAuthorName());
                        livro.setIdioma(String.join(",", dto.getLanguages()));
                        livro.setDownloads(dto.getDownload_count());

                        if (!dto.getAuthors().isEmpty()) {
                            var autor = dto.getAuthors().get(0);
                            livro.setAnoNascimento(autor.getAnoNascimento());
                            livro.setAnoMorte(autor.getAnoMorte());
                        }

                        return livro;
                    })
                    .collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao consumir API Gutendex", e);
        }
    }
}

