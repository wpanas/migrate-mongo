package com.github.wpanas.migratemongo

import com.github.wpanas.migratemongo.NewCustomer.BankAccount
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
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

    @Test
    fun `should save customer with multiple bank accounts`() {
        // given
        val customer = NewCustomer.of(
            "USD" to "US77602308983748679732663455",
            "PLN" to "PL05842479609094250496642135"
        )

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
                NewCustomer::class.java,
                Customer.COLLECTION
            ).awaitFirst()
        }

        assertEquals(customer, fetchedCustomer)
    }

    @Test
    fun `should migrate old customer with single account to new customer with multiple bank accounts`() {
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
                NewCustomer::class.java,
                Customer.COLLECTION
            ).awaitFirst()
        }

        assertEquals(customer.id, fetchedCustomer.id)
        assertTrue(fetchedCustomer.bankAccounts.contains(BankAccount(Customer.DEFAULT_CURRENCY, customer.bankAccount)))
        assertEquals(fetchedCustomer.bankAccounts.size, 1)
    }
}
