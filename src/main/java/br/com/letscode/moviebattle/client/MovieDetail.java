package br.com.letscode.moviebattle.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.text.NumberFormat;
import java.util.Locale;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDetail extends MovieMinimal {

    private Double rating;
    private Long votes;
    private Boolean response;

    @JsonProperty("imdbRating")
    public void setRating(String rating) {
        this.rating = Double.valueOf(rating);
    }

    @SneakyThrows
    @JsonProperty("imdbVotes")
    public void setVotes(String votes) {
        this.votes = NumberFormat.getInstance(Locale.US)
            .parse(votes)
            .longValue();
    }

    @JsonProperty("Response")
    public void setResponse(String response) {
        this.response = Boolean.valueOf(response);
    }
}
