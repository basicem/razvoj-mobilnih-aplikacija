////package ba.etf.rma21.projekat.data.repositories
////
////import ba.etf.rma21.projekat.data.models.Pitanje
////import ba.etf.rma21.projekat.data.models.PitanjeKviz
////
////class PitanjeKvizRepository {
////    companion object{
////        fun getPitanja(nazivKviza:String,nazivPredmeta:String):List<Pitanje>{
////            //todo Implementirati metodu da ispravno vraća rezultat
////            var dajTrojke = sveTrojke().filter { pitanjeKviz -> pitanjeKviz.kviz == nazivKviza && pitanjeKviz.predmet == nazivPredmeta }
////
////            for(nesta in dajTrojke) {
////                println("Sada je" + nesta.naziv)
////            }
////
////            var naziviPitanja = dajTrojke.map { pitanjeKviz -> pitanjeKviz.naziv }
////            var dajSvaPitanja = svaPitanja()
////            var vratiListu:MutableList<Pitanje> = mutableListOf()
////            for(pitanje in dajSvaPitanja) {
////                for(string in naziviPitanja) {
////                    if(pitanje.naziv == string)
////                        vratiListu.add(pitanje)
////                }
////            }
////            return vratiListu
////        }
////    }
////}
//
//package ba.etf.rma21.projekat.data.repositories
//
//import ba.etf.rma21.projekat.data.models.Pitanje
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.withContext
//
//
//class PitanjeKvizRepository {
//    companion object {
//
//        suspend fun getPitanja(idKviza: Int?): List<Pitanje>? {
//            return withContext(Dispatchers.IO) {
//                var response = ApiConfig.retrofit.getPitanja(idKviza!!)
//                val responseBody = response.body()
//
//                return@withContext responseBody
//            }
//            ///return emptyList()
//        }
//    }
//
//}

package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import ba.etf.rma21.projekat.data.models.Pitanje
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class PitanjeKvizRepository {
    companion object {
        private lateinit var context:Context
        fun setContext(_context: Context){
            context=_context
        }
        suspend fun getPitanja(idKviza: Int?): List<Pitanje>? {
            return withContext(Dispatchers.IO) {
                var response = ApiConfig.retrofit.getPitanja(idKviza!!)
                val responseBody = response.body()
                return@withContext responseBody
            }
        }
    }
}

