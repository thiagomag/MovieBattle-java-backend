package br.com.letscode.moviebattle.jogador;

import br.com.letscode.moviebattle.quizz.JogadorOuSenhaErradosException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RequestMapping("/jogador")
@RestController
@RequiredArgsConstructor
public class JogadorRestController {

    private final JogadorRestRepository jogadorRestRepository;
    private final JogadorRestService jogadorRestService;

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String login(@RequestBody Jogador jogador) throws NoSuchAlgorithmException, IOException {
        jogadorRestRepository.loadJogadoresList();
        try {
            if (jogadorRestService.compareJogador(jogador.getUser(), jogador.getPassword())) {
                return "Login bem sucedido";
            }
        } catch (JogadorOuSenhaErradosException e) {
            return e.getMessage();
        }
        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String cadastroJogador(@RequestBody Jogador jogador) throws IOException, NoSuchAlgorithmException {
        if (jogadorRestRepository.createJogador(jogador)){
            return "Jogador criado";
        }
        else {
            return String.valueOf(HttpStatus.BAD_REQUEST);
        }
    }
}
