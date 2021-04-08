package com.example.dollarchecker.network

import com.example.dollarchecker.model.ValCurs
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface CbrApiNew {

    @GET("XML_dynamic.asp?VAL_NM_RQ=R01235")
    fun getMouthData(@Query("date_req1") dateStart: String, @Query("date_req2") dateEnd: String): Call<ValCurs>

    companion object {
        val BASE_URL = "http://www.cbr.ru/scripts/"
        private var instance: CbrApiNew? = null
        fun create(): CbrApiNew {
            if (instance == null)
                instance = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(SimpleXmlConverterFactory.create())
                        .build()
                        .create(CbrApiNew::class.java)

            return instance!!
        }
    }
}