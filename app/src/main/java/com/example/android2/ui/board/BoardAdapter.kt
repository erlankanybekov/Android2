package com.example.android2.ui.board

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android2.databinding.ItemNewsRvBinding
import com.example.android2.databinding.PagerBoardBinding
import com.example.android2.models.News
import com.example.android2.ui.news.NewsAdapter
import java.text.SimpleDateFormat
import java.util.*





class BoardAdapter (private val onClickStart:()->Unit) :RecyclerView.Adapter<BoardAdapter.ViewHolder>() {

    private val titles = arrayListOf("Leo Messi","Cristiano Ronaldo"," Paulo Dybala ")
    private val pictures = arrayListOf<Int>()


     inner class ViewHolder(private  var binding: PagerBoardBinding):RecyclerView.ViewHolder(binding.root) {
         fun bind(position: Int) {
             binding.textTitle.text = titles[position]

             if (position == titles.size-1){
                 binding.btnStart.visibility = View.VISIBLE
             }else{
                 binding.btnStart.visibility = View.VISIBLE
             }

             binding.btnStart.setOnClickListener {
                 onClickStart()
             }


         }

     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(PagerBoardBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount()=titles.size
    }






