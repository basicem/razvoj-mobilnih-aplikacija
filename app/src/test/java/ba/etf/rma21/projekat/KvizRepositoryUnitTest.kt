//package ba.etf.rma21.projekat
//
//import ba.etf.rma21.projekat.data.repositories.KorisnikRepository
//import ba.etf.rma21.projekat.data.repositories.KvizRepository
//import junit.framework.Assert.assertEquals
//import org.junit.Test
//
//class KvizRepositoryUnitTest {
//
//    //Napisati unit testove za klasu/e kojim dohvatate listu kvizova kod filtriranja kvizova
//    @Test
//    fun testFiltriranjeKvizova(){
//        val sviKvizovi = KvizRepository.getAll()
//        assertEquals(sviKvizovi.size, 7)
//    }
//
//    @Test
//    fun testProsliKvizovi() {
//        //prosli kvizovi
//        val prosliKvizovi = KvizRepository.getNotTaken()
//        assertEquals(prosliKvizovi.size, 0)
//    }
//
//
//    @Test
//    fun testMojiKvizovi() {
//        //moji kvizovi
//        val mojiKvizovi = KvizRepository.getMyKvizes()
//        assertEquals(mojiKvizovi.size, 1)
//        val mojKviz = mojiKvizovi.get(0)
//        assertEquals(mojKviz.naziv, "Kviz 1")
//        assertEquals(mojKviz.nazivPredmeta, "WT")
//        assertEquals(mojKviz.osvojeniBodovi, null)
//    }
//
//    @Test
//    fun testBrojPredmetaIGrupaKorisnika(){
//        var brojupisanihPredmeta = KorisnikRepository.dajMojePredmete()
//        assertEquals(brojupisanihPredmeta.size, 1)
//        var brojUpisanihGrupa = KorisnikRepository.dajMojeGrupe()
//        assertEquals(brojUpisanihGrupa.size,1)
//    }
//
//
//}