from micronaut.http.annotation import Get


@Get("/hello")
def index() -> str:
    return "Hello World"


@Get("/hello/{name}")
def greet(name: str) -> str:
    return f"Hello {name}"
