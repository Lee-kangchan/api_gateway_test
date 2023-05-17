# 베이스 이미지를 지정합니다.
FROM openjdk:11-jre-slim

# 애플리케이션을 실행할 디렉토리를 지정합니다.
WORKDIR /app

# 애플리케이션 파일을 컨테이너 내부로 복사합니다.
COPY build/libs/apt_gateway_test-0.0.1-SNAPSHOT-plain.jar app.jar

# 애플리케이션 실행 시 사용할 명령어를 지정합니다.
CMD ["java", "-jar", "app.jar"]