package br.com.letscode.moviebattle.jogador;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RequestMapping("/jogador")
@RestController
public class JogadorRestController {

    private final JogadorRestRepository jogadorRestRepository;
    private final JogadorRestService jogadorRestService;

    public JogadorRestController(JogadorRestRepository jogadorRestRepository, JogadorRestService jogadorRestService) {
        this.jogadorRestRepository = jogadorRestRepository;
        this.jogadorRestService = jogadorRestService;
    }

    @GetMapping
    public String login(@RequestBody Jogador jogador) throws NoSuchAlgorithmException, IOException {
        jogadorRestRepository.loadJogadoresList();
        if(jogadorRestService.compareJogador(jogador.getUser(), jogador.getPassword())){
            return "Login bem sucedido";
        }
        else {
            return "Login mal sucedido";
        }
    }

    @PostMapping
    public String cadastroJogador(@RequestBody Jogador jogador) throws IOException, NoSuchAlgorithmException {
        if (jogadorRestRepository.createJogador(jogador)){
            return "Jogador criado";
        }
        else {
            return "ERRO";
        }
    }
}
