package org.nana.app.repository;

import com.speedment.jpastreamer.application.JPAStreamer;
import com.speedment.jpastreamer.projection.Projection;
import com.speedment.jpastreamer.streamconfiguration.StreamConfiguration;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
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

    public Stream<Film> getFilms(short minlength){
        return jpaStreamer.stream(Film.class)
                .filter(Film$.length.greaterThan(minlength))
                .sorted(Film$.length);
    }

    public Stream<Film> paged(long page, short minlength){
        return jpaStreamer.stream(Projection.select(Film$.filmId,
                        Film$.title ,Film$.length))
                .filter(Film$.length.greaterThan(minlength))
                .sorted(Film$.length)
                .skip(page* PAGE_SIZE)
                .limit(PAGE_SIZE);
    }

    public Stream<Film> actors(String startsWith, short minlength){
        final StreamConfiguration<Film> sc =
                StreamConfiguration.of(Film.class).joining(Film$.actors);
        return jpaStreamer.stream(sc)
                .filter(Film$.title.startsWith(startsWith).and(Film$.length.greaterThan(minlength)))
                .sorted(Film$.length.reversed());
    }
    @Transactional
    public void updateRentalRate(short minlenght,Float rentalRate) {
        jpaStreamer.stream(Film.class)
                .filter(Film$.length.greaterThan(minlenght))
                .forEach(f -> {
                    f.setRentalRate(rentalRate);
                });
    }
}
