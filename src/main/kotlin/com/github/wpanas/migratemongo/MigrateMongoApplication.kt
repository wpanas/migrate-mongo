package com.github.wpanas.migratemongo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.convert.MongoCustomConversions

@SpringBootApplication
class MigrateMongoApplication

fun main(args: Array<String>) {
    runApplication<MigrateMongoApplication>(*args)
}

@Configuration
class MyMongoConfiguration {

    @Bean
    fun configureConverters(): MongoCustomConversions =
        MongoCustomConversions(listOf(CustomerConverter()))
}
