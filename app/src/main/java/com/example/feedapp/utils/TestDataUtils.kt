package com.example.feedapp.utils

import android.annotation.SuppressLint
import com.example.feedapp.FeedApplication
import com.google.gson.stream.JsonReader
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets


object TestDataUtils {

    @SuppressLint("NewApi")
    fun <T> readJsonData(key: String, model: Class<T>): T? {
        var inputStream: InputStream? = null
        var jsonReader: JsonReader? = null
        var result: T? = null

        inputStream = FeedApplication.instance?.assets?.open("testData.json")
        jsonReader = JsonReader(InputStreamReader(inputStream, StandardCharsets.UTF_8))
        var tempName: String
        jsonReader.use {
            jsonReader.beginObject()
            while (jsonReader.hasNext()) {
                tempName = jsonReader.nextName()
                if (key == tempName) {
                    result = GsonParser.parseJson(jsonReader, model)
                    break
                } else {
                    jsonReader.skipValue()
                }
            }
        }
        return result
    }
}
