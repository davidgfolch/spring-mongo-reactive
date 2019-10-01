package com.dgf.casumotest.model

import org.springframework.data.annotation.Id
import java.util.*

data class Film(@Id val id: String, val title: String, val rentalType: FilmRentalType, val availability: Int) {

    fun availabilityDec(): Optional<Film> = if (availability <= 0) Optional.empty()
    else Optional.of(this.copy(availability = availability - 1))

    fun availabilityInc(): Film = this.copy(availability = availability + 1)
}
