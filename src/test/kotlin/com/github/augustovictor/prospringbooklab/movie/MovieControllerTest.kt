package com.github.augustovictor.prospringbooklab.movie

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.augustovictor.prospringbooklab.validator.MovieValidationService
import org.hamcrest.Matchers.containsInAnyOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@WebMvcTest(MovieController::class)
@Import(MovieValidationService::class)
class MovieControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `should return bad request when movie title is less than 3 characters`() {
        val invalidMovie = Movie("ab")

        val request = post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsString(invalidMovie))

        mockMvc.perform(request)
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.*", containsInAnyOrder("Movie should have a title of at least 3 characters")))
    }
}