package com.github.augustovictor.prospringbooklab.movie

import com.github.augustovictor.prospringbooklab.validator.MovieValidationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/movies")
class MovieController(
        private val movieValidationService: MovieValidationService
) {
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
}