package org.nana.app.repository;


import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.enterprise.context.ApplicationScoped;
import org.nana.app.model.Film;
import org.nana.app.model.Film$;
import jakarta.inject.Inject;
import java.util.Optional;
import java.util.stream.Stream;

@ApplicationScoped
public class FilmRepository {
    @Inject
    JPAStreamer jpaStreamer;

    public static final int PAGE_SIZE=20;

    public Optional<Film> getFilm(short filmId){
        return jpaStreamer.stream(Film.class)
                .filter(Film$.filmId.equal(filmId))
                .findFirst();
    }

    public Stream<Film> paged(long page, short minlength){
        return jpaStreamer.stream(Film.class)
                .filter(Film$.length.greaterThan(minlength))
                .sorted(Film$.length)
                .skip(page* PAGE_SIZE)
                .limit(PAGE_SIZE);
    }
}
