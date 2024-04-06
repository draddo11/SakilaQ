import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.nana.app.model.Film;
import org.nana.app.repository.FilmRepository;

import java.awt.*;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/api/v1")
public class FilmResource {

    @Inject
    FilmRepository filmRepository;
    @GET
    @Path("/film/{filmId}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getFilm(short filmId){
        Optional<Film> film = filmRepository.getFilm(filmId);
        return film.isPresent() ? film.get().getTitle() : "No film was found";
    }

    @GET
    @Path("/pagedFilms/{page}/{minLength}")
    @Produces(MediaType.TEXT_PLAIN)
    public String Paged(Long page,short minLength){
        return filmRepository.paged(page,minLength)
                .map( f-> String.format("%s(%d min)", f.getTitle(), f.getLength()))
                .collect(Collectors.joining("\n"));
    }


    @GET
    @Path("/pagedFilms/{startsWith}")
    @Produces(MediaType.TEXT_PLAIN)
    public String actors(String startsWith){
        return filmRepository.actors(startsWith)
                .map(f-> String.format("%s (%d min): %s",
                        f.getTitle(),
                        f.getLength(),
                        f.getActors().stream()
                                .map( a-> String.format("%s %s", a.getFirstName(), a.getLastName()))
                                .collect(Collectors.joining(""))))
                .collect(Collectors.joining("/n"));

    }


}
