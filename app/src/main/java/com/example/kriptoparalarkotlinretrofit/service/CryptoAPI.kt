package com.example.kriptoparalarkotlinretrofit.service


import com.example.kriptoparalarkotlinretrofit.model.CryptoModel
import retrofit2.Response
import retrofit2.http.GET


interface CryptoAPI {

     @GET("currencies/ticker?key=8211a604e46b8983354e5f8cd9be1056")
    suspend fun getData(): Response<List<CryptoModel>> //Coroutine
   // fun getData(): Observable<List<CryptoModel>>

  //  fun getData(): Call<List<CryptoModel>> //Notmal

}