//package ba.etf.rma21.projekat
//
//import ba.etf.rma21.projekat.data.models.Grupa
//import ba.etf.rma21.projekat.data.models.Predmet
//import ba.etf.rma21.projekat.data.repositories.GrupaRepository
//import ba.etf.rma21.projekat.data.repositories.PredmetRepository
//import junit.framework.Assert.assertEquals
//import org.hamcrest.Matchers.*
//import org.junit.Assert.assertThat
//import org.junit.Test
//import org.hamcrest.CoreMatchers.`is` as Is
//
//class PredmetGrupaRepositoryUnitTest {
//    //Napisati unit testove za klasu/e kojim dohvatate listu predmeta po godinama, grupa po predmetima
//    @Test
//    fun testSviPredmeti(){
//        val sviPredmeti = PredmetRepository.getAll()
//        assertEquals(sviPredmeti.size, 10)
//        assertThat(sviPredmeti, hasItem<Predmet>(hasProperty("naziv", Is("DM"))))
//        assertThat(sviPredmeti, hasItem<Predmet>(hasProperty("naziv", Is("RPR"))))
//        assertThat(sviPredmeti, hasItem<Predmet>(hasProperty("naziv", Is("WT"))))
//        assertThat(sviPredmeti, not(hasItem<Predmet>(hasProperty("naziv", Is("Nema ovog imena")))))
//    }
//
//    @Test
//    fun testMojiPredmeti(){
//        val sviPredmeti = PredmetRepository.getUpisani()
//        assertEquals(1, sviPredmeti.size)
//        assertThat(sviPredmeti, hasItem<Predmet>(hasProperty("naziv", Is("WT"))))
//        assertThat(sviPredmeti, not(hasItem<Predmet>(hasProperty("naziv", Is("OE")))))
//    }
//
//    @Test
//    fun testPredmetiSaGodine(){
//        val sviPredmeti1 = PredmetRepository.getPredmetsByGodina(1)
//        assertEquals(sviPredmeti1.size, 2)
//        assertThat(sviPredmeti1, hasItem<Predmet>(hasProperty("naziv", Is("OE"))))
//        assertThat(sviPredmeti1, hasItem<Predmet>(hasProperty("naziv", Is("IM"))))
//
//        val sviPredmeti2 = PredmetRepository.getPredmetsByGodina(2)
//        assertEquals(sviPredmeti2.size, 2)
//        assertThat(sviPredmeti2, hasItem<Predmet>(hasProperty("naziv", Is("DM"))))
//        assertThat(sviPredmeti2, hasItem<Predmet>(hasProperty("naziv", Is("RPR"))))
//
//        val sviPredmeti3 = PredmetRepository.getPredmetsByGodina(3)
//        assertEquals(sviPredmeti3.size, 2)
//        assertThat(sviPredmeti3, hasItem<Predmet>(hasProperty("naziv", Is("WT"))))
//        assertThat(sviPredmeti3, hasItem<Predmet>(hasProperty("naziv", Is("PJP"))))
//    }
//
//    @Test
//    fun testPredmetiIGrupeSaGodine1(){
//        val sviPredmeti = PredmetRepository.getPredmetsByGodina(1)
//        assertEquals(sviPredmeti.size, 2)
//        assertThat(sviPredmeti, hasItem<Predmet>(hasProperty("naziv", Is("OE"))))
//        assertThat(sviPredmeti, hasItem<Predmet>(hasProperty("naziv", Is("IM"))))
//
//        val grupeZaOE = GrupaRepository.getGroupsByPredmet("OE")
//        assertEquals(grupeZaOE.size, 2)
//        assertThat(grupeZaOE, hasItem<Grupa>(hasProperty("naziv", Is("PON 13:00"))))
//        assertThat(grupeZaOE, hasItem<Grupa>(hasProperty("naziv", Is("SRI 14:30"))))
//        assertThat(grupeZaOE, not(hasItem<Grupa>(hasProperty("naziv", Is("UTO 13:00")))))
//    }
//
//    @Test
//    fun testPredmetiIGrupeSaGodine2(){
//        val sviPredmeti = PredmetRepository.getPredmetsByGodina(5)
//        assertEquals(sviPredmeti.size, 2)
//        assertThat(sviPredmeti, hasItem<Predmet>(hasProperty("naziv", Is("RI"))))
//        assertThat(sviPredmeti, hasItem<Predmet>(hasProperty("naziv", Is("NSI"))))
//
//        val grupaZaNSI = GrupaRepository.getGroupsByPredmet("NSI")
//        assertEquals(grupaZaNSI.size, 2)
//        assertThat(grupaZaNSI, hasItem<Grupa>(hasProperty("naziv", Is("UTO 13:00"))))
//        assertThat(grupaZaNSI, hasItem<Grupa>(hasProperty("naziv", Is("SRI 14:30"))))
//        assertThat(grupaZaNSI, not(hasItem<Grupa>(hasProperty("naziv", Is("UTO 13:30")))))
//    }
//
//
//
//}