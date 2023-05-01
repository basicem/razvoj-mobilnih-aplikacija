package ba.etf.rma21.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Odgovor(
    @PrimaryKey  @ColumnInfo(name = "id") val id:Int,
    @ColumnInfo(name = "odgovoreno") val odgovoreno:Int,
    @ColumnInfo(name = "PitanjeId") @SerializedName("PitanjeId") val pitanjeId : Int,
    @ColumnInfo(name = "KvizId") var kvizId: Int,
    @ColumnInfo(name = "KvizTakenId") var idKvizTaken: Int
)
