FROM openjdk:21-jdk-bookworm
WORKDIR /
COPY target/harbor-image-version-tag-0.0.1-SNAPSHOT.jar /
EXPOSE 1840

CMD ["java", "-jar", "harbor-image-version-tag-0.0.1-SNAPSHOT.jar"]