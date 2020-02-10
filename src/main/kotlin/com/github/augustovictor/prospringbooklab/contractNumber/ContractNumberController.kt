package com.github.augustovictor.prospringbooklab.contractNumber

import com.github.augustovictor.prospringbooklab.formatter.ApplicationConversionServiceFactoryBean
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/contract-number")
class ContractNumberController(
        private val formatterService: ApplicationConversionServiceFactoryBean
) {
    @GetMapping("/{contractNumber}/validate")
    fun validateContractNumber(@PathVariable("contractNumber") contractNumber: ContractNumber): ContractNumber {
        return contractNumber
    }

    @PostMapping("/{contractNumber}")
    fun generateContractNumber(
            @PathVariable("contractNumber") contractNumber: ContractNumber,
            @RequestParam("issued_at") issuedAt: String
    ): ContractNumber {
        return contractNumber.apply { this.issuedAt = formatterService.getDateTimeFormatter().parse(issuedAt, Locale.ENGLISH) }
    }
}