package com.example.android2.ui.board

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.android2.R
import com.example.android2.databinding.FragmentBoardBinding
import me.relex.circleindicator.CircleIndicator2


class BoardFragment : Fragment() {
    private lateinit var binding: FragmentBoardBinding

    private var images:List<Int>?=null
    lateinit var boardAdapter: BoardAdapter
    lateinit var indicator2: CircleIndicator2


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter =  BoardAdapter{

            findNavController().navigateUp()
        }
        binding.viewPager.adapter=adapter

        binding.viewPager.apply {
            beginFakeDrag()
            fakeDragBy(-10f)
            endFakeDrag()
        }


        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }

        })

    }


}