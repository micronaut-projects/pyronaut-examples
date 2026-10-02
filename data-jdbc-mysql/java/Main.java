package example;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.runtime.Micronaut;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.serde.annotation.Serdeable;
import pyronaut.build.AppConfig;
import pyronaut.build.Dependency;

import java.util.List;
import java.util.Optional;

// Micronaut Data JDBC, a connection pool and the MySQL driver.
@Dependency(group = "io.micronaut.data", module = "micronaut-data-jdbc")
@Dependency(group = "io.micronaut.sql", module = "micronaut-jdbc-hikari")
@Dependency(group = "com.mysql", module = "mysql-connector-j")
@Dependency(group = "io.micronaut.data", module = "micronaut-data-processor", scope = Dependency.Scope.BUILD)
// No URL or credentials: Test Resources starts a MySQL container and supplies them.
@AppConfig(name = "datasources.default.db-type", value = "mysql")
@AppConfig(name = "datasources.default.dialect", value = "MYSQL")
@AppConfig(name = "datasources.default.schema-generate", value = "CREATE_DROP")
class Config { }

@Serdeable
@MappedEntity
record Book(@Id @GeneratedValue @Nullable Long id, String title, int pages) {
}

@JdbcRepository(dialect = Dialect.MYSQL)
interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findByPagesGreaterThan(int pages);
}

@Controller("/books")
@ExecuteOn(TaskExecutors.BLOCKING)
class BookController {
    private final BookRepository books;

    BookController(BookRepository books) {
        this.books = books;
    }

    @Get
    List<Book> list() {
        return books.findAll();
    }

    @Get("/{id}")
    Optional<Book> show(Long id) {
        return books.findById(id);
    }

    @Get("/longer-than/{pages}")
    List<Book> longerThan(int pages) {
        return books.findByPagesGreaterThan(pages);
    }

    @Post
    HttpResponse<Book> save(@Body Book book) {
        return HttpResponse.created(books.save(book));
    }
}
