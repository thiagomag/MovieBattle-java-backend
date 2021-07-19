package br.com.letscode.moviebattle.jogador;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class JogadorRestService {

    private final JogadorRestRepository jogadorRestRepository;

    public Boolean compareJogador(String user, String password) throws NoSuchAlgorithmException {
        String passwordHex = jogadorRestRepository.getSHA1Hex(password);
        for (Jogador jogador : jogadorRestRepository.jogadoresList) {
            if (user.equals(jogador.getUser())){
                return passwordHex.equals(jogador.getPassword());
            }
        }
        return false;
    }
}
