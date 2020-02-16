package com.github.augustovictor.prospringbooklab

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class ProSpringBookLabApplication

fun main(args: Array<String>) {
	runApplication<ProSpringBookLabApplication>(*args)
}
