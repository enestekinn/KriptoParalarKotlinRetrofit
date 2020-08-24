package com.example.kriptoparalarkotlinretrofit.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kriptoparalarkotlinretrofit.R
import com.example.kriptoparalarkotlinretrofit.adapter.RecyclerViewAdapter
import com.example.kriptoparalarkotlinretrofit.model.CryptoModel
import com.example.kriptoparalarkotlinretrofit.service.CryptoAPI
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.coroutines.*

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(),RecyclerViewAdapter.Listener {

    private val  BASE_URL = "https://api.nomics.com/v1/"
    private var cryptoModels : ArrayList<CryptoModel> ?=null
    private var rvAdapter : RecyclerViewAdapter? =null


    private var job : Job? = null

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Error: ${throwable.localizedMessage}")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // api_key = 8211a604e46b8983354e5f8cd9be1056
        // https://api.nomics.com/v1/currencies/ticker?key=8211a604e46b8983354e5f8cd9be1056


    //    compositeDisposable = CompositeDisposable()


        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        recyclerView.scrollToPosition(0)

        loadData()

        floatingActionButton2.setOnClickListener {
            
            recyclerView.smoothScrollToPosition(0)
        }

    }

 private   fun loadData(){

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CryptoAPI::class.java)



        job = CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.getData()

            withContext(Dispatchers.Main + exceptionHandler) {

                if (response.isSuccessful){

                    response.body()?.let {
                        cryptoModels = ArrayList(it)
                        cryptoModels.let {
                            rvAdapter =RecyclerViewAdapter(it!!,this@MainActivity)
                            recyclerView.adapter=rvAdapter
                        }
                    }
                }
            }
        }







/*
        call.enqueue(object : Callback<List<CryptoModel>> {
            override fun onResponse(call: Call<List<CryptoModel>>, response: Response<List<CryptoModel>>) {

              if (response.isSuccessful) {
                      response.body()?.let {
                          cryptoModels = ArrayList(it)

                          rvAdapter=RecyclerViewAdapter(cryptoModels!!,this@MainActivity)
                          recyclerView.adapter=rvAdapter

                      }
              }
            }

            override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
             t.stackTrace
            }

        })

 */

/*

        compositeDisposable?.add(
            retrofit.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handlerResponse)
        )

 */

    }

    private fun handlerResponse(cryptoList: List<CryptoModel>){
        cryptoModels = ArrayList(cryptoList)

        for (c : CryptoModel in cryptoList){

            println("*************")
            println(c.name)
            println(c.price)
            println(c.logo_url)
            println("*************")

        }


        rvAdapter=RecyclerViewAdapter(cryptoModels!!, this@MainActivity)
        recyclerView.adapter=rvAdapter


    }

    override fun onItemClick(cryptoModel: CryptoModel) {
Toast.makeText(this, "Clicked : ${cryptoModel.name}", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
      //  compositeDisposable?.clear()
        job?.cancel()
    }
}