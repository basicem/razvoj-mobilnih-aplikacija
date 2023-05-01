package ba.etf.rma21.projekat.viewmodel

import ba.etf.rma21.projekat.data.models.KvizTaken
import ba.etf.rma21.projekat.data.models.Odgovor
import ba.etf.rma21.projekat.data.repositories.OdgovorRepository
import ba.etf.rma21.projekat.data.repositories.TakeKvizRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class OdgovorViewModel {

    val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun dodajOdgovor(idKvizTaken: Int, idPitanje: Int, odgovor: Int,  onSuccess: (bodovi: Int) -> Unit, onError: () -> Unit) {
        // Create a new coroutine on the UI thread
        scope.launch{
            // Make the network call and suspend execution until it finishes
            println("U viewmodelu je " + idKvizTaken)
            val result = OdgovorRepository.postaviOdgovorKviz(idKvizTaken, idPitanje, odgovor)
            // Display result of the network request to the user
            when (result) {
                is Int -> onSuccess?.invoke(result)
                else-> onError?.invoke()
            }
        }
    }

    fun getOdgovoriKviz(idKviz: Int, onSuccess: (odgovori: List<Odgovor>) -> Unit, onError: () -> Unit) {
        // Create a new coroutine on the UI thread
        scope.launch{
            // Make the network call and suspend execution until it finishes
            println("U viewmodelu je " + idKviz)
            val result = OdgovorRepository.getOdgovoriKviz(idKviz)
            // Display result of the network request to the user
            when (result) {
                is List<Odgovor> -> onSuccess?.invoke(result)
                else-> onError?.invoke()
            }
        }
    }
    fun predajOdgovore(idKviz: Int, onSuccess: (odgovori: List<Odgovor>) -> Unit, onError: () -> Unit) {
        scope.launch{
            // Make the network call and suspend execution until it finishes
            println("U viewmodelu je " + idKviz)
            val result = OdgovorRepository.predajOdgovore(idKviz)
            // Display result of the network request to the user
            when (result) {
                is List<Odgovor> -> onSuccess?.invoke(result)
                else-> onError?.invoke()
            }
        }
    }
}