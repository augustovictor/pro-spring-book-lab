package com.github.augustovictor.prospringbooklab.converter

import com.github.augustovictor.prospringbooklab.contractNumber.ContractNumber
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class StringToContractNumberConverterTest {

    private lateinit var subject: StringToContractNumberConverter


    @Before
    fun setup() {
        subject = StringToContractNumberConverter()
    }

    @Test
    fun `should convert A0000012 to contractNumber with productPrefix A and productId 12`() {
        val stringContractNumber = "A0000012"
        val expected = ContractNumber('A', 12)
        val actual = subject.convert(stringContractNumber)

        assertEquals(actual, expected)
    }
}