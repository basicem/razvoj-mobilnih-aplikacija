package ba.etf.rma21.projekat.viewmodel

import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.KvizTaken
import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.data.repositories.KvizRepository
import ba.etf.rma21.projekat.data.repositories.PredmetIGrupaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class KvizListaViewModel {

    val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun getSviKvizovi(onSuccess: (grupa: List<Kviz>) -> Unit, onError: () -> Unit) {
        scope.launch{
            // Make the network call and suspend execution until it finishes
            val result = KvizRepository.getAll();
            println("Ovdje smo dobili sve grupe " + result)
            for(kviz in result!!) {
                kviz.nazivPredmeta = " "
            }
            // Display result of the network request to the user
            //ovdje cemo za svaki kviz dobiti predmet nekako
            var sviPredmeti = PredmetIGrupaRepository.getPredmeti()
            var sveGrupe = PredmetIGrupaRepository.getGrupe()
            for(grupa in sveGrupe!!) {
                val sviKvizoviZaGrupu = KvizRepository.getKvizoveZaGrupu(grupa.id);
                println("Svi kvizovi za grupu neku su " + sviKvizoviZaGrupu)
                for(predmet in sviPredmeti!!) {
                    val sveGrupeZaPredmet = PredmetIGrupaRepository.getGrupeZaPredmet(predmet.id.toInt())
                    var index = sveGrupeZaPredmet!!.indexOf(grupa)
                    if(index >= 0) {
                        var taGrupa = sveGrupeZaPredmet[index]
                        println("Trebamo dodati predmet " + predmet)
                        for(kviz in result!!) {
                            for(kviz2 in sviKvizoviZaGrupu!!) {
                                if(kviz.id == kviz2.id)
                                    kviz.nazivPredmeta = (kviz.nazivPredmeta + " " + predmet.naziv)
                            }
                        }
                    }
                }
            }

            when (result) {
                is List<Kviz> -> onSuccess?.invoke(result)
                else-> onError?.invoke()
            }
        }
    }


    fun getMojiKvizovi(onSuccess: (kviz: List<Kviz>) -> Unit, onError: () -> Unit) {
        scope.launch{
            // Make the network call and suspend execution until it finishes
            val result = KvizRepository.getMyKvizes();
            println("Ovdje smo dobili sve grupe " + result)
            for(kviz in result!!) {
                kviz.nazivPredmeta = " "
            }
            // Display result of the network request to the user
            //ovdje cemo za svaki kviz dobiti predmet nekako
            var sviPredmeti = PredmetIGrupaRepository.getPredmeti()
            var sveGrupe = PredmetIGrupaRepository.getGrupe()
            for(grupa in sveGrupe!!) {
                val sviKvizoviZaGrupu = KvizRepository.getKvizoveZaGrupu(grupa.id);
                println("Svi kvizovi za grupu neku su " + sviKvizoviZaGrupu)
                for(predmet in sviPredmeti!!) {
                    val sveGrupeZaPredmet = PredmetIGrupaRepository.getGrupeZaPredmet(predmet.id.toInt())
                    var index = sveGrupeZaPredmet!!.indexOf(grupa)
                    if(index >= 0) {
                        var taGrupa = sveGrupeZaPredmet[index]
                        println("Trebamo dodati predmet " + predmet)
                        for(kviz in result!!) {
                            for(kviz2 in sviKvizoviZaGrupu!!) {
                                if(kviz.id == kviz2.id)
                                    kviz.nazivPredmeta = (kviz.nazivPredmeta + " " + predmet.naziv)
                            }
                        }
                    }
                }
            }

            when (result) {
                is List<Kviz> -> onSuccess?.invoke(result)
                else-> onError?.invoke()
            }
        }
        //return getMyKvizes()
    }
    fun getMojiUradjeni(onSuccess: (kvizovi: List<Kviz>) -> Unit, onError: () -> Unit)  {
        scope.launch{
            // Make the network call and suspend execution until it finishes
            val result = KvizRepository.getDovrseni()
            for(kviz in result!!) {
                kviz.nazivPredmeta = " "
            }
            // Display result of the network request to the user
            //ovdje cemo za svaki kviz dobiti predmet nekako
            var sviPredmeti = PredmetIGrupaRepository.getPredmeti()
            var sveGrupe = PredmetIGrupaRepository.getGrupe()
            for(grupa in sveGrupe!!) {
                val sviKvizoviZaGrupu = KvizRepository.getKvizoveZaGrupu(grupa.id);
                println("Svi kvizovi za grupu neku su " + sviKvizoviZaGrupu)
                for(predmet in sviPredmeti!!) {
                    val sveGrupeZaPredmet = PredmetIGrupaRepository.getGrupeZaPredmet(predmet.id.toInt())
                    var index = sveGrupeZaPredmet!!.indexOf(grupa)
                    if(index >= 0) {
                        var taGrupa = sveGrupeZaPredmet[index]
                        println("Trebamo dodati predmet " + predmet)
                        for(kviz in result!!) {
                            for(kviz2 in sviKvizoviZaGrupu!!) {
                                if(kviz.id == kviz2.id)
                                    kviz.nazivPredmeta = (kviz.nazivPredmeta + " " + predmet.naziv)
                            }
                        }
                    }
                }
            }
            when (result) {
                is List<Kviz> -> onSuccess?.invoke(result)
                else-> onError?.invoke()
            }
        }

    }
    fun getMojiBuduci(onSuccess: (grupa: List<Kviz>) -> Unit, onError: () -> Unit)  {
        scope.launch{
            // Make the network call and suspend execution until it finishes
            val result = KvizRepository.getBuduci()
            for(kviz in result!!) {
                kviz.nazivPredmeta = " "
            }
            // Display result of the network request to the user
            //ovdje cemo za svaki kviz dobiti predmet nekako
            var sviPredmeti = PredmetIGrupaRepository.getPredmeti()
            var sveGrupe = PredmetIGrupaRepository.getGrupe()
            for(grupa in sveGrupe!!) {
                val sviKvizoviZaGrupu = KvizRepository.getKvizoveZaGrupu(grupa.id);
                println("Svi kvizovi za grupu neku su " + sviKvizoviZaGrupu)
                for(predmet in sviPredmeti!!) {
                    val sveGrupeZaPredmet = PredmetIGrupaRepository.getGrupeZaPredmet(predmet.id.toInt())
                    var index = sveGrupeZaPredmet!!.indexOf(grupa)
                    if(index >= 0) {
                        var taGrupa = sveGrupeZaPredmet[index]
                        println("Trebamo dodati predmet " + predmet)
                        for(kviz in result!!) {
                            for(kviz2 in sviKvizoviZaGrupu!!) {
                                if(kviz.id == kviz2.id)
                                    kviz.nazivPredmeta = (kviz.nazivPredmeta + " " + predmet.naziv)
                            }
                        }
                    }
                }
            }

            when (result) {
                is List<Kviz> -> onSuccess?.invoke(result)
                else-> onError?.invoke()
            }
        }
    }
    fun getMojiProsli(onSuccess: (grupa: List<Kviz>) -> Unit, onError: () -> Unit)  {
        scope.launch {
            // Make the network call and suspend execution until it finishes
            val result = KvizRepository.getProsli()
            for(kviz in result!!) {
                kviz.nazivPredmeta = " "
            }
            // Display result of the network request to the user
            //ovdje cemo za svaki kviz dobiti predmet nekako
            var sviPredmeti = PredmetIGrupaRepository.getPredmeti()
            var sveGrupe = PredmetIGrupaRepository.getGrupe()
            for(grupa in sveGrupe!!) {
                val sviKvizoviZaGrupu = KvizRepository.getKvizoveZaGrupu(grupa.id);
                println("Svi kvizovi za grupu neku su " + sviKvizoviZaGrupu)
                for(predmet in sviPredmeti!!) {
                    val sveGrupeZaPredmet = PredmetIGrupaRepository.getGrupeZaPredmet(predmet.id.toInt())
                    var index = sveGrupeZaPredmet!!.indexOf(grupa)
                    if(index >= 0) {
                        var taGrupa = sveGrupeZaPredmet[index]
                        println("Trebamo dodati predmet " + predmet)
                        for(kviz in result!!) {
                            for(kviz2 in sviKvizoviZaGrupu!!) {
                                if(kviz.id == kviz2.id)
                                    kviz.nazivPredmeta = (kviz.nazivPredmeta + " " + predmet.naziv)
                            }
                        }
                    }
                }
            }

            when (result) {
                is List<Kviz> -> onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }
    }
}