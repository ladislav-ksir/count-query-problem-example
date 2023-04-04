package com.example.demo.repository.util

import jakarta.persistence.EntityManager
import jakarta.persistence.NoResultException
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Root
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

object QueryUtil {

    fun <T> paginate(entityManager: EntityManager, query: CriteriaQuery<T>, root: Root<*>? = null, pagination: Pageable): Page<T> {
        val count = count(entityManager, query, root)

        val (start, pageSize, pageNumber) = getPaginationVariables(pagination, count)

        val typedQuery = entityManager.createQuery(query).apply {
            firstResult = start
            maxResults = pageSize
        }

        val results = typedQuery.resultList

        return PageImpl(results, PageRequest.of(pageNumber, pageSize), count)
    }

    private fun <T> count(em: EntityManager, criteria: CriteriaQuery<T>, root: Root<*>? = null): Long {
        return try {
            // TODO
            return 6
        } catch (e: NoResultException) {
            0
        }
    }

    private fun getPaginationVariables(pagination: Pageable, totalCount: Long): Triple<Int, Int, Int> {
        return if (pagination.isUnpaged) {
            Triple(0, if (totalCount > 0) totalCount.toInt() else 1, 0)
        } else {
            Triple(pagination.pageNumber * pagination.pageSize, pagination.pageSize, pagination.pageNumber)
        }
    }
}