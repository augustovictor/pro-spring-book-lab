package com.github.augustovictor.prospringbooklab.contractNumber

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/contract-number")
class ContractNumberController {
    @GetMapping("/{contractNumber}/validate")
    fun validateContractNumber(@PathVariable("contractNumber") contractNumber: ContractNumber): ContractNumber {
        return contractNumber
    }
}