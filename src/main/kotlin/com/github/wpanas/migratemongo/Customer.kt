package com.github.wpanas.migratemongo

import com.github.wpanas.migratemongo.Customer.Companion.COLLECTION
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document(collection = COLLECTION)
data class Customer(
    val id: String,
    val bankAccount: String
) {
    companion object {
        const val COLLECTION = "customers"

        fun of(bankAccount: String) =
            Customer(UUID.randomUUID().toString(), bankAccount)
    }
}
