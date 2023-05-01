package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.Odgovor
@Dao
interface OdgovorDAO {
    @Query("SELECT * FROM Odgovor")
    suspend fun getAll(): List<Odgovor>
    @Insert
    suspend fun insertAll(vararg odgovori: Odgovor)
    @Query("SELECT max(id) FROM Odgovor")
    suspend fun getMaxID() : Int
    @Query("DELETE FROM Odgovor")
    suspend fun obrisiSve()
}