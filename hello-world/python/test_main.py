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

MicronautTest()

context: Annotated[ApplicationContext, Inject]


def client():
    return requests.with_context(context)


def test_hello_world():
    response = client().get("/hello")
    assert response.status_code == 200
    assert response.text == "Hello World"


def test_hello_name():
    response = client().get("/hello/Pyronaut")
    assert response.text == "Hello Pyronaut"
