# Assignment 2

## Running the project

I have tried to build a build.xml at file, but the standalone server have some trouble in executing, so:
import the project in Eclipse and run:

- **server** run the `StandaloneServer.java` on package `introsde.assignment2.rest.server`
- **client for my server** run the `TestMyWebServer.java` on package `introsde.assignment2.rest.client`
- **client for other server** run the `TestOthersWebServer.java` on package `introsde.assignment2.rest.client`

__Please note__ that the `TestMyWebServer.java` and `TestOthersWebServer.java` share the same code, but the first does also create/delete/put stuff, while the second instead use only the get method.

__Please note__ that there is a `Resource` folder which contains the sqlite db and a sql script to generate it if it is needed.

__Please note__ that you can change some configuration: server port (will be used both by the server and the client to test it), client port (will be used only by the client with the porpuses of testing others webserver) and db URL in the file `config.properties`.
