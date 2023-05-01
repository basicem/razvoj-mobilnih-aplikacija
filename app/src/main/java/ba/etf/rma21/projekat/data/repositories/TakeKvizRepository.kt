package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import ba.etf.rma21.projekat.data.models.KvizTaken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TakeKvizRepository {
    companion object {
        private lateinit var context: Context
        fun setContext(_context: Context){
            context=_context
        }
        suspend fun zapocniKviz(idKviza: Int): KvizTaken? {
            return withContext(Dispatchers.IO) {
                println("Kviz koji radimo je " + idKviza)
                var sviMojiKvizoviresult = getPocetiKvizovi()
                if(!sviMojiKvizoviresult.isNullOrEmpty()) {
                    for (take in sviMojiKvizoviresult!!) {
                        if (take.KvizId == idKviza)
                            return@withContext take
                    }
                }
                var mojHash:String = AccountRepository.acHash
                var response = ApiConfig.retrofit.dodajPokusajOdgovaranja(mojHash, idKviza!!)
                val responseBody = response.body()
                println("Trebalo me upisati")
                println("Poruka je " + response.message())
                if(response.message() == "OK")
                    return@withContext responseBody
                return@withContext null
            }
        }


        suspend fun getPocetiKvizovi(): List<KvizTaken>? {
            return withContext(Dispatchers.IO) {
                var response = ApiConfig.retrofit.getListuPokusajaZaStudenta(AccountRepository.acHash)
                val responseBody = response.body()
                println("Sada je nesta?" + responseBody)
                println("A velicina je " + responseBody!!.size)
                if(responseBody!!.size == 0) return@withContext null
                if(response.message() == "OK")
                    return@withContext responseBody
                return@withContext null
            }
        }

    }

}


//package ba.etf.rma21.projekat.data.repositories
//
//import android.content.Context
//import ba.etf.rma21.projekat.data.models.KvizTaken
//
//class TakeKvizRepository {
//    companion object {
//        private lateinit var context:Context
//        fun setContext(_context: Context){
//            context=_context
//        }
//        suspend fun zapocniKviz(idKviza: Int): KvizTaken? {
//            return null
//        }
//
//
//        suspend fun getPocetiKvizovi(): List<KvizTaken>? {
//            return emptyList()
//        }
//    }
//
//}
//

