package ba.etf.rma21.projekat.data.models


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity
data class KvizTaken(
    @PrimaryKey  @ColumnInfo(name = "id") val id:Int,
    @ColumnInfo(name = "student") val student:String,
    @ColumnInfo(name = "osvojeniBodovi") val osvojeniBodovi:Double,
    @ColumnInfo(name = "datumRada") val datumRada:Date,
    @SerializedName("KvizId") val KvizId:Int?)

