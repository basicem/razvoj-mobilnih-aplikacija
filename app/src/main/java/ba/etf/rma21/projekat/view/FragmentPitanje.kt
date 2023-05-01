package ba.etf.rma21.projekat.view

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ba.etf.rma21.projekat.MainActivity
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Odgovor
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.repositories.OdgovorRepository
import ba.etf.rma21.projekat.viewmodel.OdgovorViewModel
import ba.etf.rma21.projekat.viewmodel.SharedViewModel
//import ba.etf.rma21.projekat.viewmodel.SharedViewModel
//import ba.etf.rma21.projekat.data.repositories.KorisnikRepository.Companion.dajMojeKvizove
import java.text.SimpleDateFormat
import java.util.*


class FragmentPitanje(pitanje:Pitanje): Fragment() {
    var novoPitanje:Pitanje = pitanje
    lateinit var  tekstPitanja: TextView
    lateinit var odgovoriLista: ListView
    lateinit var viewModel: SharedViewModel
    var odgovorViewModel: OdgovorViewModel = OdgovorViewModel()
    private var  tvPoruka: Int = 0
    private var kvizTrenutni: Int = 0

    fun onError() {
        val toast = Toast.makeText(context, "Greška!", Toast.LENGTH_SHORT)
        toast.show()
    }
    fun onSuccess(bodovi: Int) {
        val toast = Toast.makeText(context, "Sve OKI!", Toast.LENGTH_SHORT)
        toast.show()
        viewModel.sendOdgovor(novoPitanje.naziv, novoPitanje.opcije.get(0), 0)
    }
    fun onSuccessOboji(odgovori: List<Odgovor>) {
        val toast = Toast.makeText(context, "Imamo nase odgovore!", Toast.LENGTH_SHORT)
        toast.show()
        var pozicija = -1
        lateinit var mOdgovor: Odgovor
        for(odg in odgovori) {
            println("Moji odgovori " + odg.pitanjeId)
            if(odg.pitanjeId == novoPitanje.id) {
                println("Moj odgovor na pitanje sa id-om " + odg.pitanjeId)
                mOdgovor = odg
                pozicija = mOdgovor.odgovoreno
                break
            }
        }
        if(pozicija == -1) return
        //ovo je za tacni odgovor

        if(mOdgovor.odgovoreno < novoPitanje.opcije.size) {
            var odgovor:TextView = odgovoriLista.get(novoPitanje.tacan) as TextView
            odgovor.setBackgroundColor(Color.parseColor("#3DDC84"))
            var mojOdgovor: TextView = mOdgovor.let { odgovoriLista.get(it.odgovoreno) } as TextView
            var boja:String = "#3DDC84"
            if(novoPitanje.tacan != mOdgovor.odgovoreno) {
                mojOdgovor.setBackgroundColor(Color.parseColor("#DB4F3D"))
                boja = "#DB4F3D"
            }
            else {
                mojOdgovor.setBackgroundColor(Color.parseColor("#3DDC84"))
            }
        }
        odgovoriLista.isEnabled = false

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.pitanje_fragment, container, false)
        tvPoruka = arguments?.getInt("kvizTakenID") as Int
        kvizTrenutni = arguments?.getInt("kvizNovi") as Int
        tekstPitanja = view.findViewById(R.id.tekstPitanja)
        odgovoriLista = view.findViewById(R.id.odgovoriLista)
        tekstPitanja.setText(novoPitanje.tekstPitanja)

        //treba smjestiti odgovore na pitanje
        var odgovori = novoPitanje.opcije
        var adapter:ArrayAdapter<String> = ArrayAdapter(tekstPitanja.context, android.R.layout.simple_list_item_1, odgovori)
        odgovoriLista.adapter = adapter
        odgovorViewModel.getOdgovoriKviz(kvizTrenutni,
                onSuccess = ::onSuccessOboji,
                onError = ::onError)


        odgovoriLista.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                obojiOpciju(position)

            }
        }
        return view;
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(activity as MainActivity)[SharedViewModel::class.java]


    }
    fun obojiOpciju(pozicija:Int) {
        //trebas ovdje naci koji ti je idKvizTaken
        odgovorViewModel.dodajOdgovor(tvPoruka, novoPitanje.id, pozicija,
                onSuccess = ::onSuccess,
                onError = ::onError)

        var odgovor:TextView = odgovoriLista.get(novoPitanje.tacan) as TextView
        odgovor.setBackgroundColor(Color.parseColor("#3DDC84"))
        var mojOdgovor: TextView = odgovoriLista.get(pozicija) as TextView
        //pretpostavka da je tacna boja
        var boja:String = "#3DDC84"
        if(novoPitanje.tacan != pozicija) {
            mojOdgovor.setBackgroundColor(Color.parseColor("#DB4F3D"))
            boja = "#DB4F3D"
        }
        else {
            mojOdgovor.setBackgroundColor(Color.parseColor("#3DDC84"))
        }
        odgovoriLista.isEnabled = false

    }

    companion object {
        fun newInstance(pitanje:Pitanje): FragmentPitanje = FragmentPitanje(pitanje)
    }
}