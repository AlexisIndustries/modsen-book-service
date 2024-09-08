FROM gradle:jdk21-alpine AS build

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean bootJar

FROM amazoncorretto:21-alpine3.20

RUN apk --no-cache add bash curl

EXPOSE 8888

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]