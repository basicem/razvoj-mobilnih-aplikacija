package ba.etf.rma21.projekat.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ba.etf.rma21.projekat.data.models.Kviz

class SharedViewModel : ViewModel() {
    var odgovorKorisnika = MutableLiveData<MutableList<Triple<String, String, Int>>>()
    var trenutniKviz = MutableLiveData<Kviz>()

    //odgovor na pitanje
    fun sendOdgovor(pitanje: String, odgovor: String, redniBrojodgovor: Int) {
        odgovorKorisnika.addNewItem(Triple(pitanje, odgovor, redniBrojodgovor))
    }
    fun getOdgovor() : LiveData<MutableList<Triple<String, String, Int>>> {
        return  odgovorKorisnika
    }
    fun setKviz(imeKviza:Kviz) {
        trenutniKviz.value = imeKviza
    }
    fun getKviz() : LiveData<Kviz> {
        return trenutniKviz
    }

    fun <T> MutableLiveData<MutableList<T>>.addNewItem(item: T) {
        val oldValue = this.value ?: mutableListOf()
        oldValue.add(item)
        this.value = oldValue
    }

    fun <T> MutableLiveData<MutableList<T>>.addNewItemAt(index: Int, item: T) {
        val oldValue = this.value ?: mutableListOf()
        oldValue.add(index, item)
        this.value = oldValue
    }

    fun <T> MutableLiveData<MutableList<T>>.removeItemAt(index: Int) {
        if (!this.value.isNullOrEmpty()) {
            val oldValue = this.value
            oldValue?.removeAt(index)
            this.value = oldValue
        } else {
            this.value = mutableListOf()
        }
    }

}