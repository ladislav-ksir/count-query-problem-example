package com.example.demo.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.math.BigInteger

@Entity
class Person(
    @Id
    val id: BigInteger,
    val name: String,
    val surname: String
)