# Pyronaut Examples

Runnable examples of [Pyronaut](https://github.com/micronaut-projects/pyronaut) applications.

Every example is a direct-source application: there is no `pyproject.toml` or build file.
Each one lives in its own directory with a Java and a Python version of the same application:

```
<Example>/
├── README.md
├── java/
│   ├── Main.java       # the application
│   └── MainTest.java   # its tests
└── python/
    ├── main.py         # the application
    └── test_main.py    # its tests
```

Dependencies and configuration are declared inline, with `@Dependency`/`@AppConfig` in Java and
`Dependency(...)`/`AppConfig(...)` in Python. Pyronaut resolves them on the first run and caches
them in `__pyronaut__`.

## Examples

| Example | Description |
| --- | --- |
| [hello-world](hello-world) | A minimal HTTP application |
| [data-jdbc-mysql](data-jdbc-mysql) | Micronaut Data JDBC with MySQL |
| [data-jdbc-oracle](data-jdbc-oracle) | Micronaut Data JDBC with Oracle Database |

## Running an example

Install Pyronaut (see the [Pyronaut documentation](https://github.com/micronaut-projects/pyronaut)), then
from an example's `java` or `python` directory:

```bash
pyronaut dev Main.java
```

```bash
pyronaut dev main.py
```

Run the tests:

```bash
pyronaut test Main.java -- MainTest.java
```

```bash
pyronaut test main.py -- test_main.py
```

The database examples need Docker: Pyronaut infers [Micronaut Test Resources](https://micronaut-projects.github.io/micronaut-test-resources/latest/guide/)
from the declared driver and starts a database container for `pyronaut dev` and `pyronaut test`.

## Adding an example

Create a directory with a kebab-case name containing a `README.md`, `java/Main.java` with
`java/MainTest.java`, and `python/main.py` with `python/test_main.py`. CI discovers new examples
automatically and tests each one with [setup-pyronaut](https://github.com/micronaut-projects/setup-pyronaut).
