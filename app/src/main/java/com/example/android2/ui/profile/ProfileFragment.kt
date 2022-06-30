package com.example.android2.ui.profile

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.android2.Prefs
import com.example.android2.databinding.ProfileFragmentBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: ProfileFragmentBinding
    private lateinit var Prefs:Prefs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imgProfile.setOnClickListener {
            getContent.launch("image/*")
        }
        Prefs = Prefs(requireContext())
        binding.editProfile.setText(Prefs.isEditText())



        binding.profileBtn.setOnClickListener {

            saveData()

        }


    }

    private fun saveData() {
        Prefs = Prefs(requireContext())
        Prefs.saveEditText(binding.editProfile.text.toString())



    }


    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            Glide.with(binding.imgProfile).load(uri).circleCrop().into(binding.imgProfile)
        }




}