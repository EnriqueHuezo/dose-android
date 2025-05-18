package com.waseefakhtar.doseapp.util

import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

object JsonUtils {
    inline fun <reified T> decodeJson(jsonString: String?, default: T): T {
        return jsonString?.let { Json.decodeFromString(it) } ?: default
    }

    inline fun <reified T> encodeJson(value: T): String {
        val json = Json
        val serializer: SerializationStrategy<T> = json.serializersModule.serializer()
        return json.encodeToString(serializer, value)
    }
}