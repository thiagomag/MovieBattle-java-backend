package br.com.letscode.moviebattle.quizz.jogadorquizz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JogadorQuizz {

    private String nome;
    private int vida;
    private int score;
    private int rodada;
}
