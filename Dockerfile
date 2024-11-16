FROM openjdk:8
COPY build/lib/*.jar app.jar
ARG spring_profiles_active=local
ARG jdbc_database_url=url
ENV JDBC_DATABASE_URL ${jdbc_database_url}
ENV SPRING_PROFILES_ACTIVE ${spring_profiles_active}

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-Duser.timezone=Asia/Seoul", "app.jar"]