FROM maven:alpine
WORKDIR /app
COPY ./ ./
RUN cd ./parent && mvn install
RUN mvn clean install
EXPOSE 8080
CMD mvn spring-boot:run -Djvm.options="-Xms1G -Xmx1G"