package com.example.android2.ui.home

import android.os.Build
import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.android2.App
import com.example.android2.R
import com.example.android2.databinding.FragmentHomeBinding
import com.example.android2.models.News
import com.example.android2.ui.news.NewsAdapter

import android.text.Editable
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentResultListener


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: NewsAdapter
    private var ischanged : Boolean = false
    private var list = arrayListOf<News>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        adapter = NewsAdapter{
            val news = adapter.getItem(it)

            val bundle = Bundle()
            Toast.makeText(requireContext(),it.toString(),Toast.LENGTH_SHORT).show()
            bundle.putSerializable("news",news)
            ischanged = true
            findNavController().navigate(R.id.newsFragment,bundle)


        }
        val list = App.database.newsDao().getAll()
        adapter.addItems(list!!)



    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.onItemLongClick {
            Toast.makeText(requireContext(),"ntcn",Toast.LENGTH_SHORT).show()
        }

        binding.FloatBtn.setOnClickListener {
            findNavController().navigate(R.id.newsFragment)
        }
        parentFragmentManager.setFragmentResultListener(
            "news",
            viewLifecycleOwner
        ) { requestKey, bundle ->

            val news = bundle.getSerializable("news") as News
            val position:Int? = null
            if (ischanged){
                position?.let {
                    adapter.replaceItem(news,it)
                }
            }else{
                adapter.addItem(news)

            }

            Log.e("home", "text ${news.title }${news.createdAt}")
            Toast.makeText(requireContext(), news.title , Toast.LENGTH_SHORT).show()

        }

        binding.recyclerView.adapter = adapter



        binding.searchViewRecycle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                list = App.database.newsDao().getSearch(s.toString()) as ArrayList<News>
                adapter.addList(list);
            }


        }
        )
        binding.recyclerView.adapter = adapter
        list = App.database.newsDao().sortAll() as ArrayList<News>
        adapter.addList(list)





    }






    override fun onDestroyView() {
        super.onDestroyView()

    }
}