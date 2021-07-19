package br.com.letscode.moviebattle.movie;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("movies")
@RequiredArgsConstructor
public class MovieRestController {

    private final MovieService movieService;

    @GetMapping
    public List<Movie> filmesParaQuizz(@RequestParam List<String> name) throws IOException {
        return movieService.salvarFilmes(name);
    }

    @GetMapping("/todos")
    public List<Movie> listar() throws IOException {
        return movieService.listarTodos();
    }
}