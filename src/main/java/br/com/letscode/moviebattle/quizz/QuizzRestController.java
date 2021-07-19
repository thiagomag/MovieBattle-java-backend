package br.com.letscode.moviebattle.quizz;

import br.com.letscode.moviebattle.quizz.moviequizz.MovieQuizz;
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
    public List<MovieQuizz> listarFilmes() throws IOException {
        return this.quizzService.listMovies();
    }

    @PostMapping
    public Boolean jogada(@RequestBody Quizz quizz) throws NoSuchAlgorithmException, IOException {
        return quizzService.jogada(quizz);
    }
}