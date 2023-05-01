package ba.etf.rma21.projekat.viewmodel

import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.repositories.PitanjeKvizRepository
import ba.etf.rma21.projekat.data.repositories.PredmetIGrupaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class PitanjeKvizViewModel {
    //var statusKviza: MutableMap<Kviz,String> = mutableMapOf()
    val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun getPitanjaZaKviz(kviz: Kviz, onSuccess: (grupa: List<Pitanje>) -> Unit, onError: () -> Unit){
        scope.launch{
            // Make the network call and suspend execution until it finishes
            val result = PitanjeKvizRepository.getPitanja(kviz.id!!)
            println("Ovdje smo dobili sva pitanja za kviz " + result)
            // Display result of the network request to the user
            when (result) {
                is List<Pitanje> -> onSuccess?.invoke(result)
                else-> onError?.invoke()
            }
        }
    }
}