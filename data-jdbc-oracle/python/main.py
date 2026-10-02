from pyronaut.build import AppConfig, Dependency

# Micronaut Data JDBC, a connection pool and the Oracle JDBC driver.
Dependency(group="io.micronaut.data", module="micronaut-data-jdbc")
Dependency(group="io.micronaut.sql", module="micronaut-jdbc-hikari")
Dependency(group="com.oracle.database.jdbc", module="ojdbc17")
Dependency(group="io.micronaut.data", module="micronaut-data-processor", scope=Dependency.Scope.BUILD)
# No URL or credentials: Test Resources starts an Oracle Database Free container and supplies them.
AppConfig(name="datasources.default.db-type", value="oracle")
AppConfig(name="datasources.default.dialect", value="ORACLE")
AppConfig(name="datasources.default.schema-generate", value="CREATE_DROP")

from dataclasses import dataclass
from typing import Annotated, Protocol

from jakarta.inject import Inject
from micronaut.data.annotation import GeneratedValue, Id, MappedEntity
from micronaut.data.jdbc.annotation import JdbcRepository
from micronaut.data.model.query.builder.sql import Dialect
from micronaut.data.repository import CrudRepository
from micronaut.http import HttpResponse
from micronaut.http.annotation import Body, Get, Post
from micronaut.serde.annotation import Serdeable


@Serdeable
@MappedEntity
@dataclass
class Book:
    id: Annotated[int | None, Id, GeneratedValue] = None
    title: str = ""
    pages: int = 0


@JdbcRepository(dialect=Dialect.ORACLE)
class BookRepository(CrudRepository[Book, int], Protocol):
    def findByPagesGreaterThan(self, pages: int) -> list[Book]: ...


books: Annotated[BookRepository, Inject]


@Get("/books")
def list_books() -> list[Book]:
    return books.findAll()


@Get("/books/{id}")
def show_book(id: int) -> HttpResponse[Book]:
    book = books.findById(id).orElse(None)
    return HttpResponse.ok(book) if book is not None else HttpResponse.notFound()


@Get("/books/longer-than/{pages}")
def longer_than(pages: int) -> list[Book]:
    return books.findByPagesGreaterThan(pages)


@Post("/books")
def save_book(book: Annotated[Book, Body]) -> HttpResponse[Book]:
    return HttpResponse.created(books.save(book))
