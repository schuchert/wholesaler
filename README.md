# CD Workshop: Wholesaler

This application makes up part of the CD workshop infrastructure.  This README will someday contain more information about how this application interacts with others in the workshop.

It has an [acceptance test suite][acceptance-test] you might like to look at.

**Note**: We highly recommend that you use the latest versions of any software required by this sample application. For example, make sure that you are using the most recent verion of maven.

## Running on the workshop [Pivotal Web Services][pws]
Gitlab is deploying the application, see `.gitlab-ci.yml`

## Running this app locally
There are two options: in an IDE or at the command line.

To run it in the IDE, run Application.java.

To run at the command line, from the directory that contains this README.md:
```bash
mvn package
java -jar target/wholesaler.jar
```

In both cases, it uses port 8080, so point your browser to: [Swagger UI](http://localhost:8080/swagger-ui.html)

## Running on your own [Pivotal Web Services][pws]

Log in.

```bash
cf login -a https://api.run.pivotal.io
```

Target your org / space.

```bash
cf target -o myorg -s myspace
```

Build the app.

```bash
mvn package
```

Push the app. Its manifest assumes you called your ClearDB instance 'mysql'.

```bash
cf push -n mysubdomain
```

## Running locally

The following assumes you have a working Java 1.8 SDK installed.

Start the application server from your IDE or the command line:

```bash
mvn spring-boot:run
```

Finally, point your browser to the [Swagger UI](http://localhost:8080/swagger-ui.html).
