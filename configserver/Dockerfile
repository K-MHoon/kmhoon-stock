# stage 1
# openJDK 이미지 생성
FROM openjdk:17-slim as build

LABEL maintainer="K-MHoon"

# 애플리케이션 jar 파일 위치 변수를 전달한다.
ARG JAR_FILE

# 컨테이너에 jar 파일을 복사한다.(추가)
COPY ${JAR_FILE} app.jar

# unpackage jar file. 디렉터리를 생성하고 해당 디렉터리로 이동해서 app.jar 파일 압축을 해제한다.
# 레이어가 추출되면 전체 애플리케이션 JAR 대신 이들 레이어만 포함된 또 다른 이미지를 생성한다.
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf /app.jar)

# stage2
FROM openjdk:17-slim

# 컨테이너의 마운트 지점(/tmp)을 만든다.
# 동일한 이미지를 사용해서 새 컨테이너를 만들 때 이전 볼륨과 격리되는 새 볼륨을 생성한다.
VOLUME /tmp

# stage 1에서 build라고 명명된 첫 이미지에서 여러 레이어를 복사한다.
ARG DEPENDENCY=/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

# 애플리케이션을 실행한다.
# 컨테이너가 생성될 때 이미지의 라이선싱 서비스를 실행 대상으로 지정한다.
ENTRYPOINT ["java", "-cp", "app:app/lib/*", "com.kmhoon.configserver.ConfigurationServerApplication"]


# Docker Layered Jar

#FROM openjdk:17-slim as build
#WORKDIR application
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} application.jar
#RUN java -Djarmode=layertools -jar application.jar extract
#
#FROM openjdk:17-slim
#WORKDIR application
#COPY --from=build application/dependencies/ ./
#COPY --from=build application/spring-boot-loader/ ./
#COPY --from=build application/snapshot-dependencies/ ./
#COPY --from=build application/application/ ./
#ENTRYPOINT ["java",  "org.springframework.boot.loader.JarLauncher" ]