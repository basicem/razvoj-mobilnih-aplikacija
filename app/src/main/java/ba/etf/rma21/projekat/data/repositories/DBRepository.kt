package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.dao.AccountDAO
import ba.etf.rma21.projekat.data.models.Account
import ba.etf.rma21.projekat.data.models.Pitanje
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class DBRepository {
    companion object{
        private lateinit var context:Context
        fun setContext(_context: Context){
            context=_context
        }
        suspend fun updateNow():Boolean{
            var pomocna: Boolean
            withContext(Dispatchers.IO) {
                var db = AppDatabase.getInstance(context)
                var response = ApiConfig.retrofit.getUpdate(AccountRepository.getHash(), db.accountDao().getDate())
                val responseBody = response.body()?.changed

                if (responseBody == true) {
                    db.pitanjaDao().obrisiSve()
                    db.grupaDao().obrisiSve()
                    db.predmetDao().obrisiSve()
                    db.kvizDao().obrisiSve()
                    db.odgovorDao().obrisiSve()
                    var grupe = PredmetIGrupaRepository.getUpisaneGrupe();
                    var predmeti = PredmetIGrupaRepository.getUpisani();
                    var kvizovi = KvizRepository.getUpisani();
                    var pitanja = mutableSetOf<Pitanje>()


                    for(kviz in kvizovi!!) {
                        var pitanjaBaza = PitanjeKvizRepository.getPitanja(kviz.id);
                        for(p in pitanjaBaza!!) {
                            p.KvizId = mutableListOf();
                            pitanja.add(p)
                        }
                    }

                    for(p in pitanja) {
                        for(kviz in kvizovi) {
                            var svaPitanja = PitanjeKvizRepository.getPitanja(kviz.id);

                            for(sva in svaPitanja!!) {
                                if(sva.id == p.id) {
                                    p.KvizId.add(kviz.id.toString())
                                }
                            }
                        }
                    }

                    for(pitanje in pitanja) {
                        print("Ovo pitanje pripada: " + pitanje.KvizId.toString())
                    }

                    //kvizovi
                    for (kviz in kvizovi!!) {
                        var kvizovi = db.kvizDao().getAll()
                        var dodaj: Boolean = true
                        for(k in kvizovi) {
                            if(k.id == kviz.id)
                                dodaj = false
                        }
                        if(dodaj == true)
                            db.kvizDao().insertAll(kviz)
                    }

                    //predmeti
                    for (predmet in predmeti) {
                        var predmetiIzBaze = db.predmetDao().getAll()
                        var dodaj: Boolean = true
                        for(k in predmetiIzBaze) {
                            if(k.id == predmet.id)
                                dodaj = false
                        }
                        if(dodaj == true)
                            db.predmetDao().insertAll(predmet)

                        //db.predmetDao().insertAll(predmet)
                    }
                    //grupe
                    for (grupa in grupe!!) {
                        db.grupaDao().insertAll(grupa)
                    }
                    //pitanja
                    for (pitanje in pitanja) {
                        var svaPitanjaIzBaze = db.pitanjaDao().getAll()
                        var dodaj: Boolean = true
                        for(k in svaPitanjaIzBaze) {
                            if(k.id == pitanje.id)
                                dodaj = false
                        }
                        if(dodaj == true)
                            db.pitanjaDao().insertAll(pitanje)
                    }
                    val datum = Calendar.getInstance().time;
                    val pattern = "yyyy-MM-dd'T'HH:mm:ss"
                    val formater = SimpleDateFormat(pattern, Locale.ENGLISH)
                    var string: String = formater.format(datum);

                    db.accountDao().delete(Account(AccountRepository.getHash(), string))
                    db.accountDao().insertAll(Account(AccountRepository.getHash(), string));
                    pomocna = true;

                }
                else {
                    pomocna = false;
                }
            }
            return pomocna
        }
    }
}