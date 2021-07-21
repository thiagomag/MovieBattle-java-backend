package br.com.letscode.moviebattle.jogador;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class JogadorRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void login() throws Exception {
        var jogador = new Jogador("teste","teste");
        this.mockMvc.perform(get("/jogador")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(jogador)))
                .andExpect(status().isAccepted())
                .andDo(print());
    }

    @Test
    void loginCredenciaisIncorretas() throws Exception {
        var jogador = new Jogador("teste","teste20");
        this.mockMvc.perform(get("/jogador")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(jogador)))
                .andExpect(content().string(containsString("")))
                .andDo(print());
    }

    @Test
    void cadastroJogador() throws Exception {
        var jogador = new Jogador("teste","teste");
        this.mockMvc.perform(post("/jogador")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(jogador)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void cadastroJogadorSenhaForaDoPadrao() throws Exception {
        var jogador = new Jogador("teste","123");
        this.mockMvc.perform(post("/jogador")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(jogador)))
                .andDo(print())
                .andExpect(content().string(containsString(String.valueOf(HttpStatus.BAD_REQUEST))));
    }

    @Test
    void cadastroJogadorUserForaDoPadrao() throws Exception {
        var jogador = new Jogador("user##","123");
        this.mockMvc.perform(post("/jogador")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(jogador)))
                .andDo(print())
                .andExpect(content().string(containsString(String.valueOf(HttpStatus.BAD_REQUEST))));
    }
}

