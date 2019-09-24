package com.dgf.casumotest.service;

import com.dgf.casumotest.model.CustomerFilm;
import com.dgf.casumotest.model.Film;
import com.dgf.casumotest.model.calculated.Surcharges;
import com.dgf.casumotest.model.calculated.SurchargesLine;
import com.dgf.casumotest.model.calculated.RentResultLine;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CalcService {

    private static final Double PREMIUM_PRICE = 40.0;
    private static final Double BASIC_PRICE = 30.0;

    private static final int PREMIUM_POINTS = 2;
    private static final int BASIC_POINTS = 1;

    private static final Double PREMIUM_SURCHARGE = PREMIUM_PRICE;
    private static final Double BASIC_SURCHARGE = BASIC_PRICE;

    private static final int PREMIUM_DAYS = 1;
    private static final int REGULAR_DAYS = 3;
    private static final int OLD_DAYS = 5;

    private static final String FILM_RENTAL_TYPE_UNSUPPORTED_TYPE = "FilmRentalType unsupported type";

    List<RentResultLine> getPrice(List<Film> films) {
        return films.parallelStream().map(f -> new RentResultLine(f,this.getCost(f))).collect(Collectors.toList());
    }

    private Double getCost(Film film) {
        switch (film.getRentalType()) {
            case NEW: return PREMIUM_PRICE;
            case REGULAR:
            case OLD:
                return BASIC_PRICE;
            default: throw new IllegalStateException(FILM_RENTAL_TYPE_UNSUPPORTED_TYPE);
        }
    }

    int getPoints(List<Film> films) {
        return films.parallelStream().mapToInt(film-> {
                switch (film.getRentalType()) {
                    case NEW: return PREMIUM_POINTS;
                    case REGULAR:
                    case OLD:
                        return BASIC_POINTS;
                    default: throw new IllegalStateException(FILM_RENTAL_TYPE_UNSUPPORTED_TYPE);
                }
        }).sum();
    }

    Surcharges getSurcharges(List<CustomerFilm> films, int returnDays) {
        Date now = Calendar.getInstance().getTime();
        List<SurchargesLine> lines = films.parallelStream()
            .map(film -> new SurchargesLine(film, getSurcharge(film, now, returnDays)))
            .collect(Collectors.toList());
        return new Surcharges(lines,lines.parallelStream().mapToDouble(SurchargesLine::getSurcharge).sum());
    }

    private Double getSurcharge(CustomerFilm film, Date now, int returnDays) {
        long days = returnDays>0?
            returnDays:
            TimeUnit.DAYS.convert(film.getDate().getTime() - now.getTime(), TimeUnit.MILLISECONDS);
        switch (film.getRentalType()) {
            case NEW:
                return PREMIUM_SURCHARGE*(days>PREMIUM_DAYS?days-PREMIUM_DAYS:0);
            case REGULAR:
                return BASIC_SURCHARGE*(days-REGULAR_DAYS>0?days-REGULAR_DAYS:0);
            case OLD:
                return BASIC_SURCHARGE*(days-OLD_DAYS>0?days-OLD_DAYS:0);
            default: throw new IllegalStateException(FILM_RENTAL_TYPE_UNSUPPORTED_TYPE);
        }
    }
}
