package com.example.android2.ui.Profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.android2.databinding.ProfileFragmentBinding
import java.lang.Exception

class ProfileFragment : Fragment() {

    private lateinit var binding: ProfileFragmentBinding
    var selectedImage : Uri?=null
    var selectedBitmap:Bitmap?=null

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProfileFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imgProfile.setOnClickListener {
            openGallery(it)
        }
    }

     fun openGallery(view: View) {
        activity?.let {
            if (ContextCompat.checkSelfPermission(it.applicationContext,android.Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1)
            }else{
            val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent,2)
            }
        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode==1){
            if (grantResults.size> 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent,2)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 2 && requestCode == Activity.RESULT_OK && data!=null){
            selectedImage  = data.data
        }
        try {
            context?.let {
                if (selectedImage != null){
                    if (Build.VERSION.SDK_INT>=28){
                        val source = ImageDecoder.createSource(it.contentResolver,selectedImage!!)
                        selectedBitmap = ImageDecoder.decodeBitmap(source)
                        binding.imgProfile.setImageBitmap(selectedBitmap)
                    } else{
                        selectedBitmap = MediaStore.Images.Media.getBitmap(it.contentResolver,selectedImage)
                        binding.imgProfile.setImageBitmap(selectedBitmap)
                    }
                }
            }

        }catch (e:Exception){
            e.printStackTrace()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

}