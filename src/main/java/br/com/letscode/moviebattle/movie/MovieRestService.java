package br.com.letscode.moviebattle.movie;

import br.com.letscode.moviebattle.client.MovieDetailRestRepository;
import br.com.letscode.moviebattle.client.MovieMinimalRestRepository;
import br.com.letscode.moviebattle.client.ResultSearch;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Service
@RequiredArgsConstructor
public class MovieRestService {

    private Set<Movie> duplaFilmes;
    private List<Movie> movieList;

    private final MovieRestRepository movieRepository;
    private final MovieMinimalRestRepository minimalRestRepository;
    private final MovieDetailRestRepository detailRestRepository;

    public List<Movie> listarTodos() throws IOException {
        return movieRepository.listAll();
    }

    private Movie filmeAleatorio() throws IOException  {
        var movies = listarTodos();
        var random = new Random();
        return movies.get(random.nextInt(movies.size()));
    }

    public Set<Movie> escolherFilme() throws IOException {
        duplaFilmes = new LinkedHashSet<>();
        duplaFilmes.add(filmeAleatorio());
        duplaFilmes.add(filmeAleatorio());
        return duplaFilmes;
    }

    public List<Movie> salvarFilmes(List<String> name) throws IOException {
        var resultList = name.stream()
                .map(this.minimalRestRepository::search)
                .collect(Collectors.toList());
        var reduce = reduce(resultList);
        movieList = new ArrayList<>();
        for(int i = 0; i < reduce.getResultList().size(); i++) {
            var movie = Movie.builder()
                    .title(reduce.getResultList().get(i).getTitle())
                    .year(reduce.getResultList().get(i).getYear())
                    .imdbId(reduce.getResultList().get(i).getImdbId())
                    .rating(detailRestRepository.detail(reduce.getResultList().get(i).getImdbId()).getRating())
                    .build();
            movieRepository.inserirArquivo(movie);
            movieList.add(movie);
        }
        return movieList;
    }

    private ResultSearch reduce(List<ResultSearch> resultList) {
        return ResultSearch.builder()
                .resultList(resultList.stream().map(ResultSearch::getResultList).flatMap(Collection::stream).collect(
                        Collectors.toList()))
                .response(resultList.stream().map(ResultSearch::getResponse).reduce(true, Boolean::logicalAnd))
                .total(resultList.stream().map(ResultSearch::getTotal).reduce(0, Integer::sum))
                .build();
    }
}