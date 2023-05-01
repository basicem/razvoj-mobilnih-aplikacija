package ba.etf.rma21.projekat.data.repositories

import android.os.Message
import ba.etf.rma21.projekat.data.models.*
import retrofit2.Response
import retrofit2.http.*
import java.util.*

interface Api {

    @GET("/account/{id}/lastUpdate")
    suspend fun getUpdate(@Path("id") id: String, @Query("date") datum:String?): Response<Changed>

    //predmeti i grupe
    @GET("/predmet")
    suspend fun getPredmeti(): Response<List<Predmet>>

    @GET("/predmet/{id}")
    suspend fun getPredmetZaId(@Path("id") id: Int): Response<Predmet>

    @GET("/grupa")
    suspend fun getGrupe(): Response<List<Grupa>>

    @GET("/predmet/{id}/grupa")
    suspend fun getGrupeZaPredmet(@Path("id") id: Int) : Response<List<Grupa>>

    @POST("/grupa/{gid}/student/{id}")
    suspend fun upisiNaGrupu(@Path("gid") gid: Int, @Path("id") id: String): Response<Observable>

    @GET("/student/{id}/grupa")
    suspend fun getUpisaneGrupe(@Path("id") id: String) : Response<List<Grupa>>

    @GET("/student/{id}")
    suspend fun getAccount(@Path("id") hash: String) : Response<Account>

    @DELETE("/student/{id}/upisugrupeipokusaji")
    suspend fun obrisiPodatke(@Path("id") id: Int) : Response<String>

    //kvizovi
    @GET("/kviz")
    suspend fun getAll(): Response<List<Kviz>>

    @GET("/kviz/{id}")
    suspend fun getById(@Path("id") id: Int): Response<Kviz>

    @GET("/grupa/{id}/kvizovi")
    suspend fun getKvizoveZaGrupu(@Path("id") id: Int) : Response<List<Kviz>>

    //pitanja
    @GET("/kviz/{id}/pitanja")
    suspend fun getPitanja(@Path("id") id: Int) : Response<List<Pitanje>>



    //odgovori
    @GET("/student/{id}/kviztaken/{ktid}/odgovori")
    suspend fun getOdgovoreZaPokusaj(@Path("id") id: String, @Path("ktid") kid: Int) : Response<List<Odgovor>>

    @POST("/student/{id}/kviztaken/{ktid}/odgovor")
    suspend fun dodajOdgovorZaKvizIStudenta(@Path("id") id: String, @Path("ktid") kid: Int, @Body podatakOdgovor: PodatakOdgovor) : Response<Message>?

    //kviz taken
    @GET("/student/{id}/kviztaken")
    suspend fun getListuPokusajaZaStudenta(@Path("id") id: String) : Response<List<KvizTaken>>

    @POST("/student/{id}/kviz/{kid}")
    suspend fun dodajPokusajOdgovaranja(@Path("id") id: String, @Path("kid") kid: Int) :Response<KvizTaken?>
}
