# pro-spring-book-lab

## Instructions

Run the spring application: ``

Run the external service: `node externalMovieService.js`

## Features:

### Distributed logging
Log consolidation VS log streaming

- Context id
- Context id hierarchy

- [x] Implement MDC
- [ ] Enrich MDC info in another layer of the system
- [x] External service with feignClient
	- [x] Custom ErrorDecoder
	- [ ] Custom Decoder
	- [ ] [Encoder and ErrorDecoder with JacksonDecoder](https://stackoverflow.com/questions/35853908/how-to-set-custom-jackson-objectmapper-with-spring-cloud-netflix-feign)
	- [ ] Custom Encoder (different formats. eg: xml, soap, json)
	- [x] Add CorrelationId header before request
- [x] Logging with SLF4j as the logging façade and logback as the logging framework
- [x] External service (with nodejs)
- [ ] Send logs to aws cloudwatch
- [ ] Implement ELK in aws to search and present logs
- [ ] Implement wiremock
- [ ] Tests for everything

## Observations

Consider whether you need both logging and feignLoggerLevel FULL for feign client. 


# Generated log

16-02-2020 08:24:30.276 INFO  [http-nio-8080-exec-1] CorrelationId[] --- o.a.c.c.C.[Tomcat].[localhost].[/].log16-02-2020 08:24:30.368 TRACE [http-nio-8080-exec-1] CorrelationId[3aa8181b-e2cd-42a6-a77f-9b3b93086a2d] --- c.g.a.p.movie.MovieController.getAll{
  "timestamp" : "2020-02-16 08:24:30.368",
  "level" : "TRACE",
  "thread" : "http-nio-8080-exec-1",
  "mdc" : {
    "CorrelationId" : "3aa8181b-e2cd-42a6-a77f-9b3b93086a2d"
  },
  "logger" : "com.github.augustovictor.prospringbooklab.movie.MovieController",
  "message" : "Request to '/movies' received successfully",
  "context" : "default"
}
```

If you keep making API calls you'd get different `CorrelationId` results. However when we provide a `CorrelationId` it will be used instead. As shown below:
```
curl -i http://localhost:8080/movies -H "CorrelationId: cf852cc9-017a-439c-b7d9-437221afe33d"

16-02-2020 08:27:28.904 TRACE [http-nio-8080-exec-2] CorrelationId[cf852cc9-017a-439c-b7d9-437221afe33d] --- c.g.a.p.movie.MovieController.getAll{
  "timestamp" : "2020-02-16 08:27:28.904",
  "level" : "TRACE",
  "thread" : "http-nio-8080-exec-2",
  "mdc" : {
    "CorrelationId" : "cf852cc9-017a-439c-b7d9-437221afe33d"
  },
  "logger" : "com.github.augustovictor.prospringbooklab.movie.MovieController",
  "message" : "Request to '/movies' received successfully",
  "context" : "default"
}
```

External application request flow:
- Implement FeignClient
- Create an endpoint to call the external service
- [externalMovieService.js](./externalMovieService.js)

### Demo:


Requesting a resource from our spring application, which will in turn make a request to the external service

```
curl -i http://localhost:8080/movies/external

# SPRING APPLICATION RESULT

HTTP/1.1 200
Content-Type: application/json
Transfer-Encoding: chunked
Date: Sun, 16 Feb 2020 12:13:10 GMT

[{"title":"movie A","description":"description for movie A from NODEJS APP"},{"title":"movie B","description":"description for movie B from NODEJS APP"}]

# SPRING APPLICATION LOGS

16-02-2020 09:22:32.548 INFO  [http-nio-8080-exec-1] CorrelationId[] --- o.a.c.c.C.[Tomcat].[localhost].[/].log16-02-2020 09:22:32.710 TRACE [http-nio-8080-exec-1] CorrelationId[8e007dcd-3f01-4640-a703-8100474e9f5d] --- c.g.a.p.movie.MovieController.fetchAllExternal{
  "timestamp" : "2020-02-16 09:22:32.710",
  "level" : "TRACE",
  "thread" : "http-nio-8080-exec-1",
  "mdc" : {
    "CorrelationId" : "8e007dcd-3f01-4640-a703-8100474e9f5d"
  },
  "logger" : "com.github.augustovictor.prospringbooklab.movie.MovieController",
  "message" : "Request to '/movies/external' received successfully",
  "context" : "default"
}
```

```
# EXTERNAL SERVICE LOGS (HEADERS ONLY)

{ correlationid: '8e007dcd-3f01-4640-a703-8100474e9f5d',
  accept: '*/*',
  'user-agent': 'Java/1.8.0_192',
  host: 'localhost:3000',
  connection: 'keep-alive' }
```

Note the `corellationId` is generated and passed on to the external service

Now let's try the same, but providing a `CorrelationId` to our Spring application. This will emulate that our Spring application is one of the services in a request chain, and received a `CorrelationId` from another service

```
curl -i http://localhost:8080/movies/external -H "CorrelationId: 49b300b9-739c-4dd1-836d-3e4b998ef3d8"

# SPRING APPLICATION RESULT

HTTP/1.1 200
Content-Type: application/json
Transfer-Encoding: chunked
Date: Sun, 16 Feb 2020 12:26:42 GMT

[{"title":"movie A","description":"description for movie A from NODEJS APP"},{"title":"movie B","description":"description for movie B from NODEJS APP"}]

# SPRING APPLICATION LOGS

16-02-2020 09:26:42.909 TRACE [http-nio-8080-exec-2] CorrelationId[49b300b9-739c-4dd1-836d-3e4b998ef3d8] --- c.g.a.p.movie.MovieController.fetchAllExternal{
  "timestamp" : "2020-02-16 09:26:42.909",
  "level" : "TRACE",
  "thread" : "http-nio-8080-exec-2",
  "mdc" : {
    "CorrelationId" : "49b300b9-739c-4dd1-836d-3e4b998ef3d8"
  },
  "logger" : "com.github.augustovictor.prospringbooklab.movie.MovieController",
  "message" : "Request to '/movies/external' received successfully",
  "context" : "default"
}
```

```
# EXTERNAL SERVICE LOGS (HEADERS ONLY)

{ correlationid: '49b300b9-739c-4dd1-836d-3e4b998ef3d8',
  accept: '*/*',
  'user-agent': 'Java/1.8.0_192',
  host: 'localhost:3000',
  connection: 'keep-alive' }
```

Note the `corellationId` is reused from the initial request made to Spring application and passed on to the external service