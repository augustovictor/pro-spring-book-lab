package com.github.augustovictor.prospringbooklab.contractNumber

import com.github.augustovictor.prospringbooklab.converter.AppConfig
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
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

        val request = MockMvcRequestBuilders
                .get("/contract-number/$validContractNumber/validate")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)

        mockMvc.perform(request)
                .andExpect(status().isOk)
    }

    @Test
    fun `should return 400 when contractNumber is NOT valid`() {

    }
}