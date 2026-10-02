# Micronaut Data JDBC with Oracle Database

A books REST API backed by Oracle Database through [Micronaut Data JDBC](https://micronaut-projects.github.io/micronaut-data/latest/guide/#jdbc).
It defines a `Book` entity, a `BookRepository` with a derived finder (`findByPagesGreaterThan`), and a controller.

The source declares Micronaut Data JDBC, Hikari and the Oracle Database driver inline, together with the
datasource dialect, but deliberately omits the JDBC URL and credentials. Pyronaut infers
[Micronaut Test Resources](https://micronaut-projects.github.io/micronaut-test-resources/latest/guide/)
from that and starts an Oracle Database Free container for `pyronaut dev` and `pyronaut test`, so Docker must be running.
The schema is created on startup with `schema-generate = CREATE_DROP`.

## Java

```bash
cd java
pyronaut dev Main.java
```

See [`Main.java`](java/Main.java).

## Python

```bash
cd python
pyronaut dev main.py
```

See [`main.py`](python/main.py). The repository is a `Protocol` extending `CrudRepository`;
Micronaut Data implements it at build time, exactly as for the Java interface.

## Try it

```bash
curl -X POST -H 'Content-Type: application/json' -d '{"title":"The Dispossessed","pages":387}' http://localhost:8080/books
curl -X POST -H 'Content-Type: application/json' -d '{"title":"The Lathe of Heaven","pages":184}' http://localhost:8080/books
curl http://localhost:8080/books
curl http://localhost:8080/books/longer-than/200
```

## Test

```bash
cd java && pyronaut test Main.java -- MainTest.java
```

```bash
cd python && pyronaut test main.py -- test_main.py
```
