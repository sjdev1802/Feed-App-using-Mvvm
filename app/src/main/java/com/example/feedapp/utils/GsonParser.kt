package com.example.feedapp.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.stream.JsonReader
import java.lang.Exception
import java.lang.reflect.Type

object GsonParser {

    val gson: Gson

    init {
        gson =
            getBuilder()
    }

    private fun getBuilder(): Gson {
        val builder = GsonBuilder()
        return builder.create()
    }

    fun toJsonString(payloadObject: Any?): String? {
        requireNotNull(payloadObject, { "Object can not be null" })
        try {
            return toJsonString(
                payloadObject,
                payloadObject.javaClass
            )
        } catch (e: Exception) {

        }
        return null
    }

    fun toJsonString(payloadObject: Any?, type: Type?): String? {
        requireNotNull(payloadObject, { "Object can not be null" })
        try {
            return gson.toJson(payloadObject, type)
        } catch (e: Exception) {

        }
        return null
    }

    fun <T> parseJson(jsonString: String?, model: Class<T>?): T? {
        return try {
            gson.fromJson(jsonString, model)
        } catch (e: Exception) {
            return null
        }
    }


    fun <T> parseJson(reader: JsonReader?, model: Class<T>): T? {
        return try {
            gson.fromJson(reader, model)
        } catch (e: Exception) {
            return null
        }
    }

}