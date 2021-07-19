package br.com.letscode.moviebattle.quizz.jogadorquizz;

import java.util.Comparator;

public class JogadorQuizzScoreComparator implements Comparator<JogadorQuizz> {
    @Override
    public int compare(JogadorQuizz j1, JogadorQuizz j2) {
        return Integer.compare(j1.getScore(), j2.getScore());
    }
}
