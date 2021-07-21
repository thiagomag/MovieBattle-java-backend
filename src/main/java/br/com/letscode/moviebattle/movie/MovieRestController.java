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

    private final MovieRestService movieRestService;

    @GetMapping
    public List<Movie> filmesParaQuizz(@RequestParam List<String> name) throws IOException {
        return movieRestService.salvarFilmes(name);
    }

    @GetMapping("/todos")
    public List<Movie> listar() throws IOException {
        return movieRestService.listarTodos();
    }
}