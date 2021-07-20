package br.com.letscode.moviebattle.quizz;

import br.com.letscode.moviebattle.quizz.jogadorquizz.JogadorQuizz;

import java.util.List;

public class AcabouJogoException extends RuntimeException {
    public AcabouJogoException(List<JogadorQuizz> rankingList) {
        super("O jogo acabou\n" +
                "Ranking de pontuação:\n" +
                rankingList);
    }
}
