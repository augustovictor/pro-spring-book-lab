package com.github.augustovictor.prospringbooklab.formatter

import org.springframework.format.Formatter
import org.springframework.format.support.FormattingConversionServiceFactoryBean
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Component("conversionService")
class ApplicationConversionServiceFactoryBean : FormattingConversionServiceFactoryBean() {
    companion object {
        private val DEFAULT_INPUT_DATE_FORMAT = "dd-MM-yyyy"
    }

    private val formatters = hashSetOf<Formatter<*>>()

    fun getDateTimeFormatter(): Formatter<LocalDate> {
        return object : Formatter<LocalDate> {
            override fun print(localDate: LocalDate, locale: Locale): String {
                return DateTimeFormatter.ofPattern(DEFAULT_INPUT_DATE_FORMAT).format(localDate)
            }

            override fun parse(dateTimeString: String, locale: Locale): LocalDate {
                val formatter = DateTimeFormatter.ofPattern(DEFAULT_INPUT_DATE_FORMAT)

                val localDate = formatter.parse(dateTimeString)

                return LocalDate.from(localDate)
            }

        }
    }
}