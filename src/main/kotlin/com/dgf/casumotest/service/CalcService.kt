package com.dgf.casumotest.service

import com.dgf.casumotest.model.CustomerFilm
import com.dgf.casumotest.model.Film
import com.dgf.casumotest.model.FilmRentalType.*
import com.dgf.casumotest.model.calculated.RentResultLine
import com.dgf.casumotest.model.calculated.Surcharges
import com.dgf.casumotest.model.calculated.SurchargesLine
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.TimeUnit

@Service
class CalcService {

    companion object {
        private const val PREMIUM_PRICE = 40.0
        private const val BASIC_PRICE = 30.0

        private const val PREMIUM_POINTS = 2
        private const val BASIC_POINTS = 1

        private const val PREMIUM_SURCHARGE = PREMIUM_PRICE
        private const val BASIC_SURCHARGE = BASIC_PRICE

        private const val PREMIUM_DAYS = 1
        private const val REGULAR_DAYS = 3
        private const val OLD_DAYS = 5

    }


    internal fun getPrice(films: List<Film>) = films.map { f -> RentResultLine(f, this.getCost(f)) }

    private fun getCost(film: Film) = when (film.rentalType) {
        NEW -> PREMIUM_PRICE
        REGULAR, OLD -> BASIC_PRICE
    }

    internal fun getPoints(films: List<Film>) = films.map { film ->
        when (film.rentalType) {
            NEW -> PREMIUM_POINTS
            REGULAR, OLD -> BASIC_POINTS
        }
    }.sum()

    internal fun getSurcharges(films: List<CustomerFilm>, returnDays: Int): Surcharges {
        val now = Calendar.getInstance().time
        val lines = films.map { film -> SurchargesLine(film, getSurcharge(film, now, returnDays)) }.toList()
        return Surcharges(lines, lines.map { it::surcharge.get() }.sum())
    }

    private fun getSurcharge(film: CustomerFilm, now: Date, returnDays: Int): Double =
            (if (returnDays > 0) returnDays
            else TimeUnit.DAYS.convert(film.date.time - now.time, TimeUnit.DAYS).toInt()
                    ).let {
                when (film.rentalType) {
                    NEW -> PREMIUM_SURCHARGE * if (it > PREMIUM_DAYS) it - PREMIUM_DAYS else 0
                    REGULAR -> BASIC_SURCHARGE * if (it - REGULAR_DAYS > 0) it - REGULAR_DAYS else 0
                    OLD -> BASIC_SURCHARGE * if (it - OLD_DAYS > 0) it - OLD_DAYS else 0
                }
            }

}
