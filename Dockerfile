FROM eclipse-temurin:17-jdk
COPY build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java -jar -Dspring.profiles.active=$SPRING_PROFILES_ACTIVE -Duser.timezone=Asia/Seoul app.jar"]