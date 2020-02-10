package com.github.augustovictor.prospringbooklab.contractNumber

import java.time.LocalDate

data class ContractNumber(
        val productPrefix: Char,
        val productId: Int

) {
    lateinit var issuedAt: LocalDate
}