package example;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.core.type.Argument;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@MicronautTest(transactional = false)
class MainTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Inject
    BookRepository books;

    @Test
    void savesAndQueriesBooks() {
        books.deleteAll();

        HttpResponse<Book> response = client.toBlocking()
            .exchange(HttpRequest.POST("/books", new Book(null, "The Dispossessed", 387)), Book.class);
        assertEquals(HttpStatus.CREATED, response.status());
        Book saved = response.body();
        assertNotNull(saved.id());

        client.toBlocking().exchange(HttpRequest.POST("/books", new Book(null, "The Lathe of Heaven", 184)));

        List<Book> all = client.toBlocking().retrieve(HttpRequest.GET("/books"), Argument.listOf(Book.class));
        assertEquals(2, all.size());

        Book found = client.toBlocking().retrieve("/books/" + saved.id(), Book.class);
        assertEquals("The Dispossessed", found.title());

        List<Book> longer = client.toBlocking().retrieve(HttpRequest.GET("/books/longer-than/200"), Argument.listOf(Book.class));
        assertEquals(List.of("The Dispossessed"), longer.stream().map(Book::title).toList());
    }
}
