import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.awt.*;

@Path("/")
public class FilmResource {
    @GET
    @Path("/helloWorld")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(){
        return "Hello World";
    }

    @GET
    @Path("/helloWorld2")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello2(){
        return "Hello World 2";
    }
}
