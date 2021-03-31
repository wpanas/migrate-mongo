package com.github.wpanas.migratemongo

import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.data.mongodb.core.query.where
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
@ContextConfiguration(initializers = [MongoInitializer::class])
class MigrateMongoApplicationTests {

    @Autowired
    lateinit var mongoTemplate: ReactiveMongoTemplate

    @Test
    fun `should save customer with single bank account`() {
        // given
        val customer = Customer.of("US77602308983748679732663455")

        // when
        runBlocking {
            mongoTemplate.save(customer, Customer.COLLECTION).awaitFirst()
        }

        // then
        val fetchedCustomer = runBlocking {
            mongoTemplate.find(
                Query.query(
                    where(Customer::id).isEqualTo(customer.id)
                ),
                Customer::class.java,
                Customer.COLLECTION
            ).awaitFirst()
        }

        assertEquals(customer, fetchedCustomer)
    }
}
