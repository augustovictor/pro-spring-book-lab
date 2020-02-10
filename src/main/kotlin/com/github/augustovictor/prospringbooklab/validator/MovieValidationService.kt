package com.github.augustovictor.prospringbooklab.validator

import com.github.augustovictor.prospringbooklab.movie.Movie
import org.springframework.stereotype.Service
import javax.validation.ConstraintViolation
import javax.validation.Validator

@Service
class MovieValidationService(
        private val validator: Validator
) {
    fun validateMovie(movie: Movie): Set<ConstraintViolation<Movie>> {
        return validator.validate(movie)
    }
}