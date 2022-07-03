package com.example.android2.ui.news

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.android2.databinding.ItemNewsRvBinding

import com.example.android2.models.News
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import java.util.Collections
import android.view.View
import android.view.View.OnLongClickListener
import com.example.android2.interfaces.OnClickListener


class NewsAdapter(private val onClick:(position:Int)->Unit) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private var list = arrayListOf<News>()

    private var onClickListener: OnClickListener? = null



    inner class ViewHolder(private var binding: ItemNewsRvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(news: News) {
            binding.newsTitle.text = news.title
            binding.textDate.text = getDate(news.createdAt, "dd MMM yyyy")
            binding.textTime.text = getDate(news.createdAt, "HH:mm,")

            if (adapterPosition % 2 == 0) {
                binding.root.setBackgroundColor(Color.DKGRAY)
            } else {
                binding.root.setBackgroundColor(Color.GRAY)
            }
            itemView.setOnClickListener {
                onClick(position)
            }


        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNewsRvBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])

    }

    override fun getItemCount(): Int {
        return list.size

    }

    fun addItem(news: News) {
        list.add(0, news)
        notifyItemInserted(0)
    }

    fun addItems(list: List<News>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun addList(list: List<News>) {
        this.list = list as ArrayList<News>
        Collections.reverse(this.list)
        notifyDataSetChanged()
    }

    fun updateItem(news: News?, position: Int) {
        list[position] = news!!
        notifyItemChanged(position)
    }





    fun getItem(pos: Int): News {
        return list[pos]
    }

    fun replaceItem(news: News, position: Int) {
        list.set(position, news)
        notifyItemChanged(position)
    }


    fun getDate(milliSeconds: Long, dateFormat: String): String {
        val formater = SimpleDateFormat(dateFormat)

        val calendar = Calendar.getInstance();
        calendar.timeInMillis = milliSeconds;
        return formater.format(calendar.time);
    }

}


