package br.com.letscode.moviebattle.quizz;

import br.com.letscode.moviebattle.jogador.Jogador;
import br.com.letscode.moviebattle.jogador.JogadorRestService;
import br.com.letscode.moviebattle.movie.Movie;
import br.com.letscode.moviebattle.movie.MovieRestRepository;
import br.com.letscode.moviebattle.quizz.moviequizz.MovieQuizz;
import br.com.letscode.moviebattle.quizz.moviequizz.MovieQuizzService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizzRestService {

    private List<MovieQuizz> movieQuizzList;

    private final MovieQuizzService movieQuizzService;
    private final MovieRestRepository movieRestRepository;
    private final JogadorRestService jogadorRestService;

    public List<MovieQuizz> listMovies() throws IOException {
        movieQuizzList = movieQuizzService.escolherFilme();
        return movieQuizzList;
    }

    public Boolean jogada(Jogador jogador, String imdbId) throws NoSuchAlgorithmException, IOException {
        if (jogadorRestService.compareJogador(jogador.getUser(), jogador.getPassword())) {
            List<Movie> moviesRatingList = new ArrayList<>();
            var allMovies = movieRestRepository.listAll();
            for (MovieQuizz movieQuizz : movieQuizzList) {
                for (Movie allMovie : allMovies) {
                    if (movieQuizz.getImdbId().equals(allMovie.getImdbId())) {
                        moviesRatingList.add(allMovie);
                    }
                }
            }
            Boolean x = comparacao(imdbId, moviesRatingList);
            if (x != null) return x;
        }
        throw new JogadorOuSenhaErradosException();
    }

    private Boolean comparacao(String imdbId, List<Movie> moviesRatingList) {
        if (moviesRatingList.get(0).getRating() >= moviesRatingList.get(1).getRating()) {
            if (moviesRatingList.get(0).getImdbId().equals(imdbId))
                return true;
        } else if (moviesRatingList.get(0).getRating() <= moviesRatingList.get(1).getRating()) {
            if (moviesRatingList.get(1).getImdbId().equals(imdbId))
                return true;
        } else {
            return false;
        }
        return null;
    }

    public void score(Quizz quizz) {
        if (quizz.getResposta()) {
            quizz.getJogador().setScore(quizz.getJogador().getScore() + 50);
        } else {
            quizz.getJogador().setVida(quizz.getJogador().getVida() - 1);
        }
    }
}
