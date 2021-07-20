package br.com.letscode.moviebattle.movie;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    private String title;
    private Integer year;
    private String imdbId;
    @JsonIgnore
    private Double rating;
}