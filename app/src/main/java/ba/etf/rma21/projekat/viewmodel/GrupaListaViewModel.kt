package ba.etf.rma21.projekat.viewmodel

import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.repositories.DBRepository
import ba.etf.rma21.projekat.data.repositories.PredmetIGrupaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class GrupaListaViewModel {

    val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun getSveGrupe(onSuccess: (grupa: List<Grupa>) -> Unit, onError: () -> Unit) {
        // Create a new coroutine on the UI thread
        scope.launch{
            // Make the network call and suspend execution until it finishes
            val result = PredmetIGrupaRepository.getGrupe()
            DBRepository.updateNow();
            println("Ovdje smo dobili sve grupe " + result)
            // Display result of the network request to the user
            when (result) {
                is List<Grupa> -> onSuccess?.invoke(result)
                else-> onError?.invoke()
            }
        }
    }
    fun getGrupeZaPredmet(idPredmeta: Int, onSuccess: (grupa: List<Grupa>) -> Unit, onError: () -> Unit) {
        scope.launch{
            // Make the network call and suspend execution until it finishes
            val result = PredmetIGrupaRepository.getGrupeZaPredmet(idPredmeta)
            println("Ovdje smo dobili sve grupe za idPredmeta" + result)
            DBRepository.updateNow();
            // Display result of the network request to the user
            when (result) {
                is List<Grupa> -> onSuccess?.invoke(result)
                else-> onError?.invoke()
            }
        }
    }
    fun upisiMeNaKviz(idGrupe: Int, onSuccess: (poruka: String) -> Unit, onError: () -> Unit) {
        scope.launch{
            // Make the network call and suspend execution until it finishes
            val result = PredmetIGrupaRepository.upisiUGrupu(idGrupe)
            DBRepository.updateNow();
            // Display result of the network request to the user
            when (result) {
                is Boolean -> onSuccess?.invoke(result.toString())
                else-> onError?.invoke()
            }
        }
    }

    fun getUpisaneGrupe(onSuccess: (grupe: List<Grupa>) -> Unit, onError: () -> Unit) {
        scope.launch{
            // Make the network call and suspend execution until it finishes
            val result = PredmetIGrupaRepository.getUpisaneGrupe()
            DBRepository.updateNow();
            // Display result of the network request to the user
            when (result) {
                is List<Grupa> -> onSuccess?.invoke(result)
                else-> onError?.invoke()
            }
        }
    }


//    fun getGrupe(predmet: String):List<Grupa>{
//        return PredmetIGrupaRepository.getGroupsByPredmet(predmet)
//    }
//    fun getGrupeZaPredmetNaziv(predmet: String):List<String> {
//        return PredmetIGrupaRepository.getGrupeZaPredmetNaziv(predmet);
//    }
//    fun dodajGrupu(grupa: String, predmet: String) {
//        PredmetIGrupaRepository.dodajGrupu(grupa, predmet)
//    }
//    fun dajMojeGrupe() : List<Grupa> {
//        return KorisnikRepository.dajMojeGrupe()
//    }

}