package com.github.augustovictor.prospringbooklab.contractNumber

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@RunWith(SpringRunner::class)
@WebMvcTest(ContractNumberController::class)
class ContractNumberControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `should return 200 when contractNumber is valid`() {
        val validContractNumber = "C0000012"

        val request = MockMvcRequestBuilders
                .get("/contract-number/$validContractNumber/validate")

        mockMvc.perform(request)
                .andExpect(status().isOk)
    }

    @Test
    fun `should return 400 when contractNumber is NOT valid`() {

    }
}