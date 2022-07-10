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
        binding.img1.setOnClickListener {
            getContent1.launch("image/*")
        }
        binding.img2.setOnClickListener {
            getContent2.launch("image/*")
        }

        Prefs = Prefs(requireContext())

        if (Prefs.isImageView() != null){
            Glide.with(binding.imgProfile).load(Prefs.isImageView()).circleCrop().into(binding.imgProfile)
        }

        if (Prefs.isImageView1() != null){
            Glide.with(binding.img1).load(Prefs.isImageView1()).centerCrop().into(binding.img1)
        }
        if (Prefs.isImageView2() != null){
            Glide.with(binding.img2).load(Prefs.isImageView2()).centerCrop().into(binding.img2)
        }

    }

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            Prefs.saveImageView(uri.toString())
            Glide.with(binding.imgProfile).load(uri).circleCrop().into(binding.imgProfile)
        }

    private val getContent1 =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            Prefs.saveImageView1(uri.toString())
            Glide.with(binding.img1).load(uri).centerCrop().into(binding.img1)
        }

    private val getContent2 =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                Prefs.saveImageView2(uri.toString())
                Glide.with(binding.img2).load(uri).centerCrop().into(binding.img2)
            }




}


