package com.github.wpanas.migratemongo

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.utility.DockerImageName

object MongoInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
    private val container: MongoDBContainer = MongoDBContainer(DockerImageName.parse("mongo:4.4.4-bionic"))

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        container.start()

        TestPropertyValues.of(
            mapOf("spring.data.mongodb.uri" to container.replicaSetUrl)
        ).applyTo(applicationContext)
    }
}
