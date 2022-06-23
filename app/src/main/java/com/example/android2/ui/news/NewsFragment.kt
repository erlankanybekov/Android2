package com.example.android2.ui.news

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.android2.databinding.NewsFragmentBinding
import com.example.android2.models.News
import java.text.SimpleDateFormat
import java.util.*
import java.util.ResourceBundle.getBundle

class NewsFragment : Fragment() {

    private lateinit var binding: NewsFragmentBinding
    private val adapter = NewsAdapter(this::onClick)


    private fun onClick(news: News, position: Int) {

    }

    companion object {
        fun newInstance() = NewsFragment()
    }

    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NewsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.SaveBtn.setOnClickListener {
            save()
        }
    }



    private fun save() {
        val text = binding.newsED.text.toString().trim()
        val bundle = Bundle()
        bundle.putString("text", text)
        val news = News(text, System.currentTimeMillis())
        bundle.putSerializable("news", news)
        parentFragmentManager.setFragmentResult("news", bundle)
        findNavController().navigateUp()


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}