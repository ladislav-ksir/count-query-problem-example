package com.example.demo.repository

import com.example.demo.dto.PersonSearchParameters
import com.example.demo.entity.Person
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.math.BigInteger

interface PersonRepository : JpaRepository<Person, BigInteger>, PersonRepositoryCustom

interface PersonRepositoryCustom {

    fun searchPersons(searchParameters: PersonSearchParameters, pageable: Pageable): Page<Person>

    fun searchPersons(searchParameters: PersonSearchParameters): List<Person>
}

