package com.example.demo

import com.example.demo.dto.PersonSearchParameters
import com.example.demo.repository.PersonRepository
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.PageRequest

@DataJpaTest
class PersonRepositoryTest {

    @Autowired
    lateinit var personRepository: PersonRepository

    @Test
    fun `count all`() {
        personRepository.count() shouldBe 6
    }

    @Test
    fun `custom repository search`() {
        // Search without criteria should be equal to total count
        personRepository.searchPersons(PersonSearchParameters()).size shouldBe personRepository.count()

        personRepository.searchPersons(PersonSearchParameters(surname = "Anderson")).size shouldBe 2
        personRepository.searchPersons(PersonSearchParameters(name = "Thomas")).size shouldBe 1
    }

    @Test
    fun `custom repository search with pagination`() {
        val pageRequest = PageRequest.of(0, 1)

        personRepository.searchPersons(PersonSearchParameters(surname = "Anderson"), pageRequest).apply {
            size shouldBe 1
            totalElements shouldBe 2
            totalPages shouldBe 2
        }
    }
}