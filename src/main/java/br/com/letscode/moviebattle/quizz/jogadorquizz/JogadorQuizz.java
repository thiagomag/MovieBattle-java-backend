package br.com.letscode.moviebattle.quizz.jogadorquizz;

import br.com.letscode.moviebattle.jogador.Jogador;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JogadorQuizz implements Comparable<JogadorQuizz> {

    private String nome;
    private int vida;
    private int score;
    private int rodada;

    @Override
    public int compareTo(JogadorQuizz jogadorQuizz) {
        return this.getNome().compareToIgnoreCase(jogadorQuizz.getNome());
    }
}
