package ba.etf.rma21.projekat.data.models

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListaUString {
    @TypeConverter
    fun fromString(value: String?) : List<String> {
        val list = object : TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(value, list)
    }
    @TypeConverter
    fun fromList(list: List<String?>?): String {
        val gson = Gson()
        return gson.toJson(list);
    }

}
