package com.github.augustovictor.prospringbooklab.converter

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ConversionServiceFactoryBean
import org.springframework.core.convert.converter.Converter

@Configuration
class AppConfig {
    @Bean
    fun conversionService(): ConversionServiceFactoryBean {
        val conversionServiceFactoryBean = ConversionServiceFactoryBean()

        val converters = hashSetOf<Converter<*, *>>()

        converters.add(contractNumberConverter())

        conversionServiceFactoryBean.setConverters(converters)

        conversionServiceFactoryBean.afterPropertiesSet()

        return conversionServiceFactoryBean
    }

    @Bean
    fun contractNumberConverter() = StringToContractNumberConverter()
}