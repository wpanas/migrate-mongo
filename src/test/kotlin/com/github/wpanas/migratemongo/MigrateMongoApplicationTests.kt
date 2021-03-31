package com.github.wpanas.migratemongo

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
@ContextConfiguration(initializers = [MongoInitializer::class])
class MigrateMongoApplicationTests {

    @Test
    fun contextLoads() {
    }
}
