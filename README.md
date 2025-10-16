# Syncope 5
Study prototype for Apache Syncope 5.0

## Prerequisites

1. Apache Maven
1. JDK 21 installed with `JAVA_HOME` property set
1. GraalVM installed  
    * [download latest CE build](https://github.com/graalvm/graalvm-ce-builds/releases) for Java 25 and your platform
    * uncompress and set `GRAALVM_HOME` property

## How to run

### Spring Boot

As ordinary Spring Boot application:

```
$ mvn spring-boot:run
```

### [Executable](https://docs.spring.io/spring-boot/docs/3.0.0-RC2/reference/htmlsingle/#native-image.developing-your-first-application.native-build-tools.maven)

As executable application:

```
$ mvn -Pnative native:compile
$ ./target/syncope
```

### [Buildpacks](https://docs.spring.io/spring-boot/docs/3.0.0-RC2/reference/htmlsingle/#native-image.developing-your-first-application.buildpacks)

Build Docker image and run as Docker container:

```
$ mvn -Pnative spring-boot:build-image
$ docker run syncope:4.0.0-SNAPSHOT 
```

## How to test

Just browse http://localhost:8080/v3/api-docs

### Spring Boot

```
$ mvn test
```

### Executable

```
$ mvn -PnativeTest test
```
