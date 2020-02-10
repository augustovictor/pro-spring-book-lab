package com.github.augustovictor.prospringbooklab.movie

import com.github.augustovictor.prospringbooklab.validator.CheckMovie
import javax.validation.constraints.NotNull

@CheckMovie
data class Movie(
        @NotNull
        val title: String
)
