package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.Predmet
@Dao
interface KvizDAO {
    @Query("SELECT * FROM Kviz")
    suspend fun getAll(): List<Kviz>
    @Insert
    suspend fun insertAll(vararg kvizovi: Kviz)
    @Query("DELETE FROM Kviz")
    suspend fun obrisiSve()
    @Query("UPDATE Kviz SET predan = :bool WHERE id = :id")
    suspend fun updateStanje(id: Int, bool: String)
}