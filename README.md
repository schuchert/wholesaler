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
java -jar target/wholesaler.jar
```

In both cases, it uses port 8080, so poing your browser to:
(localhost:8080/swagger-ui.html)

## Running on your own [Pivotal Web Services][pws]

Log in.

```bash
cf login -a https://api.run.pivotal.io
```

Target your org / space.

```bash
cf target -o myorg -s myspace
```

Sign up for a cleardb instance.

```bash
cf create-service cleardb spark mysql
```

Build the app.

```bash
brew install maven
mvn package
```

Push the app. Its manifest assumes you called your ClearDB instance 'mysql'.

```bash
cf push -n mysubdomain
```

Export the test host

```bash
export HOST=http://mysubdomain.cfapps.io
```

Now follow the [interaction instructions][interaction].

## Running locally

The following assumes you have a working Java 1.8 SDK installed.

Install and start mysql:

```bash
brew install mysql
mysql.server start
mysql -u root
```

Create a database user and table in the MySQL REPL you just opened:

```
bin/create_db_and_user.sh
```

Start the application server from your IDE or the command line:

```bash
mvn spring-boot:run
```

Export the test host

```bash
export HOST=http://localhost:8080
```

Now follow the [interaction instructions][interaction].

[acceptance-test]:https://github.com/cloudfoundry-samples/pong_matcher_acceptance
[pws]:https://run.pivotal.io
[interaction]:https://github.com/cloudfoundry-samples/pong_matcher_rails/blob/master/README.md#interaction-instructions
