package com.github.augustovictor.prospringbooklab.validator

import com.github.augustovictor.prospringbooklab.contractNumber.ContractNumber
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.Validator

@Component("contractNumberValidator")
class ContractNumberValidator : Validator {
    override fun validate(obj: Any, errors: Errors) {
        val contractNumber = obj as ContractNumber

        if (contractNumber.productId < 1) {
            errors.rejectValue("productId", "nonpositive.productId")
        }
    }

    override fun supports(clazz: Class<*>): Boolean {
        return ContractNumber::class.java == clazz
    }
}