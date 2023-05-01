package ba.etf.rma21.projekat.viewmodel

import android.content.Context
import ba.etf.rma21.projekat.data.models.Account
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.data.repositories.AccountRepository
import ba.etf.rma21.projekat.data.repositories.DBRepository
import ba.etf.rma21.projekat.data.repositories.PredmetIGrupaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class PredmetListaViewModel {

    val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun getSvePredmete(onSuccess: (predmeti: List<Predmet>) -> Unit, onError: () -> Unit) {
        // Create a new coroutine on the UI thread
        scope.launch{
            // Make the network call and suspend execution until it finishes
            val result = PredmetIGrupaRepository.getPredmeti()
            println("Ovdje smo dobili sve predmete " + result)
            // Display result of the network request to the user
            when (result) {
                is List<Predmet> -> onSuccess?.invoke(result)
                else-> onError?.invoke()
            }
        }
    }
    fun getSveMojePredmete(onSuccess: (predmeti: List<Predmet>) -> Unit, onError: () -> Unit) {
        // Create a new coroutine on the UI thread
        scope.launch{
            // Make the network call and suspend execution until it finishes
            val resultMojeGrupe = PredmetIGrupaRepository.getUpisaneGrupe()
            val resultMojiPredmeti: MutableList<Predmet>? = mutableListOf()
            for(grupa in resultMojeGrupe!!) {
                resultMojiPredmeti!!.add(PredmetIGrupaRepository.getPredmetZaID(grupa.predmetId)!!)
            }
            println("Svi moji predmeti su")
            for(predmet in resultMojiPredmeti!!) {
                println("Predmet je " + predmet)
            }

            val result = PredmetIGrupaRepository.getPredmeti()
            println("Ovdje smo dobili sve predmete " + result)
            // Display result of the network request to the user
            when (result) {
                is List<Predmet> -> onSuccess?.invoke(result)
                else-> onError?.invoke()
            }
        }

    }

    fun getSveMojePredmeteNaKojeNisamUpisan(onSuccess: (predmeti: List<Predmet>) -> Unit, onError: () -> Unit) {
        // Create a new coroutine on the UI thread
        scope.launch{
            // Make the network call and suspend execution until it finishes
            val resultMojeGrupe = PredmetIGrupaRepository.getUpisaneGrupe()
            val resultMojiPredmeti: MutableList<Predmet>? = mutableListOf()
            for(grupa in resultMojeGrupe!!) {
                resultMojiPredmeti!!.add(PredmetIGrupaRepository.getPredmetZaID(grupa.predmetId)!!)
            }
            var result = PredmetIGrupaRepository.getPredmeti()
            result = result!!.toMutableList()
            for(predmet in resultMojiPredmeti!!) {
                if(result!!.contains(predmet))
                    result.remove(predmet)
            }
            println("Ovdje smo dobili sve predmete na koje nisam upisan" + result)
            // Display result of the network request to the user
            when (result) {
                is List<Predmet> -> onSuccess?.invoke(result)
                else-> onError?.invoke()
            }
        }

    }



//    fun getSvePredmete():List<Predmet>{
//        return PredmetRepository.getAll();
//    }
//
//    fun getPredmeteZaGodinu(godina: Int):List<Predmet>{
//        return PredmetRepository.getPredmetsByGodina(godina);
//    }
//    fun getPredmeteZaGodinuNaziv(godina: Int):List<String> {
//        return PredmetRepository.getPredmetsByGodinaNaziv(godina);
//    }
//    fun getPredmeteNaKojeNisamUpisan(godina: Int):List<String> {
//        return PredmetRepository.getPredmeteNaKojimNisam(godina)
//    }
//    fun dodajPredmet(ime: String, godina: Int) {
//         KorisnikRepository.dodajPredmet(ime, godina)
//    }
//    fun dajMojePredmete(): List<Predmet> {
//        return KorisnikRepository.dajMojePredmete()
//    }

}