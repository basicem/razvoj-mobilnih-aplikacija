package ba.etf.rma21.projekat.viewmodel

import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.KvizTaken
import ba.etf.rma21.projekat.data.repositories.PredmetIGrupaRepository
import ba.etf.rma21.projekat.data.repositories.TakeKvizRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class KvizTakenViewModel {

    val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun zapocniKviz(idKviza: Int, onSuccess: (kvizPokusaj: KvizTaken) -> Unit, onError: () -> Unit) {
        // Create a new coroutine on the UI thread
        scope.launch{
            // Make the network call and suspend execution until it finishes
            val result = TakeKvizRepository.zapocniKviz(idKviza)
            println("Ovdje smo dobili kvizTaken nadam se:  " + result)
            // Display result of the network request to the user
            when (result) {
                is KvizTaken -> onSuccess?.invoke(result)
                else-> onError?.invoke()
            }
        }
    }

    fun getPocetiKvizovi(onSuccess: (kvizPokusaj:  List<KvizTaken>) -> Unit, onError: () -> Unit) {
        scope.launch{
            // Make the network call and suspend execution until it finishes
            val result = TakeKvizRepository.getPocetiKvizovi()
            println("Ovdje smo dobili kvizTaken nadam se:  " + result)
            // Display result of the network request to the user
            when (result) {
                is List<KvizTaken> -> onSuccess?.invoke(result)
                else-> onError?.invoke()
            }
        }
    }
}