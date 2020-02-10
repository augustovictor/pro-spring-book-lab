package com.github.augustovictor.prospringbooklab.validator

import com.github.augustovictor.prospringbooklab.movie.Movie
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class CheckMovieValidator : ConstraintValidator<CheckMovie, Movie> {
    override fun isValid(movie: Movie, constraintValidatorContext: ConstraintValidatorContext): Boolean {
        var isValid = true

        if (movie.title.length <= 2) {
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate("{app.constraints.CheckMovie.message}")
                    .addPropertyNode("title")
                    .addConstraintViolation()
            isValid = false
        }

        return isValid
    }

}