//package ba.etf.rma21.projekat.data.models
//
//data class Predmet(val naziv: String, val godina: Int) {
//}

package ba.etf.rma21.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Predmet(
    @PrimaryKey  @ColumnInfo(name = "id") @SerializedName("id") var id: Int,
    @ColumnInfo(name = "naziv") @SerializedName("naziv")  var naziv: String,
    @ColumnInfo(name = "godina") @SerializedName("godina")  var godina: Int
)
