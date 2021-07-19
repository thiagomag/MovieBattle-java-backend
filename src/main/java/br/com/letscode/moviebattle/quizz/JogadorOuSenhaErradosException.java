package br.com.letscode.moviebattle.quizz;

public class JogadorOuSenhaErradosException extends RuntimeException {
    public JogadorOuSenhaErradosException() {
        super("Inform um jogador e/ou senha corretas");
    }
}
