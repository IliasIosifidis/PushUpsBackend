package org.pushups.gymgoers

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.web.config.EnableSpringDataWebSupport

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
class GymGoersApplication

val log = LoggerFactory.getLogger(GymGoersApplication::class.java)

fun main(args: Array<String>) {
    runApplication<GymGoersApplication>(*args)
    log.info("Ready to go!")
}
