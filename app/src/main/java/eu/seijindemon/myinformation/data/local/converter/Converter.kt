package eu.seijindemon.myinformation.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import eu.seijindemon.myinformation.data.model.KeyValue

class Converter {

    var gson = Gson()

    @TypeConverter
    fun KeyValueItemToString(keysValues: List<KeyValue>): String {
        return gson.toJson(keysValues)
    }

    @TypeConverter
    fun stringToKeyValueItem(data: String): List<KeyValue> {
        val listType = object : TypeToken<List<KeyValue>>() {
        }.type
        return gson.fromJson(data, listType)
    }
}