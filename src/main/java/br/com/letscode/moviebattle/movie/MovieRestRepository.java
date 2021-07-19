package br.com.letscode.moviebattle.movie;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class MovieRestRepository {

    private String caminho = "src/main/resources/dados/movies.csv";

    private Path path;

    @PostConstruct
    public void init() {
        try {
            path = Paths.get(String.valueOf(caminho));
            if (!path.toFile().exists()) {
                Files.createFile(path);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void inserirArquivo(Movie movie) throws IOException {
        write(format(movie), StandardOpenOption.APPEND);
    }

    private void write(String clienteStr, StandardOpenOption option) throws IOException {
        try (BufferedWriter bf = Files.newBufferedWriter(path, option)) {
            bf.flush();
            bf.write(clienteStr);
        }
    }


    public List<Movie> listAll() throws IOException {
        List<Movie> movies;
        try (BufferedReader br = Files.newBufferedReader(path)) {
            movies = br.lines().filter(Objects::nonNull).filter(Predicate.not(String::isEmpty)).map(this::convert).collect(Collectors.toList());
        }
        return movies;
    }

    private String format(Movie movie) {
        return String.format("%s,%s,%s,%f\r\n",
                movie.getTitle(),
                movie.getYear(),
                movie.getImdbId(),
                movie.getRating());
    }

    private Movie convert(String linha) {
        StringTokenizer token = new StringTokenizer(linha, ",");
        return Movie.builder()
                .title(token.nextToken())
                .year(Integer.valueOf(token.nextToken()))
                .imdbId(token.nextToken())
                .rating(Double.valueOf(token.nextToken()))
                .build();
    }
}
