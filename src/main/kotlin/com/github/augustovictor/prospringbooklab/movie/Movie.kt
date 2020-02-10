package com.github.augustovictor.prospringbooklab.movie

import com.github.augustovictor.prospringbooklab.validator.CheckMovie
import javax.validation.constraints.AssertTrue
import javax.validation.constraints.NotNull

@CheckMovie
data class Movie(
        @NotNull
        val title: String,

        val description: String? = null
) {
        @AssertTrue(message = "")
        fun thereIsDescription(): Boolean {
                var isValid = true

                if (description == null) {
                        isValid = false
                }

                return isValid
        }
}
