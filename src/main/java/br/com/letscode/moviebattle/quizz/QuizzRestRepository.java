package br.com.letscode.moviebattle.quizz;

import br.com.letscode.moviebattle.quizz.jogadorquizz.JogadorQuizz;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class QuizzRestRepository {

    private String caminho = "src/main/resources/dados/ranking.csv";

    private Path path;

    @PostConstruct
    public void init() {
        try {
            path = Paths.get(String.valueOf(caminho));
            if (!path.toFile().exists()) {
                Files.createFile(path);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void inserirArquivo(List<JogadorQuizz> rankingList) throws IOException {
        Files.deleteIfExists(path);
        init();
        rankingList.sort(Comparator.comparing(JogadorQuizz::getScore));
        for (JogadorQuizz jogadorQuizz : rankingList) {
            write(format(jogadorQuizz), StandardOpenOption.APPEND);
        }
    }

    private void write(String clienteStr, StandardOpenOption option) throws IOException {
        try (BufferedWriter bf = Files.newBufferedWriter(path, option)) {
            bf.flush();
            bf.write(clienteStr);
        }
    }

    public List<JogadorQuizz> listAll() throws IOException {
        List<JogadorQuizz> rankingList;
        try (BufferedReader br = Files.newBufferedReader(path)) {
            rankingList = br.lines().filter(Objects::nonNull).filter(Predicate.not(String::isEmpty)).map(this::convert).collect(Collectors.toList());
        }
        rankingList.sort(Comparator.comparing(JogadorQuizz::getScore));
        return rankingList;
    }

    private String format(JogadorQuizz jogadorQuizz) {
        return String.format("%s;%d\r\n",
                jogadorQuizz.getNome(),
                jogadorQuizz.getScore());
    }

    private JogadorQuizz convert(String linha) {
        StringTokenizer token = new StringTokenizer(linha, ";");
        return JogadorQuizz.builder()
                .nome(token.nextToken())
                .score(Integer.parseInt(token.nextToken()))
                .build();
    }
}