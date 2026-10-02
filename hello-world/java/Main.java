package example;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;

@Controller("/hello")
class HelloController {

    @Get
    String index() {
        return "Hello World";
    }

    @Get("/{name}")
    String greet(@PathVariable String name) {
        return "Hello " + name;
    }
}
