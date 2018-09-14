FROM maven:alpine
WORKDIR /app
COPY ./ ./
EXPOSE 8080
CMD mvn spring-boot:run -Djvm.options=-Xmx512m
# CMD mvn spring-boot:run  -Xms3G -Xmx3G