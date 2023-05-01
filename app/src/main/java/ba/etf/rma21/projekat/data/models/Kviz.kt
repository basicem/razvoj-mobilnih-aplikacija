////package ba.etf.rma21.projekat.data.models
////
////import java.util.*
////
////data class Kviz(
////        val naziv: String, val nazivPredmeta: String, val datumPocetka: Date, val datumKraj: Date,
////        var datumRada: Date, val trajanje: Int, val nazivGrupe: String, var osvojeniBodovi: Float?
////
////) {
////    override fun equals(other: Any?): Boolean {
////        if (this === other) return true
////        if (javaClass != other?.javaClass) return false
////
////        other as Kviz
////
////        if (naziv != other.naziv) return false
////        if (nazivPredmeta != other.nazivPredmeta) return false
////
////        return true
////    }
////
////    override fun hashCode(): Int {
////        var result = naziv.hashCode()
////        result = 31 * result + nazivPredmeta.hashCode()
////        return result
////    }
////}
//
package ba.etf.rma21.projekat.data.models


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity
data class Kviz(
    @PrimaryKey @ColumnInfo(name = "id")  @SerializedName("id") val id: Int,
    @ColumnInfo(name = "naziv") @SerializedName("naziv") val naziv: String,
    @ColumnInfo(name = "datumPocetka") @SerializedName("datumPocetak") val datumPocetka: String,
    @ColumnInfo(name = "datumKraj") @SerializedName("datumKraj") val datumKraj: String?,
    //var datumRada: String?,
    @ColumnInfo(name = "trajanje") @SerializedName("trajanje") val trajanje: Int,
    //
    @ColumnInfo(name = "osvojeniBodovi") @SerializedName("osvojeniBodovi") var osvojeniBodovi: Double?,
    @ColumnInfo(name = "datumRada") @SerializedName("datumRada") var datumRada: String?,
    //var nazivGrupe: String?,
    //var osvojeniBodovi: Double?,
    @ColumnInfo(name = "nazivPredmeta") @SerializedName("nazivPredmeta") var nazivPredmeta: String?,
    @ColumnInfo(name = "predan") @SerializedName("predan") var predan: Boolean?
)
//data class Kviz(
//        val id:Int,
//        val naziv:String,
//        val datumPocetak:Date,
//        val datumKraj:Date? = null,
//        val datumRada: Date? = null,
//        val trajanje:Int,
//        val osvojeniBodovi: Double? = null,
//        var nazivPredmeta: MutableSet<String>) {
//}

