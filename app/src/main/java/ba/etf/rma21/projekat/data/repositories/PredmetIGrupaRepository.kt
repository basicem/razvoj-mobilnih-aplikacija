//package ba.etf.rma21.projekat.data.repositories
//
//import ba.etf.rma21.projekat.data.models.Grupa
//import ba.etf.rma21.projekat.data.models.Predmet
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.withContext
//import org.json.JSONArray
//import org.json.JSONException
//import org.json.JSONObject
//import java.io.IOException
//import java.net.HttpURLConnection
//import java.net.MalformedURLException
//import java.net.URL
//import kotlin.properties.Delegates.observable
//
//
//class PredmetIGrupaRepository {
//    companion object{
//
//        suspend fun getPredmeti():List<Predmet>? {
//            return withContext(Dispatchers.IO) {
//                var response = ApiConfig.retrofit.getPredmeti()
//                val responseBody = response.body()
//
//                return@withContext responseBody
//            }
//            //return emptyList()
//        }
//        suspend fun getGrupe():List<Grupa>?{
//            return withContext(Dispatchers.IO) {
//                var response = ApiConfig.retrofit.getGrupe()
//                val responseBody = response.body()
//
//                return@withContext responseBody
//            }
//            //return emptyList()
//        }
//
//        suspend fun getGrupeZaPredmet(idPredmeta:Int):List<Grupa>?{
//            return withContext(Dispatchers.IO) {
//                var response = ApiConfig.retrofit.getGrupeZaPredmet(idPredmeta)
//                val responseBody = response.body()
//
//                return@withContext responseBody
//            }
//            //return emptyList()
//        }
//
//        suspend fun upisiUGrupu(idGrupa:Int):Boolean?{
//DBRepository.updateNow()
//            return withContext(Dispatchers.IO) {
//                var idKorisnika: String = AccountRepository.acHash
//                var response = ApiConfig.retrofit.upisiNaGrupu(idGrupa, idKorisnika)
//                val responseBody = response.message()
//                if(responseBody == "OK") return@withContext true
//                return@withContext  false
//
//            }
//        }
//
//        suspend fun getUpisaneGrupe():List<Grupa>?{
//            return withContext(Dispatchers.IO) {
//                var idKorisnika: String = AccountRepository.acHash
//                var response = ApiConfig.retrofit.getUpisaneGrupe(idKorisnika)
//                val responseBody = response.body()
//
//                return@withContext responseBody
//            }
//            //return emptyList()
//        }
//        suspend fun getPredmetZaID(id: Int):Predmet? {
//            return withContext(Dispatchers.IO) {
//                var response = ApiConfig.retrofit.getPredmetZaId(id)
//                val responseBody = response.body()
//
//                return@withContext responseBody
//            }
//            //return emptyList()
//        }
//
//
//    }
//}

package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Predmet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class PredmetIGrupaRepository {
    companion object{
        private lateinit var context:Context
        fun setContext(_context: Context){
            context=_context
        }
        suspend fun getPredmeti():List<Predmet>? {
            return withContext(Dispatchers.IO) {
                var response = ApiConfig.retrofit.getPredmeti()
                val responseBody = response.body()

                return@withContext responseBody
            }
        }
        suspend fun getGrupe():List<Grupa>?{
            return withContext(Dispatchers.IO) {
                var response = ApiConfig.retrofit.getGrupe()
                val responseBody = response.body()

                return@withContext responseBody
            }
        }

        suspend fun getGrupeZaPredmet(idPredmeta:Int):List<Grupa>?{
            return withContext(Dispatchers.IO) {
                var response = ApiConfig.retrofit.getGrupeZaPredmet(idPredmeta)
                val responseBody = response.body()

                return@withContext responseBody
            }
        }

        suspend fun getUpisani(): List<Predmet> {
            var upisaniPredmeti: MutableSet<Predmet> = mutableSetOf()
            val upisaneGrupe = PredmetIGrupaRepository.getUpisaneGrupe()
            val predmeti = PredmetIGrupaRepository.getPredmeti()

            if (upisaneGrupe == null || predmeti == null)
                return emptyList()
            for (grupa in upisaneGrupe) {
                for (predmet in predmeti) {
                    if (grupa.predmetId == predmet.id)
                        upisaniPredmeti.add(predmet)
                }
            }
            return upisaniPredmeti.toList()
        }

        suspend fun upisiUGrupu(idGrupa:Int):Boolean?{
        DBRepository.updateNow()
            return withContext(Dispatchers.IO) {
                var idKorisnika: String = AccountRepository.getHash()
                var response = ApiConfig.retrofit.upisiNaGrupu(idGrupa, idKorisnika)
                val responseBody = response.message()
                if(responseBody == "OK") return@withContext true
                return@withContext  false

            }
        }

        suspend fun getUpisaneGrupe():List<Grupa>?{
            return withContext(Dispatchers.IO) {
                var idKorisnika: String = AccountRepository.acHash
                var response = ApiConfig.retrofit.getUpisaneGrupe(idKorisnika)
                val responseBody = response.body()

                return@withContext responseBody
            }
        }
        suspend fun getPredmetZaID(id: Int):Predmet? {
            return withContext(Dispatchers.IO) {
                var response = ApiConfig.retrofit.getPredmetZaId(id)
                val responseBody = response.body()

                return@withContext responseBody
            }
            //return emptyList()
        }


    }
}