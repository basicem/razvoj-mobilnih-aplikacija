////package ba.etf.rma21.projekat.data.models
////
////data class Pitanje(val naziv:String, val tekst:String, val opcije:List<String>,val tacan:Int) {
////}
//
package ba.etf.rma21.projekat.data.models

import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity
data class Pitanje(
        @PrimaryKey @SerializedName("id")val id:Int,
        @ColumnInfo(name = "naziv") @SerializedName("naziv")val naziv:String,
        @ColumnInfo(name = "tekstPitanja") @SerializedName("tekstPitanja") val tekstPitanja:String,
    //@ColumnInfo(name = "opcije") @SerializedName("naziv")val opcije:String,
        @TypeConverters(ListaUString::class) @ColumnInfo(name = "opcije") @SerializedName("opcije") val opcije:List<String>,
        @ColumnInfo(name = "tacan") @SerializedName("tacan") val tacan:Int,
        @TypeConverters(ListaUString::class) @ColumnInfo(name = "KvizId") @SerializedName("KvizId") var KvizId: MutableList<String>)
