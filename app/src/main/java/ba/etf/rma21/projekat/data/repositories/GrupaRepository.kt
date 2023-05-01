//package ba.etf.rma21.projekat.data.repositories
//
//import ba.etf.rma21.projekat.data.models.Grupa
//
//class GrupaRepository {
//    companion object {
//        init {
//            // TODO: Implementirati
//        }
//
//        fun getGroupsByPredmet(nazivPredmeta: String): List<Grupa> {
//            // TODO: Implementirati
//            return sveGrupe().filter { grupa -> grupa.nazivPredmeta == nazivPredmeta }
//        }
//
//        fun getGrupeZaPredmetNaziv(predmet: String): List<String> {
//            return sveGrupe().filter { grupa -> grupa.nazivPredmeta == predmet }.map { grupa -> grupa.naziv }
//        }
//    }
//}