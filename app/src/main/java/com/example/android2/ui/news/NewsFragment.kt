package com.example.android2.ui.news

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.android2.App
import com.example.android2.databinding.NewsFragmentBinding
import com.example.android2.models.News
import java.text.SimpleDateFormat
import java.util.*
import java.util.ResourceBundle.getBundle

class NewsFragment : Fragment() {

    private lateinit var binding: NewsFragmentBinding
    private  var news: News? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NewsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


         news = arguments?.getSerializable("news") as News?

        news?.let {
            binding.newsED.setText(it.title)
        }
        binding.SaveBtn.setOnClickListener {
            save()
        }

    }



    private fun save() {
        val text = binding.newsED.text.toString().trim()
        val bundle = Bundle()

        if (news == null) {
            news = News(0,text, System.currentTimeMillis())
            App.database.newsDao().insert(news!!)


        } else {
            news?.title = text
        }

        bundle.putSerializable("news", news)
        parentFragmentManager.setFragmentResult("news", bundle)
        findNavController().navigateUp()
    }
}