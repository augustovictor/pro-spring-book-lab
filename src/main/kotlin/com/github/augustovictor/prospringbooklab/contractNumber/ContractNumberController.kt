package com.github.augustovictor.prospringbooklab.contractNumber

import com.github.augustovictor.prospringbooklab.formatter.ApplicationConversionServiceFactoryBean
import com.github.augustovictor.prospringbooklab.validator.ContractNumberValidator
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.validation.BeanPropertyBindingResult
import org.springframework.validation.ValidationUtils
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/contract-number")
class ContractNumberController(
        private val formatterService: ApplicationConversionServiceFactoryBean,
        private val contractNumberValidator: ContractNumberValidator
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("/{contractNumber}/validate")
    fun validateContractNumber(@PathVariable("contractNumber") contractNumber: ContractNumber): ContractNumber {
        return contractNumber
    }

    @PostMapping("/{contractNumber}")
    fun generateContractNumber(
            @PathVariable("contractNumber") contractNumber: ContractNumber,
            @RequestParam("issued_at") issuedAt: String
    ): ResponseEntity<*> {
        val result = contractNumber.apply { this.issuedAt = formatterService.getDateTimeFormatter().parse(issuedAt, Locale.ENGLISH) }
        val beanValidationResult = BeanPropertyBindingResult(result, "ContractNumber")
        ValidationUtils.invokeValidator(contractNumberValidator, result, beanValidationResult)
        logger.info("CONTRACT-NUMBER")
        logger.info(result.toString())

        if (beanValidationResult.allErrors.size > 0) {
            return ResponseEntity.badRequest().body(beanValidationResult.allErrors.map { it.code })
        }

        return ResponseEntity.ok().body(result)
    }
}
