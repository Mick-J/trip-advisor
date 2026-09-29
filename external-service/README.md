## External Services

## How to Run
Download the `externa-service.jar` file from this repo and place it anywhere on your machine.
Navigate to the location of this jar file and run the following command:

```
java -jar external-services.jar --server.port=7070
```

### Running the external service with Docker
A Docker image can be created using a Java 21+ runtime:
```
FROM bellsoft/liberica-openjdk-alpine:21
WORKDIR app
ADD https://github.com/Mick-J/trip-advisor/tree/main/trip-advisor/external-service/external-service.jar
CMD java -jar external-services.jar
```
Build the image:
```
docker build -t external-services .
```

Run the container:
```
docker run -p 7070:7070 external-services
```

The external service provides several endpoints that can be explored through Swagger:

```
http://localhost:7070/swagger-ui/
```