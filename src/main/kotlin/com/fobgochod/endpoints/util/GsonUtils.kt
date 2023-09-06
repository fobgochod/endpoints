package com.fobgochod.endpoints.util

import com.fobgochod.mock.serializer.Constants
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

object GsonUtils {

    private val gson: Gson = GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .setDateFormat(Constants.DATETIME_PATTERN)
            .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
            .registerTypeAdapter(LocalTime::class.java, LocalTimeAdapter())
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
            .registerTypeAdapter(Timestamp::class.java, TimestampAdapter())
            .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
            .create()

    fun toJson(any: Any?): String {
        return gson.toJson(any)
    }

    fun toMap(json: String): Map<String, Any?> {
        return gson.fromJson(json, TypeToken.getParameterized(Map::class.java, String::class.java, Any::class.java).type)
    }

    private class LocalDateAdapter : JsonSerializer<LocalDate> {
        override fun serialize(value: LocalDate, type: Type, context: JsonSerializationContext): JsonElement {
            return JsonPrimitive(value.format(Constants.DATE_FORMATTER))
        }
    }

    private class LocalTimeAdapter : JsonSerializer<LocalTime> {
        override fun serialize(value: LocalTime, type: Type, context: JsonSerializationContext): JsonElement {
            return JsonPrimitive(value.format(Constants.TIME_FORMATTER))
        }
    }

    private class LocalDateTimeAdapter : JsonSerializer<LocalDateTime> {
        override fun serialize(value: LocalDateTime, type: Type, context: JsonSerializationContext): JsonElement {
            return JsonPrimitive(value.format(Constants.DATETIME_FORMATTER))
        }
    }

    private class TimestampAdapter : JsonSerializer<Timestamp> {
        override fun serialize(value: Timestamp, type: Type, context: JsonSerializationContext): JsonElement {
            return JsonPrimitive(value.toLocalDateTime().format(Constants.DATETIME_FORMATTER))
        }
    }
}