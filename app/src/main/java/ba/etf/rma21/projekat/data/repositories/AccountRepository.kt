//package ba.etf.rma21.projekat.data.repositories
//
//import ba.etf.rma21.projekat.data.models.Account
//import ba.etf.rma21.projekat.data.models.Kviz
//import ba.etf.rma21.projekat.data.models.Predmet
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.withContext
//
//
//
//class AccountRepository {
//
//    companion object {
//        //TODO Ovdje trebate dodati hash string vašeg accounta
//        var acHash: String = "64d3afff-2e90-459f-9a61-e41ed673ac50"
//
//        //za sada ovako nek stoji
//        var mojiKvizoviSaBodovimaIDatumimaRada: MutableList<Kviz> = mutableListOf()
//
//        fun postaviHash(acHash: String): Boolean {
//            this.acHash = acHash
//            return true
//        }
//
//        fun getHash(): String {
//            return acHash
//        }
//        suspend fun getKorisnik(): Account? {
//            return withContext(Dispatchers.IO) {
//                var response = ApiConfig.retrofit.getAccount(getHash())
//                val responseBody = response.body()
//                return@withContext responseBody
//            }
//        }
//        suspend fun obrisiPodatke(idStudent: Int) : String? {
//            return withContext(Dispatchers.IO) {
//                var response = ApiConfig.retrofit.obrisiPodatke(idStudent)
//                val responseBody = response.body()
//                return@withContext responseBody
//            }
//        }
//    }
//
//}
package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.models.Account
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class AccountRepository {

    companion object {
        //TODO Ovdje trebate dodati hash string vašeg accounta
        var acHash: String = "64d3afff-2e90-459f-9a61-e41ed673ac50"
        private lateinit var context:Context
        fun setContext(_context: Context){
            context=_context
            DBRepository.setContext(context)
            OdgovorRepository.setContext(context)
            PredmetIGrupaRepository.setContext(context)
            KvizRepository.setContext(context)
        }

        suspend fun postaviHash(acHash: String): Boolean {
            print("OK")
            this.acHash = acHash
            withContext(Dispatchers.IO) {
                var db = AppDatabase.getInstance(context)
                if(db.accountDao().getAll().size == 0) {
                    //val datum = Date(0,0,0);
                    //val pattern = "yyyy-MM-dd'T'HH:mm:ss"
                    var datum = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(Date(0,0,0))
                    //val formater = SimpleDateFormat(pattern, Locale.ENGLISH)
                    //var string: String = formater.format(datum);
                    db.accountDao().insertAll(Account(acHash, datum));
                    val prije = DBRepository.updateNow()
                    return@withContext true
                }
                else if(db.accountDao().getAll()[0].acHash == acHash){
                    val prije = DBRepository.updateNow()
                    return@withContext true
                }
                //obrisi sve i dodaj novi
                else {
                    db.accountDao().obrisiSve()
                    db.pitanjaDao().obrisiSve()
                    db.grupaDao().obrisiSve()
                    db.kvizDao().obrisiSve()
                    db.predmetDao().obrisiSve()
                    db.odgovorDao().obrisiSve()
                    val datum = Date(0,0,0);
                    val pattern = "yyyy-MM-dd'T'HH:mm:ss"
                    val formater = SimpleDateFormat(pattern, Locale.ENGLISH)
                    var string: String = formater.format(datum);
                    db.accountDao().insertAll(Account(acHash, string));
                    val prije = DBRepository.updateNow()

                    return@withContext true
                }

                return@withContext true
            }
            return true
        }

        fun getHash(): String {
            return acHash
        }

        suspend fun getKorisnik(): Account? {
            return withContext(Dispatchers.IO) {
                var response = ApiConfig.retrofit.getAccount(getHash())
                val responseBody = response.body()
                return@withContext responseBody
            }
        }

    }
}