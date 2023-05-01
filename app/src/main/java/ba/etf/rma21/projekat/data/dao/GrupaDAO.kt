package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Predmet
@Dao
interface GrupaDAO {
    @Query("SELECT * FROM Grupa")
    suspend fun getAll(): List<Grupa>
    @Insert
    suspend fun insertAll(vararg grupe: Grupa)
    @Query("DELETE FROM Grupa")
    suspend fun obrisiSve()
}