package br.com.letscode.moviebattle.quizz.moviequizz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieQuizz {

    private String nome;
    private Integer ano;
    private String imdbId;
}