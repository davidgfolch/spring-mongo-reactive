package com.dgf.casumotest.model.calculated

data class RentResult(val films: List<RentResultLine>, val total: Double = 0.toDouble())
