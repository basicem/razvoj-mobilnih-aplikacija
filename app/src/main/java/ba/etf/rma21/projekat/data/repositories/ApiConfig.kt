package ba.etf.rma21.projekat.data.repositories

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiConfig {

    companion object{
        var baseURL: String = "https://rma21-etf.herokuapp.com"
        val retrofit : Api = Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api::class.java)

        fun postaviBaseURL(baseUrl:String):Unit{
            baseURL=baseUrl
        }
    }
}