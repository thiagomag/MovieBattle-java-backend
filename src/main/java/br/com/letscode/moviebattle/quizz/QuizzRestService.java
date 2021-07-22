package br.com.letscode.moviebattle.quizz;

import br.com.letscode.moviebattle.jogador.JogadorRestService;
import br.com.letscode.moviebattle.movie.Movie;
import br.com.letscode.moviebattle.movie.MovieRestService;
import br.com.letscode.moviebattle.quizz.jogadorquizz.JogadorQuizz;
import br.com.letscode.moviebattle.quizz.jogadorquizz.JogadorQuizzRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Service
@RequiredArgsConstructor
public class QuizzRestService {

    private Set<Movie> movieList;

    private final MovieRestService movieRestService;
    private final JogadorRestService jogadorRestService;
    private final JogadorQuizzRepository jogadorQuizzRepository;
    private final QuizzRestRepository quizzRestRepository;

    public Set<Movie> listMovies() throws IOException {
        movieList = movieRestService.escolherFilme();
        return movieList;
    }

    public Boolean jogada(Quizz quizz) throws NoSuchAlgorithmException, IOException {
        if (jogadorRestService.compareJogador(quizz.getJogador().getUser(), quizz.getJogador().getPassword())) {
            var jogadorQuizz = JogadorQuizz.builder()
                    .nome(quizz.getJogador().getUser())
                    .build();
            List<JogadorQuizz> jogadorQuizzList = formatarJogador(jogadorQuizz);
            Boolean resposta = comparacao(quizz.getImdbId(), movieList);
            quizz.setResposta(resposta);
            jogadorQuizzList.add(score(quizz, jogadorQuizz));
            jogadorQuizzRepository.inserirArquivo(jogadorQuizzList);
            if (jogadorQuizz.getVida() == 0) {
                quizzRestRepository.inserirArquivo(jogadorQuizzList);
                throw new AcabouJogoException(quizzRestRepository.listAll());
            }
            return resposta;
        }
        throw new JogadorOuSenhaErradosException();
    }

    private JogadorQuizz score(Quizz quizz, JogadorQuizz jogadorQuizz) {
        if(quizz.getResposta()) {
            jogadorQuizz.setScore(jogadorQuizz.getScore() + 50);
        } else {
            jogadorQuizz.setVida(jogadorQuizz.getVida() - 1);
        }
        return jogadorQuizz;
    }

    private List<JogadorQuizz> formatarJogador(JogadorQuizz jogadorQuizz) throws IOException {
        var listaQuizz = jogadorQuizzRepository.listAll();
        for (JogadorQuizz jogador : listaQuizz) {
            if (jogador.getNome().equals(jogadorQuizz.getNome()) && jogador.getVida() > 0) {
                    jogadorQuizz.setRodada(jogador.getRodada() + 1);
                    jogadorQuizz.setVida(jogador.getVida());
                    jogadorQuizz.setScore(jogador.getScore());
                    listaQuizz.remove(jogador);
                    break;
            }
        }
        if (jogadorQuizz.getRodada() == 0) {
            jogadorQuizz.setRodada(1);
            jogadorQuizz.setVida(3);
            jogadorQuizz.setScore(0);
        }
        return listaQuizz;
    }

    private Boolean comparacao(String imdbId, Set<Movie> moviesRatingList) {
        var movieList = new ArrayList<>(moviesRatingList);
        if (movieList.get(0).getImdbId().equals(imdbId)) {
            return movieList.get(0).getRating() >= movieList.get(1).getRating();
        } else if (movieList.get(1).getImdbId().equals(imdbId)) {
            return movieList.get(1).getRating() >= movieList.get(0).getRating();
        }
        return false;
    }

    public List<JogadorQuizz> listarRanking() throws IOException {
        return quizzRestRepository.listAll();
    }
}