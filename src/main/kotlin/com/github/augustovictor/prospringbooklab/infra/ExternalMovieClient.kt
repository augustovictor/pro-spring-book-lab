package com.github.augustovictor.prospringbooklab.infra

import com.github.augustovictor.prospringbooklab.movie.Movie
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(name = "externalMovieClient", url = "http://localhost:3000/movies")
interface ExternalMovieClient {
    @GetMapping
    fun fetchAll(@RequestHeader("CorrelationId") correlationId: String): List<Movie>
}