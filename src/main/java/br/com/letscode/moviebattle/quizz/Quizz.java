package br.com.letscode.moviebattle.quizz;

import br.com.letscode.moviebattle.jogador.Jogador;
import br.com.letscode.moviebattle.movie.Movie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quizz {

    private Jogador jogador;
    private String imdbId;
    private Boolean resposta;
}
