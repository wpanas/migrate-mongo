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

@Document(collection = COLLECTION)
data class NewCustomer(
    val id: String,
    val bankAccounts: Set<BankAccount>
) {
    data class BankAccount(val currency: String, val number: String)

    companion object {
        fun of(vararg bankAccounts: Pair<String, String>) = bankAccounts
            .map { (currency, number) ->
                BankAccount(currency, number)
            }
            .let {
                NewCustomer(UUID.randomUUID().toString(), it.toSet())
            }
    }
}
