# Assignment 3

## Running the project

You can run the project in this way:

1. **server**: by executing `ant execute.server` from the terminal in the root directory of the project
2. **client**: by executing `ant execute.client` from the terminal in the root directory of the project

## Configuration

There is a `Resource` folder which contains the sqlite db and a sql script to generate it if it is needed.

---

You can change some configuration: server port (will be used both by the server and the client to test it), client port (will be used only by the client with the porpuses of testing others webserver) and db URL in the file `src/config.properties`.

## About the project

The packages tree reflex the mind model.
Below you can find a more precise description of each package:

* `introsde.db.common`: contains common codes helpful for doing database stuffs (like connection, etc...)
* `introsde.jaxb.common`: contains common codes helpful for JAXB stuffs (like custom adapters, etc...)
* `introsde.assignment3.ws`: contains the real project code. More specifically, in the root of this package you can find the JAX-WS service declaration (`LifeStatus.java`) and implementation (`LifeStatusImpl.java`)

	1. `introsde.assignment3.ws.model`: contains the JAXB annotated classes, which represents our model (used both for exposing object via SOAP and for serialisation in the Databes via the DAOs)
	2. `introsde.assignment3.ws.dao`: contains the DAOs classes for accessing and storing our model in a SQLite database
	3. `introsde.assignment3.ws.server`: contains a class that publish the endpoints of my JAX-WS service
	4. `introsde.assignment3.ws.client`: contains a client class that invokes the exposed SOAP methods of my JAX-WS service