package com.github.wpanas.migratemongo

import com.github.wpanas.migratemongo.NewCustomer.BankAccount
import org.bson.Document
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter

@ReadingConverter
class CustomerConverter : Converter<Document, NewCustomer> {
    override fun convert(source: Document): NewCustomer = when (source["schemaVersion"]) {
        NewCustomer.SCHEMA_VERSION -> NewCustomer(source.getString(ID), convertMultipleBankAccounts(source))
        else -> NewCustomer(source.getString(ID), convertSingleBankAccount(source))
    }

    private fun convertSingleBankAccount(source: Document) =
        setOf(BankAccount(Customer.DEFAULT_CURRENCY, source.getString(Customer::bankAccount.name)))

    private fun convertMultipleBankAccounts(source: Document): Set<BankAccount> =
        (source[NewCustomer::bankAccounts.name] as List<*>)
            .map {
                it as Document
                BankAccount(it.getString(BankAccount::currency.name), it.getString(BankAccount::number.name))
            }.toSet()

    companion object {
        const val ID = "_id"
    }
}
