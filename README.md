# Migrate Mongo Example Project

You can learn from this project how to implement the Schema Versioning pattern 
to migrate documents from an old format to a new format.

For more info about the Schema Versioning pattern read an [article].

# How to navigate through this example?
- All tests are stored in [MigrateMongoApplicationTests.kt]
- Data classes representing documents are in [Customer.kt]
- The converter that migrate old documents to new format is in [CustomerConverter.kt]
- It is configured in class `MyMongoConfiguration` that is in [MigrateMongoApplication.kt]


[article]: https://wpanas.github.io/2021/04/02/migrate-mongo.html
[MigrateMongoApplicationTests.kt]: src/test/kotlin/com/github/wpanas/migratemongo/MigrateMongoApplicationTests.kt
[Customer.kt]: src/main/kotlin/com/github/wpanas/migratemongo/Customer.kt
[CustomerConverter.kt]: src/main/kotlin/com/github/wpanas/migratemongo/CustomerConverter.kt
[MigrateMongoApplication.kt]: src/main/kotlin/com/github/wpanas/migratemongo/MigrateMongoApplication.kt
