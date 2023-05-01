package ba.etf.rma21.projekat.viewmodel

import android.content.Context
import ba.etf.rma21.projekat.MainActivity
import ba.etf.rma21.projekat.data.models.Account
import ba.etf.rma21.projekat.data.repositories.AccountRepository
import ba.etf.rma21.projekat.data.repositories.DBRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AccountViewModel {
    val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun postaviHash(it: Context, hash: String, onSuccess: () -> Unit, onError: () -> Unit) {
        scope.launch{
            // Make the network call and suspend execution until it finishes
            AccountRepository.setContext(it);
            val result = AccountRepository.postaviHash(hash);
            println("Ovdje smo dobili korisnika " + result)
        }
    }

}