package com.dgf.casumotest.util

import com.dgf.casumotest.App.Companion.logger
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature

object Json {

    private val log = logger()
    private val objectMapper = ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT)

    fun toJson(obj: Any): String = try {
        objectMapper.writeValueAsString(obj)
    } catch (e: JsonProcessingException) {
        log.error("Can't deserialize object {}", obj)
        "Can't deserialize object $obj"
    }

}
