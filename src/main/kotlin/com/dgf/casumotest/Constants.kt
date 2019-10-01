package com.dgf.casumotest

import com.dgf.casumotest.model.Customer
import com.dgf.casumotest.model.Film
import com.dgf.casumotest.model.FilmRentalType.*

object Constants {

    val FILMS = listOf(
            Film("5d89f274c22a011f98f6dc90", "Matrix 11", NEW, 5),
            Film("5d89f274c22a011f98f6dc91", "Spider Man", REGULAR, 2),
            Film("5d89f274c22a011f98f6dc92", "Spider Man 2", REGULAR, 2),
            Film("5d89f274c22a011f98f6dc93", "Out of Africa", OLD, 1)
    )
    val CUSTOMERS = listOf(
            Customer("5d89f274c22a011f98f6dc94", "Daisy", "Williams"),
            Customer("5d89f274c22a011f98f6dc95", "Steven", "Parker"),
            Customer("5d89f274c22a011f98f6dc96", "John", "Doe"),
            Customer("5d89f274c22a011f98f6dc97", "Stella", "Harris")
    )
}
