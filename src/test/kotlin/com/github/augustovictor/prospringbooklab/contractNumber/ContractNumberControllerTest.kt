package com.github.augustovictor.prospringbooklab.contractNumber

import com.github.augustovictor.prospringbooklab.converter.AppConfig
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.containsInAnyOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@WebMvcTest(ContractNumberController::class)
@Import(AppConfig::class)
class ContractNumberControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `should return 200 when contractNumber is valid`() {
        val validContractNumber = "C0000012"

        val request = get("/contract-number/$validContractNumber/validate")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)

        mockMvc.perform(request)
                .andExpect(status().isOk)
    }

    @Test
    fun `should return 400 when contractNumber owner is empty`() {
        val validContractNumber = "Z00000000"

        val request = post("/contract-number/$validContractNumber")
                .queryParam("issued_at", "25-01-2020")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)

        mockMvc.perform(request)
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.*", containsInAnyOrder("nonpositive.productId")))
    }

    @Test
    fun `should return a valid contractNumber with issuedAt as 25-01-2020 when provided an issuedAt queryParam as 2020-01-25`() {
        val validContractNumber = "Z000000591"

        val request = post("/contract-number/$validContractNumber")
                .queryParam("issued_at", "25-01-2020")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)

        mockMvc.perform(request)
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.issuedAt", `is`("2020-01-25")))
    }
}