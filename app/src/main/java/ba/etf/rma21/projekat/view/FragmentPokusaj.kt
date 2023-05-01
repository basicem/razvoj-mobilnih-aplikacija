package ba.etf.rma21.projekat.view

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.contains
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ba.etf.rma21.projekat.MainActivity
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.Odgovor
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.repositories.KvizRepository
import ba.etf.rma21.projekat.data.repositories.OdgovorRepository
import ba.etf.rma21.projekat.data.repositories.PitanjeKvizRepository
import ba.etf.rma21.projekat.viewmodel.OdgovorViewModel
import ba.etf.rma21.projekat.viewmodel.SharedViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class FragmentPokusaj(lista:List<Pitanje>): Fragment(){
    lateinit var navigacijaPitanja: NavigationView
    var listaPitanja = lista
    lateinit var viewModel: SharedViewModel
    private var  tvPoruka: Int = 0
    private var kvizTrenutni: Int = 0
    lateinit  var trenutniKviz: Kviz
    var odgovorViewModel: OdgovorViewModel = OdgovorViewModel()


    fun onError() {
        val toast = Toast.makeText(context, "Greška!", Toast.LENGTH_SHORT)
        toast.show()
    }

    fun onSuccessPoruka(odgovori: List<Odgovor>) {
        val porukaFragment = FragmentPoruka.newInstance()

        var trenutniBodovi: Double = 0.0
        //dosadasnji bodovi
        var ukupniBodoviKviza: Double = 0.0

        for(odgovor in odgovori) {
            trenutniBodovi  = 0.0
            for(pitanje in listaPitanja!!) {
                if(odgovor.pitanjeId == pitanje.id) {
                    if(pitanje.tacan == odgovor.odgovoreno) {
                        trenutniBodovi = 1.0
                    }
                    ukupniBodoviKviza += (trenutniBodovi.toDouble() / listaPitanja.size) * 100
                    break
                }
            }
        }

        val args = Bundle()
        val upis: String = "Završili ste kviz " + trenutniKviz.naziv + " sa tačnosti " + ukupniBodoviKviza
        args.putString("upis", upis)
        porukaFragment.arguments = args
        val f = fragmentManager?.beginTransaction();
        if (f != null) {
            f.replace(R.id.container, porukaFragment)
            f.commit()
            f.addToBackStack(null)
        }
    }
    fun onSuccess(odgovori: List<Odgovor>) {
        val toast = Toast.makeText(context, "OK!", Toast.LENGTH_SHORT)
        toast.show()
    }

    fun onSuccessOboji(odgovori: List<Odgovor>) {
        val toast = Toast.makeText(context, "Imamo nase odgovore!", Toast.LENGTH_SHORT)
        toast.show()
        lateinit var mOdgovor: Odgovor
        lateinit var mPitanje: Pitanje

        if(odgovori.size == listaPitanja.size && navigacijaPitanja.menu.size() == listaPitanja.size ) {
            navigacijaPitanja.menu.add(0, listaPitanja.size, 0,"Rezultat")
            odgovorViewModel.predajOdgovore(trenutniKviz.id, onSuccess = ::onSuccess,
                    onError = ::onError)
        }

        for(odg in odgovori) {
            for(pitanje in listaPitanja) {
                if (pitanje.id == odg.pitanjeId) {
                    mPitanje = pitanje
                    mOdgovor = odg

                    if(mPitanje.tacan != mOdgovor.odgovoreno) {
                        val spannable = SpannableStringBuilder(navigacijaPitanja.menu.getItem(listaPitanja.indexOf(mPitanje)).title.toString())
                        spannable.setSpan(

                                ForegroundColorSpan(Color.parseColor("#DB4F3D")),
                                0, // start
                                1, // end
                                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                        )
                        navigacijaPitanja.menu.getItem(listaPitanja.indexOf(mPitanje)).title = spannable
                    }
                    else {
                        val spannable = SpannableStringBuilder(navigacijaPitanja.menu.getItem(listaPitanja.indexOf(mPitanje)).title.toString())
                        spannable.setSpan(

                                ForegroundColorSpan(Color.parseColor("#3DDC84")),
                                0, // start
                                1, // end
                                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                        )
                        navigacijaPitanja.menu.getItem(listaPitanja.indexOf(mPitanje)).title = spannable
                    }
                    break;
                }
            }
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.pokusaj_fragment, container, false)

        tvPoruka = arguments?.getInt("kvizTakenID") as Int
        kvizTrenutni = arguments?.getInt("kvizNovi") as Int

        val activity = activity as MainActivity
        var menu = activity.bottomNavigation.menu
        menu.findItem(R.id.kvizovi).isVisible = false
        menu.findItem(R.id.predmeti).isVisible = false
        menu.findItem(R.id.predajKviz).isVisible = true
        menu.findItem(R.id.zaustaviKviz).isVisible = true



        //postavka svih potanja na navigation view
        navigacijaPitanja = view.findViewById(R.id.navigacijaPitanja)
        navigacijaPitanja.menu.clear()
        var i:Int = 0
        for (pitanje in listaPitanja) {
            navigacijaPitanja.menu.add(0, i, 0, (i+1).toString())
            i++;

        }
        //samo ako je zavrsen(zavrsen samo ako su sva pitanja u odgovrima)
        //navigacijaPitanja.menu.add(0, i, 0,"Rezultat")


        //pozicija
        menu = navigacijaPitanja.getMenu()
        val items: MutableList<MenuItem> = ArrayList()
        for (i in 0 until menu.size()) {
            items.add(menu.getItem(i))
        }

        odgovorViewModel.getOdgovoriKviz(kvizTrenutni,
                onSuccess = ::onSuccessOboji,
                onError = ::onError)


        //default prvi
        otvoriPitanje(0)
        navigacijaPitanja.setNavigationItemSelectedListener(object : NavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
                // update highlighted item in the navigation menu
                odgovorViewModel.getOdgovoriKviz(kvizTrenutni,
                        onSuccess = ::onSuccessOboji,
                        onError = ::onError)
                menuItem.setChecked(true)
                var position:Int = items.indexOf(menuItem)
                if(menuItem.title == "Rezultat") {
                    otvoriPoruku()
                }
                else
                    otvoriPitanje(position)
                return true
            }
        })

        return view;
    }
    companion object {
        fun newInstance(novaLisa: List<Pitanje>): FragmentPokusaj = FragmentPokusaj(novaLisa)


    }

    fun obracunajKviz() {
        val activity = activity as MainActivity
        var menu = activity.bottomNavigation.menu
        menu.findItem(R.id.kvizovi).isVisible = true
        menu.findItem(R.id.predmeti).isVisible = true
        menu.findItem(R.id.predajKviz).isVisible = false
        menu.findItem(R.id.zaustaviKviz).isVisible = false


        odgovorViewModel.getOdgovoriKviz(trenutniKviz.id,
                onSuccess = ::onSuccessPoruka,
                onError = ::onError)

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(activity as MainActivity)[SharedViewModel::class.java]
        viewModel.getOdgovor().observe(viewLifecycleOwner, Observer { poruka ->
            obojiPitanje(poruka)
        })
        viewModel.getKviz().observe(viewLifecycleOwner, Observer { poruka ->
            trenutniKviz = poruka
        })
    }

    fun otvoriPoruku() {
        val activity = activity as MainActivity
        var menu = activity.bottomNavigation.menu
        menu.findItem(R.id.kvizovi).isVisible = true
        menu.findItem(R.id.predmeti).isVisible = true
        menu.findItem(R.id.predajKviz).isVisible = false
        menu.findItem(R.id.zaustaviKviz).isVisible = false

        odgovorViewModel.getOdgovoriKviz(kvizTrenutni,
                onSuccess = ::onSuccessPoruka,
                onError = ::onError)

    }

    fun obojiPitanje(poruka: MutableList<Triple<String, String, Int>>) {
        odgovorViewModel.getOdgovoriKviz(kvizTrenutni,
                onSuccess = ::onSuccessOboji,
                onError = ::onError)
    }

    fun otvoriPitanje(pozicija:Int) {
        val bundle = Bundle()
        bundle.putInt("kvizTakenID", tvPoruka)
        bundle.putInt("kvizNovi", kvizTrenutni)
        val pitanjeFragment = FragmentPitanje.newInstance(listaPitanja.get(pozicija))
        pitanjeFragment.arguments = bundle
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.framePitanje, pitanjeFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}
