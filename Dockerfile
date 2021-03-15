FROM adoptopenjdk/openjdk11

ARG JAR_FILE=build/libs/devidea-0.0.1-SNAPSHOT.jar

EXPOSE 8080

# 별칭
ADD ${JAR_FILE} devidea.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/devidea.jar"]
