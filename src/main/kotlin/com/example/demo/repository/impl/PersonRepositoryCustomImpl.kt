package com.example.demo.repository.impl

import com.example.demo.dto.PersonSearchParameters
import com.example.demo.entity.Person
import com.example.demo.repository.PersonRepositoryCustom
import com.example.demo.repository.util.QueryUtil.paginate
import jakarta.persistence.EntityManager
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class PersonRepositoryCustomImpl(
    private val entityManager: EntityManager
) : PersonRepositoryCustom {

    override fun searchPersons(searchParameters: PersonSearchParameters, pageable: Pageable): Page<Person> {
        val builder = entityManager.criteriaBuilder
        val query = builder.createQuery(Person::class.java)
        val root = query.from(Person::class.java)

        addSearchCriteria(root, builder, query, searchParameters)

        return paginate(entityManager, query, root, pageable)
    }

    override fun searchPersons(searchParameters: PersonSearchParameters): List<Person> {
        val builder = entityManager.criteriaBuilder
        val query = builder.createQuery(Person::class.java)
        val root = query.from(Person::class.java)

        addSearchCriteria(root, builder, query, searchParameters)

        return entityManager.createQuery(query).resultList
    }

    private fun addSearchCriteria(
        root: Root<Person>,
        builder: CriteriaBuilder,
        query: CriteriaQuery<Person>,
        searchParameters: PersonSearchParameters
    ) {
        val predicates = mutableListOf<Predicate>()

        searchParameters.name?.let {
            predicates.add(
                builder.equal(
                    root.get<String>(Person::name.name), it
                )
            )
        }

        searchParameters.surname?.let {
            predicates.add(
                builder.equal(
                    root.get<String>(Person::surname.name), it
                )
            )
        }

        query.where(
            *predicates.toTypedArray()
        )
    }
}