FROM maven:alpine
WORKDIR /app
COPY ./ ./
EXPOSE 8080
CMD mvn spring-boot:run -Dspring.profiles.active=dev