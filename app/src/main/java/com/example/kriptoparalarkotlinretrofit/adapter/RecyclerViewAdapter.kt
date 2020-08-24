package com.example.kriptoparalarkotlinretrofit.adapter

import android.graphics.Color
import android.graphics.Picture
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kriptoparalarkotlinretrofit.R

import com.example.kriptoparalarkotlinretrofit.model.CryptoModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_layout.view.*

class RecyclerViewAdapter(private val cryptoList : ArrayList<CryptoModel>, private val listener : Listener) : RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>() {

    interface Listener {
        fun onItemClick(cryptoModel: CryptoModel)
    }

    private val colors: Array<String> = arrayOf("#13bd27","#29c1e1","#b129e1","#d3df13","#f6bd0c","#a1fb93","#0d9de3","#ffe48f")

    class RowHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(cryptoModel: CryptoModel, colors: Array<String>, position: Int, listener: Listener) {

            itemView.setOnClickListener {
                listener.onItemClick(cryptoModel)
            }
            itemView.setBackgroundColor(Color.parseColor(colors[position % 8]))
            itemView.text_name.text = cryptoModel.name
            itemView.text_price.text = cryptoModel.price

            if (cryptoModel.logo_url !=null && !cryptoModel.logo_url.isEmpty()){
                Picasso.get().load(cryptoModel.logo_url)
                    .resize(350,200)
                    .into(itemView.imageView)
            }



        }}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout,parent,false)
        return RowHolder(view)
    }

    override fun getItemCount(): Int {

        return cryptoList.size
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
            holder.bind(cryptoList[position],colors,position,listener)
    }


}