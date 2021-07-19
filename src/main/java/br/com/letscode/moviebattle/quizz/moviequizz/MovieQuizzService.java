package br.com.letscode.moviebattle.quizz.moviequizz;

import br.com.letscode.moviebattle.movie.Movie;
import br.com.letscode.moviebattle.movie.MovieRestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MovieQuizzService {

    private final MovieRestRepository movieRestRepository;

    public List<Movie> listarTodos() throws IOException {
        return movieRestRepository.listAll();
    }

    private Movie filmeAleatorio() throws IOException  {
        var movies = listarTodos();
        var random = new Random();
        return movies.get(random.nextInt(movies.size()));
    }

    public List<MovieQuizz> escolherFilme() throws IOException {
        List<MovieQuizz> list = new ArrayList<>();
        var primeiroFilme = filmeAleatorio();
        var firstMovie = MovieQuizz.builder()
                .nome(primeiroFilme.getTitle())
                .ano(primeiroFilme.getYear())
                .imdbId(primeiroFilme.getImdbId())
                .build();
        list.add(firstMovie);
        Movie segundoFilme;
        do {
            segundoFilme = filmeAleatorio();
        } while (primeiroFilme.equals(segundoFilme));
        var secondMovie = MovieQuizz.builder()
                .nome(segundoFilme.getTitle())
                .ano(segundoFilme.getYear())
                .imdbId(segundoFilme.getImdbId())
                .build();
        list.add(secondMovie);
        return list;
    }
}