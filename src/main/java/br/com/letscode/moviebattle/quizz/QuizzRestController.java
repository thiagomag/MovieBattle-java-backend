package br.com.letscode.moviebattle.quizz;

import br.com.letscode.moviebattle.movie.Movie;
import br.com.letscode.moviebattle.quizz.jogadorquizz.JogadorQuizz;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("quizz")
@RequiredArgsConstructor
public class QuizzRestController {

    private final QuizzRestService quizzService;

    @GetMapping
    public List<Movie> listarFilmes() throws IOException {
        return this.quizzService.listMovies();
    }

    @GetMapping("/ranking")
    public List<JogadorQuizz> listarRanking() throws IOException {
        return this.quizzService.listarRanking();
    }

    @PostMapping
    public String jogada(@RequestBody Quizz quizz) throws NoSuchAlgorithmException, IOException {
        try {
            return String.valueOf(quizzService.jogada(quizz));
        } catch (JogadorOuSenhaErradosException | AcabouJogoException e) {
            return e.getMessage();
        }
    }
}