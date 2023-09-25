FROM openjdk:17-jdk-alpine
WORKDIR /
COPY target/harbor-image-version-tag-0.0.1-SNAPSHOT.jar /
EXPOSE 2023

CMD ["java", "-jar", "harbor-image-version-tag-0.0.1-SNAPSHOT.jar", ""]