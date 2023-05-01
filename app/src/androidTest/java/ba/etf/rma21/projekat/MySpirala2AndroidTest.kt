//package ba.etf.rma21.projekat
//
//import androidx.recyclerview.widget.RecyclerView
//import androidx.test.espresso.Espresso
//import androidx.test.espresso.Espresso.onData
//import androidx.test.espresso.Espresso.onView
//import androidx.test.espresso.action.ViewActions
//import androidx.test.espresso.action.ViewActions.click
//import androidx.test.espresso.assertion.ViewAssertions.matches
//import androidx.test.espresso.contrib.NavigationViewActions
//import androidx.test.espresso.contrib.RecyclerViewActions
//import androidx.test.espresso.intent.rule.IntentsTestRule
//import androidx.test.espresso.matcher.ViewMatchers
//import androidx.test.espresso.matcher.ViewMatchers.*
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import ba.etf.rma21.projekat.data.repositories.GrupaRepository
//import ba.etf.rma21.projekat.data.repositories.KvizRepository
//import ba.etf.rma21.projekat.data.repositories.PitanjeKvizRepository
//import ba.etf.rma21.projekat.data.repositories.PredmetRepository
//import org.hamcrest.CoreMatchers
//import org.hamcrest.CoreMatchers.*
//import org.hamcrest.CoreMatchers.`is` as Is
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//
//@RunWith(AndroidJUnit4::class)
//class MySpirala2AndroidTest {
//    @get:Rule
//    val intentsTestRule = IntentsTestRule<MainActivity>(MainActivity::class.java)
//
//    @Test
//    fun testUpis() {
//        Espresso.onView(ViewMatchers.withId(R.id.predmeti)).perform(ViewActions.click())
//        onView(withId(R.id.odabirGodina)).perform(click())
//        onData(allOf(Is(instanceOf(String::class.java)), Is("1"))).perform(click())
//
//        var predmetiZagodinu = PredmetRepository.getPredmetsByGodina(1)
//        onView(withId(R.id.odabirPredmet)).perform(click())
//        onData(allOf(Is(instanceOf(String::class.java)), Is(predmetiZagodinu[0].naziv))).perform(click())
//
//        var grupeZaPredmet = GrupaRepository.getGroupsByPredmet(predmetiZagodinu[0].naziv)
//        onView(withId(R.id.odabirGrupa)).perform(click())
//        onData(allOf(Is(instanceOf(String::class.java)), Is(grupeZaPredmet[0].naziv))).perform(click())
//
//        Espresso.onView(ViewMatchers.withId(R.id.dodajPredmetDugme)).perform(ViewActions.click())
//        //upisali smo se u predmet
//        onView(withSubstring("Uspješno ste upisani u grupu " + grupeZaPredmet[0].naziv + " predmeta " + predmetiZagodinu[0].naziv + " !")).check(matches(isDisplayed()))
//
//        //vrati se na kvizove
//        Espresso.onView(ViewMatchers.withId(R.id.kvizovi)).perform(ViewActions.click())
//        Espresso.onView(ViewMatchers.withId(R.id.predmeti)).perform(ViewActions.click())
//        onView(withId(R.id.odabirGodina)).check(matches(withSpinnerText(containsString("1"))));
//    }
//    @Test
//    fun testPredajKviz() {
//        onView(withId(R.id.filterKvizova)).perform(click())
//        Espresso.onData(CoreMatchers.allOf(CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)), CoreMatchers.`is`("Svi moji kvizovi"))).perform(click())
//        val kvizovi = KvizRepository.getMyKvizes()
//        onView(withId(R.id.listaKvizova)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(CoreMatchers.allOf(hasDescendant(withText(kvizovi[1].naziv)),
//                hasDescendant(withText(kvizovi[1].nazivPredmeta))), click()))
//        onView(withId(R.id.navigacijaPitanja)).check(matches(isDisplayed()))
//        onView(withId(R.id.navigacijaPitanja)).check(matches(isDisplayed()))
//        val pitanja = PitanjeKvizRepository.getPitanja(kvizovi[1].naziv, kvizovi[1].nazivPredmeta)
//        var indeks = 0
//
//        for (pitanje in pitanja) {
//            onView(withId(R.id.navigacijaPitanja)).perform(NavigationViewActions.navigateTo(indeks))
//            onData(allOf(Is(instanceOf(String::class.java)), Is("Tacan"))).perform(click())
//            indeks++
//        }
//        onView(withId(R.id.navigacijaPitanja)).check(matches(isDisplayed()))
//        onView(withId(R.id.predajKviz)).perform(click())
//        onView(withSubstring("Završili ste kviz " + kvizovi[1].naziv + " sa tačnosti 1.0")).check(matches(isDisplayed()))
//
//    }
//}