package com.github.augustovictor.prospringbooklab.validator

import javax.validation.Constraint
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS) // Annotation applied to class level
@Constraint(validatedBy = [CheckMovieValidator::class]) // Indicates it is a validator
@MustBeDocumented
annotation class CheckMovie(
        val message: String = "Movie should have a title of at least 3 characters",
        val groups: Array<KClass<out Any>> = [], // Groups in which the constraint applies to
        val payload: Array<KClass<out Any>> = [] // Addiitonal payload objects that implement javax.validation.Payload interface. Ex: Violation severity
)
