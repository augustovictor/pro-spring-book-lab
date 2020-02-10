package com.github.augustovictor.prospringbooklab.formatter

import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit4.SpringRunner
import java.time.LocalDate
import java.util.*

@RunWith(SpringRunner::class)
@Import(ApplicationConversionServiceFactoryBean::class)
class ApplicationConversionServiceFactoryBeanTest {

    @Autowired
    private lateinit var subject: ApplicationConversionServiceFactoryBean

    @Test
    fun `should parse string 2020-01-25 to localDate`() {
        val stringLocalDate = "25-01-2020"

        val actual = subject.getDateTimeFormatter().parse(stringLocalDate, Locale.ENGLISH)

        assertEquals(LocalDate.parse("2020-01-25"), actual)
    }
}