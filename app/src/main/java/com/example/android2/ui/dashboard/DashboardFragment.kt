package com.example.android2.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.android2.databinding.FragmentDashboardBinding
import com.example.android2.models.News
import com.example.android2.ui.news.NewsAdapter
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding
    private lateinit var adapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDashboardBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = NewsAdapter {

                deleteDataLive(it)

        }




    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.dashrecycler.adapter = adapter
     //   getData()
        getDataLive()

    }

    private fun getDataLive() {

        Firebase.firestore.collection("news")
            .orderBy("createdAt",Query.Direction.DESCENDING)
            .addSnapshotListener{value,error->
            val list = value?.toObjects(News::class.java)
            list?.let {
                adapter.addItems(it)
            }
        }
    }

    private fun deleteDataLive(pos:Int) {

            val query = Firebase.firestore.collection("news")
                .whereEqualTo("title",binding.editQuery.text.toString().trim())
                .get()
                 query.addOnSuccessListener {
                for ( document in it){
                    Firebase.firestore.collection("news").document(document.id)
                        .delete().addOnSuccessListener {
                        Toast.makeText(requireContext(),"успешно удалено ID: ${document.id}",Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Dash", "Error getting documents: ", exception)
            }

    }


    private fun getData(){
        Firebase.firestore.collection("news").get()
            .addOnSuccessListener {
                val list = it.toObjects(News::class.java)
                adapter.addItems(list)
            }
    }




}
