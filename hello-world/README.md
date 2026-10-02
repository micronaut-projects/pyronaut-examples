# Hello World

A minimal Pyronaut HTTP application with two routes: `/hello` and `/hello/{name}`.

## Java

```bash
cd java
pyronaut dev Main.java
```

[`Main.java`](java/Main.java) declares a Micronaut `@Controller`. No dependencies need declaring:
the HTTP server is part of every Pyronaut application.

## Python

```bash
cd python
pyronaut dev main.py
```

[`main.py`](python/main.py) declares the routes as plain functions decorated with `@Get`.

## Try it

```bash
curl http://localhost:8080/hello
curl http://localhost:8080/hello/Pyronaut
```

## Test

```bash
cd java && pyronaut test Main.java -- MainTest.java
```

```bash
cd python && pyronaut test main.py -- test_main.py
```

The tests start the application with `@MicronautTest` / `MicronautTest()` and call it over HTTP.
