package ba.etf.rma21.projekat

import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.KvizTaken
import ba.etf.rma21.projekat.data.models.Odgovor
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.repositories.AccountRepository
import ba.etf.rma21.projekat.data.repositories.DBRepository
//import ba.etf.rma21.projekat.data.repositories.KorisnikRepository.Companion.dodajKviz
import ba.etf.rma21.projekat.data.repositories.PitanjeKvizRepository.Companion.getPitanja
import ba.etf.rma21.projekat.view.FragmentKvizovi
import ba.etf.rma21.projekat.view.FragmentPoruka
//import ba.etf.rma21.projekat.view.FragmentKvizovi
//import ba.etf.rma21.projekat.view.FragmentPoruka
//import ba.etf.rma21.projekat.view.FragmentKvizovi
//import ba.etf.rma21.projekat.view.FragmentPoruka
import ba.etf.rma21.projekat.view.FragmentPredmeti
import ba.etf.rma21.projekat.viewmodel.*
//import ba.etf.rma21.projekat.viewmodel.SharedViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.time.LocalDate
import java.util.*


class MainActivity : AppCompatActivity(), FragmentPredmeti.OnDataPass{
    lateinit var bottomNavigation: BottomNavigationView
    private val filter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
    lateinit var viewModel: SharedViewModel
    lateinit var trenutniKviz: Kviz
    lateinit var trenutniPokusaj : KvizTaken
    var odgovorViewModel: OdgovorViewModel = OdgovorViewModel()
    var pitanjeKvizViewModel: PitanjeKvizViewModel = PitanjeKvizViewModel()
    var kvizTakenViewModel: KvizTakenViewModel = KvizTakenViewModel()
    lateinit var listaPitanja: List<Pitanje>
    var accountViewModel: AccountViewModel = AccountViewModel()

    var godina:String = "Godina"



    override fun onDataPass(data: String) {
        godina = data;
        println("Sada je u mainu " + godina)
    }

    fun onSuccessPitanja(pitanja: List<Pitanje>) {
        listaPitanja = pitanja;
    }
    fun odovoriNaOstala(odgovori: List<Odgovor>) {

        for(pitanje in listaPitanja) {
            var imaOdgovor: Boolean = false
            for(odgovor in odgovori) {
                if(odgovor.pitanjeId == pitanje.id) {
                    imaOdgovor = true;
                    break;
                }
            }
//            if(imaOdgovor == false) {
//                odgovorViewModel.dodajOdgovor(trenutniPokusaj.id, pitanje.id, pitanje.opcije.size,
//                        onSuccess = ::onSuccess,
//                        onError = ::onError)
//            }
        }
    }
    fun onSuccess(bodovi:Int) {
        val toast = Toast.makeText(this, "OK!", Toast.LENGTH_SHORT)
        toast.show()
    }

    fun onSuccessPoruka(odgovori: List<Odgovor>) {

        if(odgovori.size != listaPitanja.size) {
            odovoriNaOstala(odgovori);
        }


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

        val porukaFragment = FragmentPoruka.newInstance()
        val args = Bundle()
        val upis: String = "Završili ste kviz " + trenutniKviz.naziv + " sa tačnosti " + ukupniBodoviKviza
        args.putString("upis", upis)
        porukaFragment.arguments = args
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, porukaFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun onError() {
        val toast = Toast.makeText(this, "Greška!", Toast.LENGTH_SHORT)
        toast.show()
    }
    fun onSuccess() {
        val toast = Toast.makeText(this, "OK!", Toast.LENGTH_SHORT)
        toast.show()
    }

    fun onSuccessPokusaji(pokusaji: List<KvizTaken>) {
        for(pokusaj in pokusaji) {
            if(pokusaj.KvizId == trenutniKviz.id) {
                trenutniPokusaj = pokusaj
                break
            }
        }
    }

    //Listener za click
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.predmeti -> {
                val predmetFragment = FragmentPredmeti.newInstance()
                openFragment(predmetFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.kvizovi -> {
                val kvizFragment = FragmentKvizovi.newInstance()
                openFragment(kvizFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.zaustaviKviz -> {
                val kvizFragment = FragmentKvizovi.newInstance()
                openFragment(kvizFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.predajKviz -> {
                obracunajKviz()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val uri = intent
        if (uri != null) {
            val hash = uri.getStringExtra("payload")
            if (hash != null) {
                this?.let { accountViewModel.postaviHash(applicationContext, hash, onSuccess = ::onSuccess, onError = ::onError)}
                val toast = Toast.makeText(this, "Trenutni hash korisnika: " + hash, Toast.LENGTH_SHORT)
                toast.show()
            }
            else {
                this?.let { accountViewModel.postaviHash(applicationContext, AccountRepository.acHash, onSuccess = ::onSuccess, onError = ::onError)}
                val toast = Toast.makeText(this, "Trenutni hash korisnika: " + AccountRepository.acHash, Toast.LENGTH_SHORT)
                toast.show()

            }

        }
        else {
            this?.let { accountViewModel.postaviHash(applicationContext, AccountRepository.acHash, onSuccess = ::onSuccess, onError = ::onError)}
            val toast = Toast.makeText(this, "Trenutni hash korisnika: " + AccountRepository.acHash, Toast.LENGTH_SHORT)
            toast.show()
        }


        viewModel = ViewModelProvider(this)[SharedViewModel::class.java]
        viewModel.getKviz().observe(this, Observer { poruka ->
            trenutniKviz = poruka
            pitanjeKvizViewModel.getPitanjaZaKviz(trenutniKviz,
                    onSuccess = ::onSuccessPitanja,
                    onError = ::onError)

             kvizTakenViewModel.getPocetiKvizovi(
                    onSuccess = ::onSuccessPokusaji,
                    onError = ::onError)
        })

        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        bottomNavigation= findViewById(R.id.bottomNav)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        //Defaultni fragment
        bottomNavigation.selectedItemId= R.id.kvizovi
        val kvizovi = FragmentKvizovi.newInstance()
        openFragment(kvizovi)
    }
    fun obracunajKviz() {
        bottomNavigation.menu.findItem(R.id.kvizovi).isVisible = true
        bottomNavigation.menu.findItem(R.id.predmeti).isVisible = true
        bottomNavigation.menu.findItem(R.id.predajKviz).isVisible = false
        bottomNavigation.menu.findItem(R.id.zaustaviKviz).isVisible = false

        odgovorViewModel.predajOdgovore(trenutniKviz.id,
                onSuccess = ::onSuccessPoruka,
                onError = ::onError)

    }

    //Funkcija za izmjenu fragmenta
    private fun openFragment(fragment: Fragment) {
        bottomNavigation.menu.findItem(R.id.kvizovi).isVisible = true
        bottomNavigation.menu.findItem(R.id.predmeti).isVisible = true
        bottomNavigation.menu.findItem(R.id.predajKviz).isVisible = false
        bottomNavigation.menu.findItem(R.id.zaustaviKviz).isVisible = false
        val args = Bundle()
        args.putString("godina",  godina)
        fragment.arguments = args
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    override fun onResume() {
        super.onResume()
    }
    override fun onPause() {
        super.onPause()
    }

    override fun onBackPressed() {
        if(bottomNavigation.selectedItemId == R.id.predmeti) {
            bottomNavigation.selectedItemId = R.id.kvizovi
        }
        else{
            //ne radi nista
        }
    }
}

