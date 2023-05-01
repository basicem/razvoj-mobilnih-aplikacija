package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ba.etf.rma21.projekat.data.models.Odgovor
import ba.etf.rma21.projekat.data.models.Pitanje
@Dao
interface PitanjeDAO {
    @Query("SELECT * FROM Pitanje")
    suspend fun getAll(): List<Pitanje>
    @Insert
    suspend fun insertAll(vararg pitanja: Pitanje)
    @Query("DELETE FROM Pitanje")
    suspend fun obrisiSve()

}