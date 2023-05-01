package ba.etf.rma21.projekat.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.data.repositories.AccountRepository
import ba.etf.rma21.projekat.data.repositories.DBRepository
import ba.etf.rma21.projekat.viewmodel.GrupaListaViewModel
import ba.etf.rma21.projekat.viewmodel.KorisnikViewModel
import ba.etf.rma21.projekat.viewmodel.PredmetListaViewModel
import java.lang.Integer.parseInt

class FragmentPredmeti : Fragment(){
    private lateinit var  odabirGodina: Spinner
    private lateinit var  odabirPredmet: Spinner
    private lateinit var  odabirGrupa: Spinner
    private var predmetListaViewModel = PredmetListaViewModel()
    private lateinit var dodajPredmetDugme: Button
    private var grupaListaViewModel = GrupaListaViewModel()
    private var grupeSaGodine : List<Grupa> = listOf()

    var sviPredmeti: List<Predmet> = listOf()
    var sveGrupe: List<Grupa> = listOf()
    var sveGrupeStudenta: List<Grupa> = listOf()

    lateinit var dataPasser: OnDataPass

    interface OnDataPass {
        fun onDataPass(data: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataPasser = context as OnDataPass
    }

    fun passData(data: String){
        dataPasser.onDataPass(data)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    fun onSuccessPredmeti(predmeti:List<Predmet>){
        val toast = Toast.makeText(context, "Pronađeni svi predmeti", Toast.LENGTH_SHORT)
        toast.show()
        sviPredmeti = predmeti as MutableList<Predmet>;
        var mojipredmeti: List<String> = listOf("Predmet")
        //filter predmeta po godini
        sviPredmeti = sviPredmeti.filter { predmet -> predmet.godina == parseInt(odabirGodina.selectedItem.toString()) }
        //trebamo staviti samo predmete na koje nije upisan student
        //samo naziv predmeta
        mojipredmeti = mojipredmeti + sviPredmeti.map { predmet -> predmet.naziv };
        odabirPredmet.adapter = ArrayAdapter<String>(odabirGodina.context, android.R.layout.simple_list_item_1, mojipredmeti)
        odabirPredmet.setSelection(mojipredmeti.indexOf("Predmet"))
    }
    fun onError() {
        val toast = Toast.makeText(context, "Greška!", Toast.LENGTH_SHORT)
        toast.show()
    }
    fun onSuccessGrupeSaGodine(grupe:List<Grupa>) {
        grupeSaGodine = grupeSaGodine + grupe;
    }

    fun onSuccessGrupe(grupe:List<Grupa>){
        val toast = Toast.makeText(context, "Pronađene sve grupe", Toast.LENGTH_SHORT)
        toast.show()
        sveGrupe = grupe as MutableList<Grupa>
        var mojeGrupe: List<String> = listOf("Grupa")
        mojeGrupe =  mojeGrupe + sveGrupe.map { grupa -> grupa.naziv };
        odabirGrupa.adapter = ArrayAdapter<String>(odabirPredmet.context, android.R.layout.simple_list_item_1, mojeGrupe)
        odabirGrupa.setSelection(mojeGrupe.indexOf("Grupa"))
    }
    fun onSuccessUpis(poruka: String) {
        val toast = Toast.makeText(context, "Dodan u grupu", Toast.LENGTH_SHORT)
        toast.show()
    }
    fun onSuccessStudentGrupe(grupe: List<Grupa>) {
        val toast = Toast.makeText(context, "Sve moje grupe", Toast.LENGTH_SHORT)
        toast.show()
        sveGrupeStudenta = grupe as MutableList<Grupa>
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.upis_na_predmet_fragment, container, false)

        val godina_novi:String = arguments?.getString("godina") as String
        println("Godina_novi je " + godina_novi)

        //dugme
        dodajPredmetDugme = view.findViewById(R.id.dodajPredmetDugme)
        dodajPredmetDugme.setOnClickListener{ upisiKorisnika(odabirGrupa.selectedItem.toString(), odabirPredmet.selectedItem.toString(), odabirGodina.selectedItem.toString()) }

        odabirGodina = view.findViewById(R.id.odabirGodina)
        val godine = arrayOf("Godina", "1", "2", "3", "4", "5")
        odabirGodina.adapter = ArrayAdapter<String>(view.context, android.R.layout.simple_list_item_1, godine)
        odabirGodina.setSelection( godine.indexOf(godina_novi))
        //predmet
        odabirPredmet = view.findViewById(R.id.odabirPredmet)
        odabirPredmet.isEnabled = false
        //grupa
        odabirGrupa = view.findViewById(R.id.odabirGrupa)
        odabirGrupa.isEnabled = false
        dodajPredmetDugme.isEnabled = false


        odabirGodina.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                if (odabirGodina.selectedItem.toString() != "Godina") {
                    odabirPredmet.isEnabled = true
                    println("Prikupljeni predmeti su " + sviPredmeti)
                    predmetListaViewModel.getSveMojePredmeteNaKojeNisamUpisan(onSuccess = ::onSuccessPredmeti, onError = ::onError)
                } else {
                    odabirPredmet.adapter = null
                    odabirPredmet.isEnabled = false
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                //nista
            }
        }



        odabirPredmet.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                if(odabirPredmet.selectedItem.toString() != "Predmet") {
                    odabirGrupa.isEnabled = true
                    println("Prikupljene grupe  su " + sveGrupe)
                    var trazeniPredmet: Predmet = sviPredmeti[0]
                    for(predmet in sviPredmeti) {

                        if(predmet.naziv == odabirPredmet.selectedItem.toString()) {
                            trazeniPredmet = predmet
                            break;
                        }
                    }
                    grupaListaViewModel.getGrupeZaPredmet(
                            trazeniPredmet.id.toInt(),
                            onSuccess = ::onSuccessGrupe,
                            onError = ::onError)
                }
                else {
                    odabirGrupa.adapter = null
                    odabirGrupa.isEnabled = false
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //nista
            }
        }
        odabirGrupa.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                dodajPredmetDugme.isEnabled = odabirGrupa.selectedItem.toString() != "Grupa"
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //nista
            }

        }

        return view
    }

    companion object {
        fun newInstance(): FragmentPredmeti = FragmentPredmeti()
    }

    private fun upisiKorisnika(grupa: String, predmet: String, godina: String) {

        var trazenaGrupa: Grupa = sveGrupe[0]
        for(grupa in sveGrupe) {
            if(grupa.naziv == odabirGrupa.selectedItem.toString()) {
                println("Poklapa se " + grupa.naziv + " + " + odabirGrupa.selectedItem.toString())
                trazenaGrupa = grupa
            }
        }
        grupaListaViewModel.upisiMeNaKviz(trazenaGrupa.id,
                onSuccess = ::onSuccessUpis,
                onError = ::onError)


        passData(godina)
        val porukaFragment = FragmentPoruka.newInstance()
        val args = Bundle()
        val upis:String = "Uspješno ste upisani u grupu " + grupa + " predmeta " + predmet + " !"
        args.putString("upis",  upis)
        porukaFragment.arguments = args
        val f = fragmentManager?.beginTransaction();
        if (f != null) {
            f.replace(R.id.container, porukaFragment)
            f.commit()
            f.addToBackStack(null)
        }
    }
}