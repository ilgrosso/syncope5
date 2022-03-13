# notturno
Study prototype for Apache Syncope 4.0 Notturno

## Prerequisites

1. Apache Maven
1. JDK 17 installed with `JAVA_HOME` property set
1. GraalVM installed  
    * [download latest CE build](https://github.com/graalvm/graalvm-ce-builds/releases) for Java 17 and your platform
    * uncompress and set `GRAALVM_HOME` property
    * run `$GRAALVM_HOME/bin/gu install native-image`

## How to run

### Spring Boot

As ordinary Spring Boot application:

```
$ mvn spring-boot:run
```

### Executable

As executable application:

```
$ mvn -Pnative package
$ ./target/syncope
```

### [Buildpacks](https://docs.spring.io/spring-boot/docs/current/reference/html/container-images.html#container-images.buildpacks)

Build and run Docker image:

```
$ mvn spring-boot:build-image
$ docker run syncope:4.0.0-SNAPSHOT 
```
