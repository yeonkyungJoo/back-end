FROM adoptopenjdk/openjdk11

ARG JAR_FILE=build/libs/devidea*.jar

# 별칭
ADD ${JAR_FILE} devidea.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/devidea.jar"]
