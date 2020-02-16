package com.github.augustovictor.prospringbooklab.infra

import com.github.augustovictor.prospringbooklab.movie.Movie
import feign.*
import feign.codec.ErrorDecoder
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import java.io.IOException


@FeignClient(name = "externalMovieClient", url = "http://localhost:3000/movies", configuration = [DefaultCustomFeingClientConfiguration::class])
interface ExternalMovieClient {
    @GetMapping
    fun fetchAll(): List<Movie>

    @GetMapping("/bad-request")
    fun badRequest(): Any

    // TODO: Implement custom encoder to log

    // TODO: Implement result handler to log

    // TODO: Log to stdout as json inside the "message" field

    // TODO: Implement tests for happy path and edge cases

    // TODO: Implement wiremock

    // TODO: Send logs to aws cloudwatch to see how logs are presented

    // TODO: Implement ELK to search logs
}

@Configuration
class DefaultCustomFeingClientConfiguration {
    @Bean
    fun feignLoggerLevel(): Logger.Level {
        return Logger.Level.FULL
    }

    @Bean
    fun errorDecoder(): ErrorDecoder = DefaultCustomErrorDecoder()
}

/**
 * @param methodKey will contain a Feign client class name and a method name
 * @param response  will allow you to access HTTP status code, Body of HTTP Response and also the Request object. You can use these details when handing an error message and preparing a response.
 */

class DefaultCustomErrorDecoder : ErrorDecoder {
    private val defaultErrorDecoder = ErrorDecoder.Default()
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun decode(methodKey: String, response: Response): Exception {
        if (response.status() in 400..499) {
            try {
                val bodyData = Util.toByteArray(response.body().asInputStream())
                val responseBody = String(bodyData)
                logger.error("Status ${response.status()} | methodKey $methodKey")
                return ExternalCommunicationFailureException(responseBody)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        return defaultErrorDecoder.decode(methodKey, response)
    }
}

class ExternalCommunicationFailureException(message: String) : Exception(message)

@Component
class CustomRequestInterceptor : RequestInterceptor {
    override fun apply(requestTemplate: RequestTemplate) {
        requestTemplate.header("CorrelationId", MDC.get("CorrelationId"))
    }
}