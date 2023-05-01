////package ba.etf.rma21.projekat.data.models
////
////data class Grupa(val naziv: String, val nazivPredmeta: String) {
////}
//
package ba.etf.rma21.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Grupa(
    @PrimaryKey  @ColumnInfo(name = "id") @SerializedName("id") val id:Int,
    @ColumnInfo(name = "naziv") val naziv:String,
    @ColumnInfo(name = "PredmetId") @SerializedName("PredmetId")  var predmetId: Int) {
}
