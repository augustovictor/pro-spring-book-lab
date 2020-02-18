package com.github.augustovictor.prospringbooklab

import net.logstash.logback.argument.StructuredArguments.keyValue
import net.logstash.logback.argument.StructuredArguments.value
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@SpringBootApplication
@EnableFeignClients
class ProSpringBookLabApplication

fun main(args: Array<String>) {
	runApplication<ProSpringBookLabApplication>(*args)
}

abstract class LogWrapper(protected val service: Class<*>) {
	protected val logger: Logger = LoggerFactory.getLogger(service)

	abstract fun info(msg: String)
	abstract fun trace(service: String, correlationId: String, msg: String)
	abstract fun debug(service: String, correlationId: String, msg: String)
	abstract fun error(service: String, correlationId: String, msg: String, exception: Exception)
}

class CustomLogWrapper(service: Class<*>) : LogWrapper(service) {

	override fun info(msg: String) {
		logger.info(msg, keyValue("correlation_id", MDC.get("CorrelationId") ?: ""), value("service", service.toString().substringAfterLast(".")))
	}

	override fun trace(service: String, correlationId: String, msg: String) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun debug(service: String, correlationId: String, msg: String) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun error(service: String, correlationId: String, msg: String, exception: Exception) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

}

@Component
class LoggerSample {
	private val logger = LoggerFactory.getLogger(javaClass)
	private val customLogger = CustomLogWrapper(javaClass)

	@Bean
	fun runSampleLogger() {
		val messageType = "INFO"
		val messageContent = "Teste de mensagem"

		logger.info("A log of type {} has the content: {}", value("message_type", messageType), value("message_content", messageContent))

		customLogger.info("Log from custom log wrapper")
	}
}