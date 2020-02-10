package com.github.augustovictor.prospringbooklab.contractNumber

import java.time.LocalDate

data class ContractNumber(
        val productPrefix: Char,
        val productId: Int
) {
    lateinit var issuedAt: LocalDate

    override fun toString(): String {
        return "productPrefix: $productPrefix, productId: $productId, issuedAt: $issuedAt"
    }
}