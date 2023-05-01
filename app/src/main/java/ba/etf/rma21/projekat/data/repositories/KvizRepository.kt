////package ba.etf.rma21.projekat.data.repositories
////
////import ba.etf.rma21.projekat.data.models.Grupa
////import ba.etf.rma21.projekat.data.models.Kviz
////import ba.etf.rma21.projekat.data.models.Pitanje
////import ba.etf.rma21.projekat.data.repositories.KorisnikRepository.Companion.dajMojeGrupe
////import java.text.SimpleDateFormat
////import java.util.*
////
////class KvizRepository {
////
////    companion object {
////        // TODO: Implementirati
////        init {
////            // TODO: Implementirati
////        }
////        fun upisiMeNaKviz(grupa:Grupa) {
////            val listaKvizova: List<Kviz> = sviKvizovi().sortedBy { kviz -> kviz.datumPocetka }
////            for(kviz in listaKvizova) {
////                if(grupa.naziv == kviz.nazivGrupe && grupa.nazivPredmeta == kviz.nazivPredmeta)
////                    KorisnikRepository.dodajKviz(kviz, mutableListOf<Triple<String, String, Int>>())
////            }
////        }
////
////        fun getMyKvizes(): List<Kviz> {
////            // TODO: Implementirati
//////            val listaMojihGrupa: List<Grupa> = dajMojeGrupe();
//////            val listaKvizova: List<Kviz> = sviKvizovi().sortedBy { kviz -> kviz.datumPocetka }
//////            var vratiListu: MutableList<Kviz> = mutableListOf()
//////            for(grupa in listaMojihGrupa) {
//////                for(kviz in listaKvizova) {
//////                    if(grupa.naziv == kviz.nazivGrupe && grupa.nazivPredmeta == kviz.nazivPredmeta) {
//////                        println("Treba biti ovaj kviz" + kviz)
//////                        vratiListu.add(kviz)
//////                    }
//////                }
//////                }
////            var mojiKvizovi = KorisnikRepository.dajMojeKvizove()
////            for(kviz in mojiKvizovi) {
////                println("Na ovom kvizu imamo " + kviz.key.osvojeniBodovi)
////            }
////            return KorisnikRepository.dajMojeKvizove().map { kviz -> kviz.key }.sortedBy { kviz  -> kviz.datumPocetka }
////            //return vratiListu
////
////        }
////        fun jeLiTu(traziKviz: Kviz) : Boolean {
////            var lista = getMyKvizes()
////            for(kviz in lista) {
////                if(kviz.naziv == traziKviz.naziv && kviz.nazivPredmeta == traziKviz.nazivPredmeta)
////                    return true;
////            }
////            return  false
////        }
////
////        //vraca sve kvizove
////        fun getAll(): List<Kviz> {
////            var vratiKivoze:MutableList<Kviz>
////            var sviKvizovi = sviKvizovi().sortedBy { kviz  -> kviz.datumPocetka }
////            vratiKivoze = sviKvizovi as MutableList<Kviz>
////            var sviMoji = getMyKvizes()
////            for(jedanOdSvih in vratiKivoze) {
////                for(moj in sviMoji) {
////                    if(moj.equals(jedanOdSvih))
////                        vratiKivoze[vratiKivoze.indexOf(jedanOdSvih)] = moj
////                }
////            }
////            return vratiKivoze
////        }
////
////        fun getDone(): List<Kviz> {
////            // TODO: Implementirati
////            val pattern = "dd.MM.yyyy"
////            val simpleDateFormat = SimpleDateFormat(pattern)
////            return getMutable().filter { kviz -> simpleDateFormat.format(kviz.datumRada) != "01.01.1970"};
////        }
////
////        fun getFuture(): List<Kviz> {
////            // TODO: Implementirati
//    //            return getMutable().filter { kviz -> kviz.datumPocetka.after(Calendar.getInstance().time)};
////        }
////
////        fun getNotTaken(): List<Kviz> {
////            // TODO: Implementirati
////            val pattern = "dd.MM.yyyy"
////            val simpleDateFormat = SimpleDateFormat(pattern)
////            return getMutable().filter { kviz -> kviz.datumKraj.before(Calendar.getInstance().time) && simpleDateFormat.format(kviz.datumRada) == "01.01.1970" };
////        }
////
////        // TODO: Implementirati i ostale potrebne metode
////        fun getMutable(): MutableList<Kviz> {
//////            val listaMojihGrupa: List<Grupa> = dajMojeGrupe();
//////            val listaKvizova: List<Kviz> = sviKvizovi().sortedBy { kviz -> kviz.datumPocetka }
//////            var vratiListu: MutableList<Kviz> = mutableListOf<Kviz>()
//////            for(grupa in listaMojihGrupa) {
//////                for(kviz in listaKvizova) {
//////                    if(grupa.naziv == kviz.nazivGrupe && grupa.nazivPredmeta == kviz.nazivPredmeta) {
//////                        vratiListu.add(kviz)
//////                    }
//////                }
//////            }
//////            return vratiListu
////            var mojiKvizovi = KorisnikRepository.dajMojeKvizove()
////            for(kviz in mojiKvizovi) {
////                println("Na ovom kvizu imamo " + kviz.key.osvojeniBodovi)
////            }
////            return KorisnikRepository.dajMojeKvizove().map { kviz -> kviz.key }.toMutableList()
////        }
////    }
////}
//
//package ba.etf.rma21.projekat.data.repositories
//
//import ba.etf.rma21.projekat.data.models.Kviz
//import ba.etf.rma21.projekat.data.models.KvizTaken
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.withContext
//import retrofit2.Response
//import java.time.LocalDate
//import java.util.*
//
//
//class KvizRepository {
//
//    companion object {
//
//        init {
//
//        }
//
//        suspend fun getMyKvizes(): List<Kviz> {
//            return getAll()!!
//        }
//
//        fun getAl1l(): List<Kviz> {
//            return emptyList()
//        }
//
//        suspend fun getDone(): List<Kviz> {
//            return withContext(Dispatchers.IO) {
//                var idKorisnika: String = AccountRepository.acHash
//                var responseGrupe = ApiConfig.retrofit.getUpisaneGrupe(idKorisnika)
//                var responseGrupeBody = responseGrupe.body()
//                var listaKvizova = mutableListOf<Kviz>()
//                for(grupa in responseGrupeBody!!) {
//                    var response =  ApiConfig.retrofit.getKvizoveZaGrupu(grupa.id)
//                    listaKvizova = (listaKvizova + response.body()!!).toMutableList()
//                }
//                //imamo listu nasih kvizova, trebamo sada filtrirati one koji su gotovi
//                //oni koji imaju sva odgovorena pitanja
//                //treba mi postaviti i bodove nazalost
//                for(kviz in listaKvizova) {
//                    var mojiPokusaji = ApiConfig.retrofit.getListuPokusajaZaStudenta(AccountRepository.acHash).body()
//                    //var mojiPokusajiBody = mojiPokusaji.body()
//                    println("Moji pokusaji su " + mojiPokusaji)
//                    println("Kviz id je " + kviz.id)
//                    var mojPokusaj: Int = -1
//                    for(pokusaj in mojiPokusaji!!) {
//                        if(pokusaj.KvizId == kviz.id) {
//                            mojPokusaj = pokusaj.id
//                            break
//                        }
//                    }
//                    //znaci da nema pokusaja
//                    if(mojPokusaj == -1) continue
//
//                    var mojiOdgovori = ApiConfig.retrofit.getOdgovoreZaPokusaj(AccountRepository.acHash, mojPokusaj).body()
//                    //imamo pokusaje za nas kviz
//                    //sada trebamo naci bodove
//                    var pitanjaZaKviz = ApiConfig.retrofit.getPitanja(kviz.id).body()
//                    //ako nije sve odgovoreno
//                    if(mojiOdgovori?.size != pitanjaZaKviz?.size) continue
//
//                    var trenutniBodovi: Double = 0.0
//                    //dosadasnji bodovi
//                    var ukupniBodoviKviza: Double = 0.0
//
//                    for(odgovor in mojiOdgovori!!) {
//                        trenutniBodovi  = 0.0
//                        for(pitanje in pitanjaZaKviz!!) {
//                            if(odgovor.pitanjeId == pitanje.id) {
//                                if(pitanje.tacan == odgovor.odgovoreno) {
//                                    trenutniBodovi = 1.0
//                                }
//                                ukupniBodoviKviza += (trenutniBodovi.toDouble() / pitanjaZaKviz.size) * 100
//                                break
//                            }
//                        }
//                    }
//                    kviz.osvojeniBodovi = ukupniBodoviKviza
//                    kviz.datumRada = Calendar.getInstance().time
//
//                }
//                var vratiListu : MutableList<Kviz> = mutableListOf()
//                for(kviz in listaKvizova) {
//                    if(kviz.osvojeniBodovi != null)
//                        vratiListu.add(kviz)
//                }
//                return@withContext vratiListu.sortedBy { kviz  -> kviz.datumPocetka }
//            }
//        }
//
//        suspend fun getFuture(): List<Kviz> {
//            return withContext(Dispatchers.IO) {
//                var idKorisnika: String = AccountRepository.acHash
//                var responseGrupe = ApiConfig.retrofit.getUpisaneGrupe(idKorisnika)
//                var responseGrupeBody = responseGrupe.body()
//                var listaKvizova = mutableListOf<Kviz>()
//                for(grupa in responseGrupeBody!!) {
//                    var response =  ApiConfig.retrofit.getKvizoveZaGrupu(grupa.id)
//                    listaKvizova = (listaKvizova + response.body()!!).toMutableList()
//                }
//                var buduci: MutableList<Kviz> = mutableListOf()
//                for(kviz in listaKvizova) {
//                    if(kviz.datumPocetka.after(Calendar.getInstance().time))
//                        buduci.add(kviz)
//                }
//                return@withContext buduci.sortedBy { kviz  -> kviz.datumPocetka }
//            }
//        }
//
//        suspend fun getNotTaken(): List<Kviz> {
//            return withContext(Dispatchers.IO) {
//                var idKorisnika: String = AccountRepository.acHash
//                var responseGrupe = ApiConfig.retrofit.getUpisaneGrupe(idKorisnika)
//                var responseGrupeBody = responseGrupe.body()
//                var listaKvizova = mutableListOf<Kviz>()
//                for(grupa in responseGrupeBody!!) {
//                    var response =  ApiConfig.retrofit.getKvizoveZaGrupu(grupa.id)
//                    listaKvizova = (listaKvizova + response.body()!!).toMutableList()
//                }
//                var prosli: MutableList<Kviz> = mutableListOf()
//
//                for(kviz in listaKvizova) {
//                    if(kviz.datumKraj != null && kviz.datumKraj.before(Calendar.getInstance().time) && kviz.datumRada != null)
//                        prosli.add(kviz)
//                }
//                return@withContext prosli.sortedBy { kviz  -> kviz.datumPocetka }
//            }
//        }
//
//        suspend fun getAll(): List<Kviz>? {
//            return withContext(Dispatchers.IO) {
//                var response = ApiConfig.retrofit.getAll()
//                val responseBody = response.body()
//
//
//
//                //ovdje treba popraviti
//                var idKorisnika: String = AccountRepository.acHash
//                var responseGrupe = ApiConfig.retrofit.getUpisaneGrupe(idKorisnika)
//                var responseGrupeBody = responseGrupe.body()
//                var listaKvizova = mutableListOf<Kviz>()
//                for(grupa in responseGrupeBody!!) {
//                    var response =  ApiConfig.retrofit.getKvizoveZaGrupu(grupa.id)
//                    listaKvizova = (listaKvizova + response.body()!!).toMutableList()
//                }
//                for(kviz in listaKvizova) {
//                    var mojiPokusaji = ApiConfig.retrofit.getListuPokusajaZaStudenta(AccountRepository.acHash).body()
//                    //var mojiPokusajiBody = mojiPokusaji.body()
//                    println("Moji pokusaji su " + mojiPokusaji)
//                    println("Kviz id je " + kviz.id)
//                    var mojPokusaj: Int = -1
//                    for(pokusaj in mojiPokusaji!!) {
//                        if(pokusaj.KvizId == kviz.id) {
//                            mojPokusaj = pokusaj.id
//                            break
//                        }
//                    }
//                    //znaci da nema pokusaja
//                    if(mojPokusaj == -1) continue
//                    var mojiOdgovori = ApiConfig.retrofit.getOdgovoreZaPokusaj(AccountRepository.acHash, mojPokusaj).body()
//                    //imamo pokusaje za nas kviz
//                    //sada trebamo naci bodove
//                    var pitanjaZaKviz = ApiConfig.retrofit.getPitanja(kviz.id).body()
//
//                    //ako nije sve odgovoreno
//                    if(mojiOdgovori?.size != pitanjaZaKviz?.size) continue
//
//                    var trenutniBodovi: Double = 0.0
//                    //dosadasnji bodovi
//                    var ukupniBodoviKviza: Double = 0.0
//
//                    for(odgovor in mojiOdgovori!!) {
//                        trenutniBodovi  = 0.0
//                        for(pitanje in pitanjaZaKviz!!) {
//                            if(odgovor.pitanjeId == pitanje.id) {
//                                if(pitanje.tacan == odgovor.odgovoreno) {
//                                    trenutniBodovi = 1.0
//                                }
//                                ukupniBodoviKviza += (trenutniBodovi.toDouble() / pitanjaZaKviz.size) * 100
//                                break
//                            }
//                        }
//                    }
//                    kviz.osvojeniBodovi = ukupniBodoviKviza
//                    kviz.datumRada = Calendar.getInstance().time
//                }
//
//                var vratiKivoze = responseBody as MutableList<Kviz>
//
//                for(jedanOdSvih in vratiKivoze) {
//                    for(moj in listaKvizova) {
//                        if(moj.id == jedanOdSvih.id)
//                            vratiKivoze[vratiKivoze.indexOf(jedanOdSvih)] = moj
//                    }
//                }
//
//                return@withContext vratiKivoze.sortedBy { kviz  -> kviz.datumPocetka }
//            }
//        }
//
//        suspend fun getById(id: Int): Kviz? {
//            return withContext(Dispatchers.IO) {
//                var response = ApiConfig.retrofit.getById(id)
//                val responseBody = response.body()
//
//                return@withContext responseBody
//            }
//        }
//        //treba ovdje sad uradit sve
//
//        suspend fun getKvizoveZaGrupu(id:Int):List<Kviz>? {
//            return withContext(Dispatchers.IO) {
//                var response = ApiConfig.retrofit.getKvizoveZaGrupu(id)
//                val responseBody = response.body()
//
//                return@withContext responseBody?.sortedBy { kviz  -> kviz.datumPocetka }
//            }
//        }
//
//    }
//}


package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.models.Kviz
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*


class KvizRepository {

    companion object {

        init {

        }

        private lateinit var context: Context
        fun setContext(_context: Context) {
            context = _context
        }
        suspend fun getProsli() : List<Kviz> {
            DBRepository.updateNow();
            var db = AppDatabase.getInstance(context)
            var kvizovi = db.kvizDao().getAll()
            var prosli: MutableList<Kviz> = mutableListOf()
            for(kviz in kvizovi) {
                if(kviz.datumKraj != null && kviz.datumKraj < Calendar.getInstance().time.toString() && kviz.datumRada != null)
                    prosli.add(kviz)
            }
            return prosli.sortedBy { kviz  -> kviz.datumPocetka }
        }


        suspend fun getBuduci() :List<Kviz> {
            DBRepository.updateNow();
            var db = AppDatabase.getInstance(context)
            var kvizovi = db.kvizDao().getAll()

            var buduci: MutableList<Kviz> = mutableListOf()
            for(kviz in kvizovi) {
                if(kviz.datumPocetka > Calendar.getInstance().time.toString())
                    buduci.add(kviz)
            }
            return buduci.sortedBy { kviz  -> kviz.datumPocetka }
        }

        suspend fun getDovrseni() : List<Kviz> {
            DBRepository.updateNow();
            var db = AppDatabase.getInstance(context)
            var kvizovi = db.kvizDao().getAll()
            for (kviz in kvizovi) {
                var mojiPokusaji = ApiConfig.retrofit.getListuPokusajaZaStudenta(AccountRepository.acHash).body()
                //var mojiPokusajiBody = mojiPokusaji.body()
                println("Moji pokusaji su " + mojiPokusaji)
                println("Kviz id je " + kviz.id)
                var mojPokusaj: Int = -1
                for (pokusaj in mojiPokusaji!!) {
                    if (pokusaj.KvizId == kviz.id) {
                        mojPokusaj = pokusaj.id
                        break
                    }
                }
                //znaci da nema pokusaja
                if (mojPokusaj == -1) continue
                var mojiOdgovori = ApiConfig.retrofit.getOdgovoreZaPokusaj(AccountRepository.acHash, mojPokusaj).body()
                //imamo pokusaje za nas kviz
                //sada trebamo naci bodove
                var pitanjaZaKviz = ApiConfig.retrofit.getPitanja(kviz.id).body()

                if (mojiOdgovori?.size != pitanjaZaKviz?.size) continue

                var trenutniBodovi: Double = 0.0
                //dosadasnji bodovi
                var ukupniBodoviKviza: Double = 0.0

                for (odgovor in mojiOdgovori!!) {
                    trenutniBodovi = 0.0
                    for (pitanje in pitanjaZaKviz!!) {
                        if (odgovor.pitanjeId == pitanje.id) {
                            if (pitanje.tacan == odgovor.odgovoreno) {
                                trenutniBodovi = 1.0
                            }
                            ukupniBodoviKviza += (trenutniBodovi.toDouble() / pitanjaZaKviz.size) * 100
                            break
                        }
                    }
                }
                kviz.osvojeniBodovi = ukupniBodoviKviza
                kviz.datumRada = Calendar.getInstance().time.toString()
            }
            var vratiListu : MutableList<Kviz> = mutableListOf()
            for(kviz in kvizovi) {
                if(kviz.osvojeniBodovi != null)
                    vratiListu.add(kviz)
            }
            return vratiListu.sortedBy { kviz -> kviz.datumPocetka }
        }

        suspend fun getMyKvizes(): List<Kviz> {
            //prvo azuriranje
            DBRepository.updateNow();
            var db = AppDatabase.getInstance(context)
            var kvizovi = db.kvizDao().getAll()
            for (kviz in kvizovi) {
                var mojiPokusaji = ApiConfig.retrofit.getListuPokusajaZaStudenta(AccountRepository.acHash).body()
                //var mojiPokusajiBody = mojiPokusaji.body()
                println("Moji pokusaji su " + mojiPokusaji)
                println("Kviz id je " + kviz.id)
                var mojPokusaj: Int = -1
                for (pokusaj in mojiPokusaji!!) {
                    if (pokusaj.KvizId == kviz.id) {
                        mojPokusaj = pokusaj.id
                        break
                    }
                }
                //znaci da nema pokusaja
                if (mojPokusaj == -1) continue
                var mojiOdgovori = ApiConfig.retrofit.getOdgovoreZaPokusaj(AccountRepository.acHash, mojPokusaj).body()
                //imamo pokusaje za nas kviz
                //sada trebamo naci bodove
                var pitanjaZaKviz = ApiConfig.retrofit.getPitanja(kviz.id).body()

                if (mojiOdgovori?.size != pitanjaZaKviz?.size) continue

                var trenutniBodovi: Double = 0.0
                //dosadasnji bodovi
                var ukupniBodoviKviza: Double = 0.0

                for (odgovor in mojiOdgovori!!) {
                    trenutniBodovi = 0.0
                    for (pitanje in pitanjaZaKviz!!) {
                        if (odgovor.pitanjeId == pitanje.id) {
                            if (pitanje.tacan == odgovor.odgovoreno) {
                                trenutniBodovi = 1.0
                            }
                            ukupniBodoviKviza += (trenutniBodovi.toDouble() / pitanjaZaKviz.size) * 100
                            break
                        }
                    }
                }
                kviz.osvojeniBodovi = ukupniBodoviKviza
                kviz.datumRada = Calendar.getInstance().time.toString()
            }
            return kvizovi.sortedBy { kviz -> kviz.datumPocetka }
        }

            suspend fun getAll(): List<Kviz>? {
                return withContext(Dispatchers.IO) {
                    var response = ApiConfig.retrofit.getAll()
                    val responseBody = response.body()

                    //ovdje treba popraviti
                    var idKorisnika: String = AccountRepository.acHash
                    var responseGrupe = ApiConfig.retrofit.getUpisaneGrupe(idKorisnika)
                    var responseGrupeBody = responseGrupe.body()
                    var listaKvizova = mutableListOf<Kviz>()
                    for (grupa in responseGrupeBody!!) {
                        var response = ApiConfig.retrofit.getKvizoveZaGrupu(grupa.id)
                        listaKvizova = (listaKvizova + response.body()!!).toMutableList()
                    }
                    for (kviz in listaKvizova) {
                        var mojiPokusaji = ApiConfig.retrofit.getListuPokusajaZaStudenta(AccountRepository.acHash).body()
                        //var mojiPokusajiBody = mojiPokusaji.body()
                        println("Moji pokusaji su " + mojiPokusaji)
                        println("Kviz id je " + kviz.id)
                        var mojPokusaj: Int = -1
                        for (pokusaj in mojiPokusaji!!) {
                            if (pokusaj.KvizId == kviz.id) {
                                mojPokusaj = pokusaj.id
                                break
                            }
                        }
                        //znaci da nema pokusaja
                        if (mojPokusaj == -1) continue
                        var mojiOdgovori = ApiConfig.retrofit.getOdgovoreZaPokusaj(AccountRepository.acHash, mojPokusaj).body()
                        //imamo pokusaje za nas kviz
                        //sada trebamo naci bodove
                        var pitanjaZaKviz = ApiConfig.retrofit.getPitanja(kviz.id).body()

                        //ako nije sve odgovoreno
                        if (mojiOdgovori?.size != pitanjaZaKviz?.size) continue

                        var trenutniBodovi: Double = 0.0
                        //dosadasnji bodovi
                        var ukupniBodoviKviza: Double = 0.0

                        for (odgovor in mojiOdgovori!!) {
                            trenutniBodovi = 0.0
                            for (pitanje in pitanjaZaKviz!!) {
                                if (odgovor.pitanjeId == pitanje.id) {
                                    if (pitanje.tacan == odgovor.odgovoreno) {
                                        trenutniBodovi = 1.0
                                    }
                                    ukupniBodoviKviza += (trenutniBodovi.toDouble() / pitanjaZaKviz.size) * 100
                                    break
                                }
                            }
                        }
                        kviz.osvojeniBodovi = ukupniBodoviKviza
                        kviz.datumRada = Calendar.getInstance().time.toString()
                    }

                    var vratiKivoze = responseBody as MutableList<Kviz>

                    for (jedanOdSvih in vratiKivoze) {
                        for (moj in listaKvizova) {
                            if (moj.id == jedanOdSvih.id)
                                vratiKivoze[vratiKivoze.indexOf(jedanOdSvih)] = moj
                        }
                    }

                    return@withContext vratiKivoze.sortedBy { kviz -> kviz.datumPocetka }
                }
            }

            fun getDone(): List<Kviz> {
                return emptyList()
            }

            fun getFuture(): List<Kviz> {
                return emptyList()
            }

            fun getNotTaken(): List<Kviz> {
                return emptyList()
            }

            suspend fun getById(id: Int): Kviz? {
                return null
            }





        suspend fun getUpisani(): List<Kviz>? {
            return withContext(Dispatchers.IO) {
                var idKorisnika: String = AccountRepository.acHash
                var responseGrupe = ApiConfig.retrofit.getUpisaneGrupe(idKorisnika)
                var responseGrupeBody = responseGrupe.body()
                var listaKvizova = mutableListOf<Kviz>()
                for(grupa in responseGrupeBody!!) {
                    var response =  ApiConfig.retrofit.getKvizoveZaGrupu(grupa.id)
                    listaKvizova = (listaKvizova + response.body()!!).toMutableList()
                }
                //trebas sada vidjet ovjde imal koji uradjen da mu bodove dodas
                for(kviz in listaKvizova) {
                    var mojiPokusaji = ApiConfig.retrofit.getListuPokusajaZaStudenta(AccountRepository.acHash).body()
                    //var mojiPokusajiBody = mojiPokusaji.body()
                    println("Moji pokusaji su " + mojiPokusaji)
                    println("Kviz id je " + kviz.id)
                    var mojPokusaj: Int = -1
                    for(pokusaj in mojiPokusaji!!) {
                        if(pokusaj.KvizId == kviz.id) {
                            mojPokusaj = pokusaj.id
                            break
                        }
                    }
                    //znaci da nema pokusaja
                    if(mojPokusaj == -1) continue
                    var mojiOdgovori = ApiConfig.retrofit.getOdgovoreZaPokusaj(AccountRepository.acHash, mojPokusaj).body()
                    //imamo pokusaje za nas kviz
                    //sada trebamo naci bodove
                    var pitanjaZaKviz = ApiConfig.retrofit.getPitanja(kviz.id).body()

                    if(mojiOdgovori?.size != pitanjaZaKviz?.size) continue

                    var trenutniBodovi: Double = 0.0
                    //dosadasnji bodovi
                    var ukupniBodoviKviza: Double = 0.0

                    for(odgovor in mojiOdgovori!!) {
                        trenutniBodovi  = 0.0
                        for(pitanje in pitanjaZaKviz!!) {
                            if(odgovor.pitanjeId == pitanje.id) {
                                if(pitanje.tacan == odgovor.odgovoreno) {
                                    trenutniBodovi = 1.0
                                }
                                ukupniBodoviKviza += (trenutniBodovi.toDouble() / pitanjaZaKviz.size) * 100
                                break
                            }
                        }
                    }
                    kviz.osvojeniBodovi = ukupniBodoviKviza
                    kviz.datumRada = Calendar.getInstance().time.toString()

                }

                return@withContext listaKvizova.sortedBy { kviz  -> kviz.datumPocetka }
            }
        }

        suspend fun getKvizoveZaGrupu(id: Int): List<Kviz>? {
                return withContext(Dispatchers.IO) {
                    var response = ApiConfig.retrofit.getKvizoveZaGrupu(id)
                    val responseBody = response.body()

                    return@withContext responseBody?.sortedBy { kviz -> kviz.datumPocetka }
                }
            }
    }
}