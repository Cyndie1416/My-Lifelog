package com.lifelog.app.data.local.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import java.time.Instant
import java.time.LocalDate

@ProvidedTypeConverter
class RoomTypeConverters(
    private val json: Json = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
    }
) {
    private val stringListSerializer = ListSerializer(String.serializer())
    private val longListSerializer = ListSerializer(Long.serializer())

    @TypeConverter
    fun fromInstant(value: Instant?): Long? = value?.toEpochMilli()

    @TypeConverter
    fun toInstant(value: Long?): Instant? = value?.let(Instant::ofEpochMilli)

    @TypeConverter
    fun fromLocalDate(value: LocalDate?): String? = value?.toString()

    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? = value?.let(LocalDate::parse)

    @TypeConverter
    fun fromStringList(value: List<String>?): String? =
        value?.let { json.encodeToString(stringListSerializer, it) }

    @TypeConverter
    fun toStringList(value: String?): List<String> =
        value?.let { json.decodeFromString(stringListSerializer, it) }.orEmpty()

    @TypeConverter
    fun fromLongList(value: List<Long>?): String? =
        value?.let { json.encodeToString(longListSerializer, it) }

    @TypeConverter
    fun toLongList(value: String?): List<Long> =
        value?.let { json.decodeFromString(longListSerializer, it) }.orEmpty()
}

