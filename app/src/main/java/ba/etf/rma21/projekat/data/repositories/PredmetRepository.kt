//package ba.etf.rma21.projekat.data.repositories
//
//
//import ba.etf.rma21.projekat.data.models.Predmet
//import ba.etf.rma21.projekat.data.repositories.KorisnikRepository.Companion.dajMojePredmete
//
//class PredmetRepository {
//    companion object {
//        fun getUpisani(): List<Predmet> {
//            // TODO: Implementirati
//            return dajMojePredmete()
//        }
//
//
//        fun getAll(): List<Predmet> {
//            // TODO: Implementirati
//            return sviPredmeti()
//        }
//
//        //predmeti po godinama
//        fun getPredmetsByGodina(godina:Int) : List<Predmet> {
//            return sviPredmeti().filter { predmet -> predmet.godina == godina}
//        }
//
//        fun getPredmetsByGodinaNaziv(godina: Int): List<String> {
//            return sviPredmeti().filter { predmet -> predmet.godina == godina }.map { predmet -> predmet.naziv }
//        }
//
//        // TODO: Implementirati i ostale potrebne metode
//
//        fun getPredmeteNaKojimNisam(godina: Int): List<String> {
//            val mojiPredmeti:List<String> = dajMojePredmete().filter { predmet -> predmet.godina == godina }.map { predmet -> predmet.naziv }
//            val listaPredmet: List<String> = getPredmetsByGodina(godina).map { predmet -> predmet.naziv }
//            return listaPredmet.minus(mojiPredmeti)
//        }
//    }
//}