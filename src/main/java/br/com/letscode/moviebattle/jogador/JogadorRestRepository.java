package br.com.letscode.moviebattle.jogador;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JogadorRestRepository {
    List<Jogador> jogadoresList;

    private final String path = "src/main/resources/dados/jogadores.csv";

    @PostConstruct
    public void loadJogadoresList() throws IOException {
        jogadoresList = new ArrayList<>();
        CSVReader reader = new CSVReader(new FileReader(path));
        String[] line;
        while ((line = reader.readNext()) != null) {
            var jogador = new Jogador();
            jogador.setUser(line[0]);
            jogador.setPassword(line[1]);
            this.jogadoresList.add(jogador);
        }
    }

    public Boolean createJogador(Jogador jogador) throws IOException, NoSuchAlgorithmException {
        if (verificaJogador(jogador)){
            return false;
        }
        CSVWriter writer = new CSVWriter(new FileWriter(path,true));
        jogador.setPassword(getSHA1Hex(jogador.getPassword()));
        writer.writeNext(new String[]{jogador.getUser(), jogador.getPassword()});
        writer.flush();
        return true;
    }

    String getSHA1Hex(String string) throws NoSuchAlgorithmException {
        MessageDigest algorithm = MessageDigest.getInstance("SHA-1");
        byte[] passwordHash = algorithm.digest(string.getBytes(StandardCharsets.UTF_8));
        var stringHex = new StringBuilder();
        for (byte b : passwordHash) {
            stringHex.append(String.format("%02X", 0xFF & b));
        }
        return stringHex.toString();
    }

    private boolean verificaJogador(Jogador jogador){
        if (jogador.getUser().length() < 5 || jogador.getUser().length() > 10
                || jogador.getPassword().length() < 4 || jogador.getPassword().length() > 8){
            return true;
        }
        for (int i = 0; i < jogador.getUser().length(); i++){
            if (!Character.isAlphabetic(jogador.getUser().charAt(i))
                    && !Character.isDigit(jogador.getUser().charAt(i))){
                return true;
            }
        }
        for (int i = 0; i < jogador.getPassword().length(); i++){
            if (!Character.isAlphabetic(jogador.getPassword().charAt(i))
                    && !Character.isDigit(jogador.getPassword().charAt(i))){
                return true;
            }
        }
        return false;
    }
}
