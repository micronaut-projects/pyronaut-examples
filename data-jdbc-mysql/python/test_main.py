from typing import Annotated

import requests

from jakarta.inject import Inject
from micronaut.context import ApplicationContext
from micronaut.test.extensions.junit5.annotation import MicronautTest
from pyronaut.build import Dependency

Dependency(
    group="io.micronaut.pyronaut",
    module="micronaut-pyronaut-requests",
    scope=Dependency.Scope.TEST,
)

MicronautTest(transactional=False)

context: Annotated[ApplicationContext, Inject]


def client():
    return requests.with_context(context)


def test_saves_and_queries_books():
    response = client().post("/books", json={"title": "The Dispossessed", "pages": 387})
    assert response.status_code == 201
    saved = response.json()
    assert saved["id"] is not None

    client().post("/books", json={"title": "The Lathe of Heaven", "pages": 184})

    titles = {book["title"] for book in client().get("/books").json()}
    assert titles == {"The Dispossessed", "The Lathe of Heaven"}

    assert client().get(f"/books/{saved['id']}").json()["title"] == "The Dispossessed"

    longer = client().get("/books/longer-than/200").json()
    assert [book["title"] for book in longer] == ["The Dispossessed"]
