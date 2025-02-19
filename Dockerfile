FROM eclipse-temurin:17-jdk
COPY build/lib/*.jar app.jar
ARG spring_profiles_active=dev
ENV SPRING_PROFILES_ACTIVE=${spring_profiles_active}
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-Duser.timezone=Asia/Seoul", "app.jar"]