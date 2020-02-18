package com.github.augustovictor.prospringbooklab.movie

import com.github.augustovictor.prospringbooklab.CustomLogWrapper
import com.github.augustovictor.prospringbooklab.infra.ExternalMovieClient
import com.github.augustovictor.prospringbooklab.validator.MovieValidationService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/movies")
class MovieController(
        private val movieValidationService: MovieValidationService,
        private val externalMovieClient: ExternalMovieClient
) {

    private val logger = LoggerFactory.getLogger(javaClass)
    private val customLogger = CustomLogWrapper(javaClass)

    @GetMapping
    fun getAll(): List<Movie> {
        logger.trace("Request to '/movies' received successfully")
        customLogger.info("Request to '/movies' received successfully")

        return listOf(
                Movie("movie 1", "description for movie 1"),
                Movie("movie 2", "description for movie 2"),
                Movie("movie 3", "description for movie 3"),
                Movie("movie 4", "description for movie 4"),
                Movie("movie 5", "description for movie 5")
        )
    }

    @PostMapping
    fun creteMovie(@RequestBody movie: Movie): ResponseEntity<*> {
        val constraintViolations = movieValidationService.validateMovie(movie)

        if (constraintViolations.isNotEmpty()) {
            val constraintViolationResponse = constraintViolations.map {
                "with for property: '${it.propertyPath}' the value '${it.invalidValue}' with error: ${it.message}"
            }
            return ResponseEntity.badRequest().body(constraintViolationResponse)
        }

        return ResponseEntity.ok().body(movie)
    }

    @GetMapping("/external")
    fun fetchAllExternal() = externalMovieClient.fetchAll()

    @GetMapping("/bad-request")
    fun badRequest() = externalMovieClient.badRequest()
}