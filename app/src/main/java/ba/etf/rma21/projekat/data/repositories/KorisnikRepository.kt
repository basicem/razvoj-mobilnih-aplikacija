//package ba.etf.rma21.projekat.data.repositories
//
//import ba.etf.rma21.projekat.data.models.Grupa
//import ba.etf.rma21.projekat.data.models.Korisnik
//import ba.etf.rma21.projekat.data.models.Kviz
//import ba.etf.rma21.projekat.data.models.Predmet
//
//class KorisnikRepository {
//
//    companion object {
//        fun dodajPredmet(ime: String, godina: Int) {
//            Korisnik.listaPredmet.add(Predmet(ime, godina))
//        }
//
//        fun dodajGrupu(grupa: String, predmet: String) {
//            Korisnik.listaGrupa.add(Grupa(grupa, predmet))
//        }
//
//        fun dajMojePredmete(): List<Predmet> {
//            return Korisnik.listaPredmet
//        }
//
//        fun dajMojeGrupe(): List<Grupa> {
//            return Korisnik.listaGrupa
//        }
//
//        fun dodajKviz(kviz: Kviz, odgovori: MutableList<Triple<String, String, Int>>) {
//            Korisnik.mojiKvizovi.put(kviz, odgovori)
//        }
//
//        fun dajMojeKvizove() : MutableMap<Kviz,  MutableList<Triple<String, String, Int>>> {
//            return Korisnik.mojiKvizovi
//        }
//    }
//}