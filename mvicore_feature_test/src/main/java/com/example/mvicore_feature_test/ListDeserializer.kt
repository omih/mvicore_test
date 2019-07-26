package com.example.mvicore_feature_test

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ListDeserializer : JsonDeserializer<List<*>> {

    companion object {
        val TAG = "ListDeserializer"
    }

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, type: Type,
                             context: JsonDeserializationContext
    ): List<*> {

        val jsonArray = json.asJsonArray
        val result = ArrayList<Any>(jsonArray.size())
        val elementType = (type as ParameterizedType).actualTypeArguments[0]

        for (jsonElement in jsonArray) {
            var deserializedElement: Any? = null
            try {
                deserializedElement = context.deserialize<Any>(jsonElement, elementType)
            } catch (e: JsonParseException) {
                e.printStackTrace()
            }

            if (deserializedElement != null) {
                result.add(deserializedElement)
            }
        }

        return result
    }
}