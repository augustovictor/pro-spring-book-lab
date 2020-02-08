package com.github.augustovictor.prospringbooklab.converter

import com.github.augustovictor.prospringbooklab.contractNumber.ContractNumber
import org.springframework.core.convert.converter.Converter

class StringToContractNumberConverter : Converter<String, ContractNumber> {
    override fun convert(value: String): ContractNumber {
        val productPrefix = value.first()
        val productNumber = value.substring(1).toInt()
        return ContractNumber(productPrefix, productNumber)
    }
}
