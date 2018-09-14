FROM maven:alpine
WORKDIR /app
COPY ./ ./
EXPOSE 8080
CMD mvn spring-boot:run
# CMD mvn spring-boot:run  -Xms3G -Xmx3G