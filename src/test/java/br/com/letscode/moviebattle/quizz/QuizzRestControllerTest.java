package br.com.letscode.moviebattle.quizz;

import br.com.letscode.moviebattle.jogador.Jogador;
import br.com.letscode.moviebattle.movie.Movie;
import br.com.letscode.moviebattle.movie.MovieRestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class QuizzRestControllerTest {

    @Autowired
    private MovieRestService movieRestService;

    @Autowired
    private QuizzRestService quizzRestService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listarFilmeParaQuizz() throws Exception {
        this.mockMvc.perform(get("/quizz"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(String.valueOf(new ArrayList<>(movieRestService.getDuplaFilmes()).get(0).getTitle()))))
                .andExpect(content().string(containsString(String.valueOf(new ArrayList<>(movieRestService.getDuplaFilmes()).get(1).getTitle()))));
    }

    @Test
    void listarRanking() throws Exception {
        this.mockMvc.perform(get("/quizz/ranking"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Thiago")));
    }

    @Test
    void jogadaTestTrue() throws Exception {
        Jogador jogador = new Jogador("Thiago", "12345");
        Quizz quizz = Quizz.builder().jogador(jogador).imdbId("tt0848228").build();
        Set<Movie> movieList = new LinkedHashSet<>();
        movieList.add(Movie.builder().title("Batman v Superman: Dawn of Justice").year(2016).imdbId("tt2975590").rating(6.4).build());
        movieList.add(Movie.builder().title("The Avengers").year(2012).imdbId("tt0848228").rating(8.0).build());
        quizzRestService.setMovieList(movieList);
        this.mockMvc.perform(post("/quizz")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(quizz)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(String.valueOf(true))));
    }

    @Test
    void jogadaTestFalse() throws Exception {
        Jogador jogador = new Jogador("Thiago", "12345");
        Quizz quizz = Quizz.builder().jogador(jogador).imdbId("tt2975590").build();
        Set<Movie> movieList = new LinkedHashSet<>();
        movieList.add(Movie.builder().title("Batman v Superman: Dawn of Justice").year(2016).imdbId("tt2975590").rating(6.4).build());
        movieList.add(Movie.builder().title("The Avengers").year(2012).imdbId("tt0848228").rating(8.0).build());
        quizzRestService.setMovieList(movieList);
        this.mockMvc.perform(post("/quizz")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(quizz)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(String.valueOf(false))));
    }

    @Test
    void jogadaTestImdbIdErrado() throws Exception {
        Jogador jogador = new Jogador("Thiago", "12345");
        Quizz quizz = Quizz.builder().jogador(jogador).imdbId("tt458961").build();
        Set<Movie> movieList = new LinkedHashSet<>();
        movieList.add(Movie.builder().title("Batman v Superman: Dawn of Justice").year(2016).imdbId("tt2975590").rating(6.4).build());
        movieList.add(Movie.builder().title("The Avengers").year(2012).imdbId("tt0848228").rating(8.0).build());
        quizzRestService.setMovieList(movieList);
        this.mockMvc.perform(post("/quizz")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(quizz)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(String.valueOf(false))));
    }

    @Test
    void jogadaTestJogadorInexistente() throws Exception {
        Jogador jogador = new Jogador("Mariazinha", "12345");
        Quizz quizz = Quizz.builder().jogador(jogador).imdbId("tt2975590").build();
        this.mockMvc.perform(post("/quizz")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(quizz)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Informe um jogador e/ou senha corretas")));
    }
}