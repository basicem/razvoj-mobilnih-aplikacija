//package ba.etf.rma21.projekat.data.repositories
//
//import ba.etf.rma21.projekat.data.models.*
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.withContext
//
//
//class OdgovorRepository {
//    companion object {
//
//        suspend fun getOdgovoriKviz(idKviza: Int?): List<Odgovor>? {
//            return withContext(Dispatchers.IO) {
//                var idPokusaj:Int = TakeKvizRepository.zapocniKviz(idKviza!!)!!.id
//                var response = ApiConfig.retrofit.getOdgovoreZaPokusaj(AccountRepository.acHash, idPokusaj)
//
//                if(response.message() != "OK")
//                    return@withContext emptyList<Odgovor>()
//                val responseBody = response.body()
//                return@withContext responseBody
//            }
//        }
//
//        suspend fun postaviOdgovorKviz(idKvizTaken: Int, idPitanje: Int, odgovor: Int): Int? {
//            return withContext(Dispatchers.IO) {
//                var mojiPokusaji: List<KvizTaken>? = TakeKvizRepository.getPocetiKvizovi()
//                var idKviz:Int = -1
//                for(pokusaj in mojiPokusaji!!) {
//                    println("Pokusaj je " + pokusaj.id + " a moj idKvizTaken je " + idKvizTaken)
//                    if(pokusaj.id == idKvizTaken) {
//                        idKviz = pokusaj.KvizId!!
//                        break
//                    }
//                }
//                //nasli smo koji kviz radimo
//                var kviz: Kviz = KvizRepository.getById(idKviz)!!
//                //moji odgovori za pokusaj kviza
//                var odgovoriZaKviz:List<Odgovor> = OdgovorRepository.getOdgovoriKviz(idKviz)!!
//                //pitanja za kviz
//                println("ID kviza je " + idKviz)
//                var pitanjaZaKviz: List<Pitanje>? = PitanjeKvizRepository.getPitanja(idKviz)!!
//                var trenutniBodovi: Double = 0.0
//                //dosadasnji bodovi
//                var ukupniBodoviKviza: Double = 0.0
//
//                for(odgovor in odgovoriZaKviz) {
//                    trenutniBodovi  = 0.0
//                    for(pitanje in pitanjaZaKviz!!) {
//                        if(odgovor.pitanjeId == pitanje.id) {
//                            if(pitanje.tacan == odgovor.odgovoreno) {
//                                trenutniBodovi = 1.0
//                            }
//                            ukupniBodoviKviza += (trenutniBodovi.toDouble() / pitanjaZaKviz.size) * 100
//                            break
//                        }
//                    }
//                }
//
//                for(pitanje in pitanjaZaKviz!!) {
//                    if(pitanje.id == idPitanje) {
//                        if(pitanje.tacan == odgovor) {
//                            trenutniBodovi = 1.0
//                            trenutniBodovi = (trenutniBodovi.toDouble() / pitanjaZaKviz.size) * 100
//                            break
//                        }
//                    }
//                }
//                ukupniBodoviKviza = ukupniBodoviKviza + trenutniBodovi
//                println("Moji trenutni UKUPNI bodovi na kvizu su: " + ukupniBodoviKviza)
//                var podatakOdgovor: PodatakOdgovor = PodatakOdgovor(odgovor, idPitanje, 0.0)
//                var response = ApiConfig.retrofit.dodajOdgovorZaKvizIStudenta(AccountRepository.acHash, idKvizTaken, podatakOdgovor)
//                println("Poruka je da nema poruke jelde " + response!!.message())
//                return@withContext ukupniBodoviKviza.toInt()
////                val responseBody = response.body()
////                return@withContext responseBody
//            }
//        }
//    }


//}

package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.models.*


class OdgovorRepository {
    companion object {
        private lateinit var context: Context
        fun setContext(_context:Context){
            context=_context
        }
        suspend fun getOdgovoriKviz(idKviza: Int): List<Odgovor>? {
            var db = AppDatabase.getInstance(context)
            var sviOdgovori = db.odgovorDao().getAll()
            var vrati = mutableListOf<Odgovor>()
            for(odg in sviOdgovori) {
                if(odg.kvizId == idKviza)
                    vrati.add(odg)
            }
            return vrati
        }

        suspend fun postaviOdgovorKviz(idKvizTaken: Int, idPitanje: Int, odgovor: Int): Int? {
            var db = AppDatabase.getInstance(context)
            var mojiPokusaji: List<KvizTaken>? = TakeKvizRepository.getPocetiKvizovi()
            var idKviz:Int = -1
            for(pokusaj in mojiPokusaji!!) {
                println("Pokusaj je " + pokusaj.id + " a moj idKvizTaken je " + idKvizTaken)
                if(pokusaj.id == idKvizTaken) {
                    idKviz = pokusaj.KvizId!!
                    break
                }
            }
            //imamo id kviza koji radimo
            var id =0;
            var odgovori = db.odgovorDao().getAll()
            if(odgovori.size == 0) {
                id = 0;
            }
            else {
                for(odgovor in odgovori)
                    id++;
            }
            var  posalji = Odgovor(id, odgovor,idPitanje,idKviz,idKvizTaken)
            var dodaj = true
            for(answ in odgovori) {
                if(answ.pitanjeId == posalji.pitanjeId)
                    dodaj = false;
            }
            if(dodaj)
                db.odgovorDao().insertAll(posalji);

            //var kviz: Kviz = KvizRepository.getById(idKviz)!!
            //moji odgovori za pokusaj kviza
            var odgovoriZaKviz:List<Odgovor> = OdgovorRepository.getOdgovoriKviz(idKviz)!!
            //pitanja za kviz
            println("ID kviza je " + idKviz)
            var pitanjaZaKviz: List<Pitanje>? = PitanjeKvizRepository.getPitanja(idKviz)!!
            var trenutniBodovi: Double = 0.0
            //dosadasnji bodovi
            var ukupniBodoviKviza: Double = 0.0

            for(o in odgovoriZaKviz) {
                trenutniBodovi  = 0.0
                for(pitanje in pitanjaZaKviz!!) {
                    if(o.pitanjeId == pitanje.id) {
                        if(pitanje.tacan == o.odgovoreno) {
                            trenutniBodovi = 1.0
                        }
                        ukupniBodoviKviza += (trenutniBodovi.toDouble() / pitanjaZaKviz.size) * 100
                        break
                    }
                }
            }
            for(pitanje in pitanjaZaKviz!!) {
                if(pitanje.id == idPitanje) {
                    if(pitanje.tacan == odgovor) {
                        trenutniBodovi = 1.0
                        trenutniBodovi = (trenutniBodovi.toDouble() / pitanjaZaKviz.size) * 100
                        break
                    }
                }
            }
            return ukupniBodoviKviza.toInt()
        }

        suspend fun predajOdgovore(idKviz: Int) : List<Odgovor>{
            var db = AppDatabase.getInstance(context)
            var idKvizTaken:Int = -1
            var mojiPokusaji: List<KvizTaken>? = TakeKvizRepository.getPocetiKvizovi()

            for(pokusaj in mojiPokusaji!!) {
                if (pokusaj.KvizId == idKviz) {
                    idKvizTaken = pokusaj.id!!
                    break
                }
            }
            //moji odgovori za pokusaj kviza
            var odgovoriZaKviz:List<Odgovor> = OdgovorRepository.getOdgovoriKviz(idKviz)!!
            //pitanja za kviz
            println("ID kviza je " + idKviz)
            var pitanjaZaKviz: List<Pitanje>? = PitanjeKvizRepository.getPitanja(idKviz)!!
            var trenutniBodovi: Double = 0.0
            //dosadasnji bodovi
            var ukupniBodoviKviza: Double = 0.0

            for(odgovor in odgovoriZaKviz) {
                trenutniBodovi  = 0.0
                for(pitanje in pitanjaZaKviz!!) {
                    if(odgovor.pitanjeId == pitanje.id) {
                        if(pitanje.tacan == odgovor.odgovoreno) {
                            trenutniBodovi = 1.0
                            var podatakOdgovor: PodatakOdgovor = PodatakOdgovor(odgovor.odgovoreno, pitanje.id, 1.0)
                            ApiConfig.retrofit.dodajOdgovorZaKvizIStudenta(AccountRepository.acHash, idKvizTaken, podatakOdgovor)
                        }
                        else {
                            var podatakOdgovor: PodatakOdgovor = PodatakOdgovor(odgovor.odgovoreno, pitanje.id, 1.0)
                            ApiConfig.retrofit.dodajOdgovorZaKvizIStudenta(AccountRepository.acHash, idKvizTaken, podatakOdgovor)
                        }
                        ukupniBodoviKviza += (trenutniBodovi.toDouble() / pitanjaZaKviz.size) * 100
                        break
                    }
                }
            }
            var sviKvizovi = db.kvizDao().getAll()
            for(kviz in sviKvizovi) {
                if (kviz.id == idKviz) {
                    db.kvizDao().updateStanje(kviz.id, "true");
                }
            }
            return odgovoriZaKviz;
        }
    }

}