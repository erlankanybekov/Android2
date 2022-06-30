package com.example.android2.ui.board

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.android2.Prefs
import com.example.android2.databinding.FragmentBoardBinding


class BoardFragment : Fragment() {
    private lateinit var binding: FragmentBoardBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBoardBinding.inflate(inflater, container, false)
        return binding.root
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        val adapter = BoardAdapter {
            findNavController().navigateUp()
            Prefs(requireContext()).saveState()

        }
        binding.viewPager.adapter = adapter
        val dots= binding.springDotsIndicator
        val viewPager2 = binding.viewPager
        dots.setViewPager2(viewPager2)


        binding.fakeBtnSwipe.setOnClickListener {
            findNavController().navigateUp()
        }


        binding.viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                if (position == 2) {
                    binding.fakeBtnSwipe.visibility = View.GONE
                }
                else {
                    binding.fakeBtnSwipe.visibility = View.VISIBLE
                }
            }
        })

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }

            })

    }

}









