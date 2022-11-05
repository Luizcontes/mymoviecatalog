package contes.moviecatalogservice.resources;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import contes.moviecatalogservice.models.CatalogItem;
import contes.moviecatalogservice.models.Movie;
import contes.moviecatalogservice.models.Rating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {


        List<Rating> ratings = Arrays.asList(
            new Rating("1234", 4),
            new Rating("5678", 5)
            );
            
        return ratings.stream().map( rating -> {
            // Movie movie = restTemplate.getForObject("http://127.0.0.1:8081/movies/" + rating.getMoviedId(),Movie.class);

            Movie movie = webClientBuilder.build()
                .get()
                .uri("http://127.0.0.1:8081/movies/" + rating.getMoviedId())
                .retrieve()
                .bodyToMono(Movie.class)
                .block();

            return new CatalogItem(movie.getName(), "Teste", rating.getRating());
        })
        .collect(Collectors.toList());
        // get all rated movie IDs
        // for each movie ID, call movie info service and get details
        // put them all together
        
        // return Collections.singletonList(
        //     new CatalogItem("Transformers", "Teste", 4)
        // );
    }
}
