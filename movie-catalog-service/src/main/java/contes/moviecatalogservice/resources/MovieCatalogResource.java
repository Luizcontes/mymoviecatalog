package contes.moviecatalogservice.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import contes.moviecatalogservice.models.CatalogItem;
import contes.moviecatalogservice.models.Movie;
import contes.moviecatalogservice.models.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

        UserRating ratings = restTemplate.getForObject("http://localhost:8082/ratingsdata/user/" + userId, UserRating.class);

        return ratings.getUserRating().stream().map(rating -> {
            Movie movie = restTemplate.getForObject("http://127.0.0.1:8081/movies/" + rating.getMoviedId(),Movie.class);

            return new CatalogItem(movie.getName(), "Teste", rating.getRating());
        })
        .collect(Collectors.toList());

        // get all rated movie IDs
        // for each movie ID, call movie info service and get details
        // put them all together

        // return Collections.singletonList(
        // new CatalogItem("Transformers", "Teste", 4)
        // );
    }
}

// Movie movie = webClientBuilder.build()
//                 .get()
//                 .uri("http://127.0.0.1:8081/movies/" + rating.getMoviedId())
//                 .retrieve()
//                 .bodyToMono(Movie.class)
//                 .block();