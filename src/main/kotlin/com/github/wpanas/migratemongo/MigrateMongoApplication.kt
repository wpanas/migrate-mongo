package com.github.wpanas.migratemongo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MigrateMongoApplication

fun main(args: Array<String>) {
    runApplication<MigrateMongoApplication>(*args)
}
