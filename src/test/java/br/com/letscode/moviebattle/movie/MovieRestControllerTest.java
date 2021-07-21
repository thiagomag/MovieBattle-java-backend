package br.com.letscode.moviebattle.movie;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieRestControllerTest {

    @Autowired
    private MovieRestService movieRestService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listAll() throws Exception {
        this.mockMvc.perform(get("/movies/todos"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Batman")));
    }

    @Test
    void filmesParaQuizzTeste() throws Exception {
        this.mockMvc.perform(get("/movies?name=Blade"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Blade")));
    }

    @Test
    void filmesParaQuizzTesteParametroErrado() throws Exception {
        this.mockMvc.perform(get("/movies?nome=Blade"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @Test
    void filmesParaQuizzTesteNomeVazio() throws Exception {
        this.mockMvc.perform(get("/movies?name="))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(String.valueOf(movieRestService.getMovieList()))));
    }

    @Test
    void filmesParaQuizzTesteSemParametro() throws Exception {
        this.mockMvc.perform(get("/movies"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
