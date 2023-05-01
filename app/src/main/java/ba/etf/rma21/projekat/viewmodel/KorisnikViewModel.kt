package ba.etf.rma21.projekat.viewmodel

import ba.etf.rma21.projekat.data.models.Account
import ba.etf.rma21.projekat.data.repositories.AccountRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class KorisnikViewModel {

    val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun dajKorisnika(onSuccess: (korisnik: Account) -> Unit, onError: () -> Unit) {
         //Create a new coroutine on the UI thread
        scope.launch{
            // Make the network call and suspend execution until it finishes
            val result = AccountRepository.getKorisnik()
            println("Ovdje smo dobili korisnika " + result)
            // Display result of the network request to the user
            when (result) {
                is Account -> onSuccess?.invoke(result)
                else-> onError?.invoke()
            }
        }
    }

}