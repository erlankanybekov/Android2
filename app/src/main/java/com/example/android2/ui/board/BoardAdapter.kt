package com.example.android2.ui.board

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android2.R
import com.example.android2.databinding.PagerBoardBinding


class BoardAdapter(private val onClickStart:()->Unit,):RecyclerView.Adapter<BoardAdapter.ViewHolder>() {


    private val titles = arrayListOf("Leo Messi","Cristiano Ronaldo"," Paulo Dybala ")
    private val description = arrayListOf("Argentina : PSG  ","Portugal : Man Utd"," Argentina : Juventus ")

    private var imageList  =  arrayListOf(R.drawable.messi,R.drawable.cristiano,R.drawable.dybala)



     inner class ViewHolder(private  var binding: PagerBoardBinding):RecyclerView.ViewHolder(binding.root) {
         fun bind(position: Int) {
             binding.textTitle.text = titles[position]
            binding.imageView.setImageResource(imageList[position])
             binding.textDesc.text = description[position]


             if (position == titles.size-1){
                 binding.btnStart.visibility = View.VISIBLE
             }else{
                 binding.btnStart.visibility = View.INVISIBLE
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
    fun getItemId(it: View?) {
}


}








