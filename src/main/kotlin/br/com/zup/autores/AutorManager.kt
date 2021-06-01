package br.com.zup.autores

import io.micronaut.transaction.SynchronousTransactionManager
import java.sql.Connection
import javax.inject.Singleton
import javax.persistence.EntityManager

@Singleton
class AutorManager(private val entityManager: EntityManager,
                   private val transactionManager: SynchronousTransactionManager<Connection>
) {
    fun save(autor: Autor) {
        return transactionManager.executeWrite {
            entityManager.persist(autor)
        }
    }
}