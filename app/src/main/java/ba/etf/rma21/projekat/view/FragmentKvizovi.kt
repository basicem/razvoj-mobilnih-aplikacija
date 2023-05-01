package ba.etf.rma21.projekat.view

import android.R.attr.fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma21.projekat.MainActivity
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.KvizTaken
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.viewmodel.KvizListaViewModel
import ba.etf.rma21.projekat.viewmodel.KvizTakenViewModel
import ba.etf.rma21.projekat.viewmodel.PitanjeKvizViewModel
import ba.etf.rma21.projekat.viewmodel.SharedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


//import ba.etf.rma21.projekat.viewmodel.SharedViewModel


class FragmentKvizovi : Fragment(){
    private lateinit var posljednjeStanje: String
    private lateinit var listaKvizova: RecyclerView
    private var kvizListaViewModel = KvizListaViewModel()
    private lateinit var sviKvizoviAdapter: KvizoviAdapter
    private var pitanjeKvizViewModel = PitanjeKvizViewModel()
    private var listaPitanja : List<Pitanje> = listOf()
    private var kvizTakenViewModel: KvizTakenViewModel = KvizTakenViewModel()
    private lateinit var kvizNovi:Kviz
    private var kvizTakenID = -1
    lateinit var viewModel: SharedViewModel
    lateinit var sviMojiKvizovi: List<Kviz>

    private lateinit var filterKvizova: Spinner

    fun onSuccess(quizzes: List<Kviz>){
        GlobalScope.launch(Dispatchers.IO){
            withContext(Dispatchers.Main){
                sviKvizoviAdapter.updateTest(quizzes)
            }
        }
    }
    fun onError() {
        val toast = Toast.makeText(context, "Greška!", Toast.LENGTH_SHORT)
        toast.show()
    }
    fun onSuccessPokusaj(kvizTaken: KvizTaken) {
        val toast = Toast.makeText(context, "Upisali smo se!", Toast.LENGTH_SHORT)
        toast.show()

        println("KvizTaken je " + kvizTaken)
        kvizTakenID = kvizTaken.id
        println("JEL BAREM OVJDE OK " + kvizTakenID)

        pitanjeKvizViewModel.getPitanjaZaKviz(kvizNovi,
                onSuccess = ::onSuccessPitanja,
                onError = ::onError
        )
    }
    fun onSuccessMoji(mojiKvizovi: List<Kviz>) {
        sviMojiKvizovi = mojiKvizovi
    }

    fun onSuccessPitanja(quizzes: List<Pitanje>) {
        listaPitanja = quizzes
            val toast = Toast.makeText(context, "Pronasli smo pitanja!", Toast.LENGTH_SHORT)
            toast.show()
            val bundle = Bundle()
        println("NE  " + kvizTakenID)
            bundle.putInt("kvizTakenID", kvizTakenID)
            bundle.putInt("kvizNovi", kvizNovi.id)
            val pokusajFragment = FragmentPokusaj.newInstance(listaPitanja)
            val f = fragmentManager?.beginTransaction();
            if (f != null) {
                pokusajFragment.arguments = bundle
                f.replace(R.id.container, pokusajFragment, "fragmentPokusaj")
                f.commit()
                f.addToBackStack(null)
            }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        val view  = inflater.inflate(R.layout.kvizovi, container, false)
        //kvizovi
        listaKvizova = view.findViewById(R.id.listaKvizova)
        listaKvizova.layoutManager = GridLayoutManager(listaKvizova.context, 2);

        sviKvizoviAdapter = KvizoviAdapter(listOf()) { kviz -> otvoriFragmentPokusaj(kviz) }
        listaKvizova.adapter = sviKvizoviAdapter
        kvizListaViewModel.getSviKvizovi(
                onSuccess = ::onSuccess,
                onError = ::onError
        )
        kvizListaViewModel.getMojiKvizovi(
                onSuccess = ::onSuccessMoji,
                onError = ::onError
        )

        //spiner
        filterKvizova = view.findViewById(R.id.filterKvizova)
        var filteri = arrayOf("Svi kvizovi", "Svi moji kvizovi", "Urađeni kvizovi", "Budući kvizovi", "Prošli kvizovi")
        filterKvizova.adapter = ArrayAdapter<String>(view.context, android.R.layout.simple_list_item_1, filteri)

        filterKvizova.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //svi
                posljednjeStanje = filteri.get(position)
                if(filteri.get(position) == "Svi kvizovi")
                kvizListaViewModel.getSviKvizovi(
                        onSuccess = ::onSuccess,
                        onError = ::onError
                )
                    //SVI OVI STO RADE UPDATE MORAS POPRAVITI
                //svi moji
                else if(filteri.get(position) == "Svi moji kvizovi") {
                    kvizListaViewModel.getMojiKvizovi(
                            onSuccess = ::onSuccess,
                            onError = ::onError
                    )
                    //sviKvizoviAdapter.updateTest(kvizListaViewModel.getMojiKvizovi())
                }
                //svi moji uradjeni
                else if(filteri.get(position) == "Urađeni kvizovi")
                    kvizListaViewModel.getMojiUradjeni(
                            onSuccess = ::onSuccess,
                            onError = ::onError
                    )
                    //sviKvizoviAdapter.updateTest(kvizListaViewModel.getMojiUradjeni())
                //svi moji buduci
                else if(filteri.get(position) == "Budući kvizovi")
                    kvizListaViewModel.getMojiBuduci(
                            onSuccess = ::onSuccess,
                            onError = ::onError
                    )
                    //sviKvizoviAdapter.updateTest(kvizListaViewModel.getMojiBuduci())
                //svi moji prosli
                else if(filteri.get(position) == "Prošli kvizovi")
                    kvizListaViewModel.getMojiProsli(
                            onSuccess = ::onSuccess,
                            onError = ::onError
                    )
                    //sviKvizoviAdapter.updateTest(kvizListaViewModel.getMojiProsli())
            }
        }

        return view;
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(activity as MainActivity)[SharedViewModel::class.java]

    }

    //otvori novi fragmnet na klik
    private fun otvoriFragmentPokusaj(kviz: Kviz) {
        viewModel.setKviz(kviz)
        for(mk in sviMojiKvizovi) {
            if(mk.id == kviz.id) {
                kvizNovi = kviz
                kvizTakenViewModel.zapocniKviz(kvizNovi.id,
                        onSuccess = ::onSuccessPokusaj,
                        onError = ::onError)
                break
            }
        }
    }

    companion object {
        fun newInstance(): FragmentKvizovi = FragmentKvizovi()
    }


}