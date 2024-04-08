import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.nana.app.model.Film;
import org.nana.app.repository.FilmRepository;

import java.util.Optional;

import static io.smallrye.common.constraint.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

//unit test
@QuarkusTest
public class FilmRepositoryTest {
    @Inject
    FilmRepository filmRepository;

    @Test
    public void test(){
        Optional<Film>  film = filmRepository.getFilm((short)5);
        assertTrue(film.isPresent());
        assertEquals("AFRICAN EGG" , film.get().getTitle());

    }
}
