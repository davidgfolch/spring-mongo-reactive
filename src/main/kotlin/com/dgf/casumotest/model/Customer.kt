package com.dgf.casumotest.model

import org.springframework.data.annotation.Id

data class Customer(@Id val id: String,
                    val firstName: String,
                    val lastName: String,
                    val films: MutableList<CustomerFilm>,
                    val points: Int = 0) {
    constructor(id: String, firstName: String, lastName: String) : this(id, firstName, lastName, mutableListOf(), 0)
}
