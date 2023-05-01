package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ba.etf.rma21.projekat.data.models.Account
import ba.etf.rma21.projekat.data.models.Predmet

@Dao
interface AccountDAO {
    @Query("SELECT * FROM Account")
    suspend fun getAll(): List<Account>

    @Insert
    suspend fun insertAll(vararg korisnici: Account)

    @Delete
    suspend fun delete(user: Account)

    @Query("SELECT lastUpdate from Account")
    fun getDate() : String

    @Query("DELETE FROM Account")
    suspend fun obrisiSve()
}
