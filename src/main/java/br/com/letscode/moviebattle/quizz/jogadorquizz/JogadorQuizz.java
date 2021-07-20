package br.com.letscode.moviebattle.quizz.jogadorquizz;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private int vida;
    private int score;
    @JsonIgnore
    private int rodada;

    @Override
    public int compareTo(JogadorQuizz jogadorQuizz) {
        return this.getNome().compareToIgnoreCase(jogadorQuizz.getNome());
    }

    @Override
    public String toString() {
        return "Nome :" + nome +
                ", score: " + score + "\n";
    }
}
