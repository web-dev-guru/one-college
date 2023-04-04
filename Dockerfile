FROM openjdk:11

WORKDIR /srv/one-college

COPY ./target/one-college-1.0-SNAPSHOT.jar bin/one-college-1.0-SNAPSHOT.jar

EXPOSE 8082

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","bin/one-college-1.0-SNAPSHOT.jar"]
