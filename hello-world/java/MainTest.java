package example;

import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
class MainTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void helloWorld() {
        assertEquals("Hello World", client.toBlocking().retrieve("/hello"));
    }

    @Test
    void helloName() {
        assertEquals("Hello Pyronaut", client.toBlocking().retrieve("/hello/Pyronaut"));
    }
}
