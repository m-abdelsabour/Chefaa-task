package com.mohamed.tasks.comics.data.source.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


/**
 * converts List to and from String
 */

class TypeConverter {

    //list of cutome object in your database
    @TypeConverter
    fun saveImageList(listOfString: List<LocalImage?>?): String? {
        if (listOfString?.isEmpty() == true) return ""
        return Gson().toJson(listOfString)
    }

    @TypeConverter
    fun getImageList(listOfString: String?): List<LocalImage?>? {
        return Gson().fromJson(
            listOfString,
            object : TypeToken<List<LocalImage?>?>() {}.type
        )
    }

    //list of cutome object in your database
    @TypeConverter
    fun saveTextList(listOfString: List<LocalTextObject?>?): String? {
        if (listOfString?.isEmpty() == true) return ""
        return Gson().toJson(listOfString)
    }

    @TypeConverter
    fun getTextList(listOfString: String?): List<LocalTextObject?>? {
        return Gson().fromJson(
            listOfString,
            object : TypeToken<List<LocalTextObject?>?>() {}.type
        )
    }

    private fun <`object`> jsonToObject(type: Type, objectString: String): `object`? {
        val gson = Gson()
        return gson.fromJson<`object`>(objectString, type)
    }

    private fun objectToJson(`object`: Any): String {
        val gson = Gson()
        return gson.toJson(`object`)
    }

}