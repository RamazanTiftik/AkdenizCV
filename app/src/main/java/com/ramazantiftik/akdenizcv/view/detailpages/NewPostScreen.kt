package com.ramazantiftik.akdenizcv.view.detailpages

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.ramazantiftik.akdenizcv.databinding.FragmentNewPostScreenBinding


class NewPostScreen : Fragment() {

    //viewBinding
    private var _binding: FragmentNewPostScreenBinding?=null
    private val binding get() = _binding!!

    //permission for select image & select image
    private lateinit var activityResulLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private var selectedImage: Uri?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //viewBinding
        _binding= FragmentNewPostScreenBinding.inflate(inflater,container,false)
        val view=binding.root

        //select image permission fun
        registerLauncher()


        //*****CLICKS*****

        //newPostScreen to userProfileSc
        binding.photoImageViewNewPostSc.setOnClickListener {
            val action= NewPostScreenDirections.actionNewPostScreenToUserProfileScreen()
            Navigation.findNavController(it).navigate(action)
        }

        //select photo
        binding.addPhotoLinearLayoutNewPostSc.setOnClickListener {
            selectImage(it)
        }

        //*****Nav Buttons*****

        //go to userProfile
        binding.profileBtnNewPostSc.setOnClickListener {
            val action= NewPostScreenDirections.actionNewPostScreenToUserProfileScreen()
            Navigation.findNavController(it).navigate(action)
        }

        //go to homePage
        binding.homePageBtnNewPostSc.setOnClickListener {
            val action= NewPostScreenDirections.actionNewPostScreenToFeedScreen()
            Navigation.findNavController(it).navigate(action)
        }

        binding.cvBtnNewPostSc.setOnClickListener {
            val action=NewPostScreenDirections.actionNewPostScreenToUserCvScreen()
            Navigation.findNavController(it).navigate(action)
        }


        // Inflate the layout for this fragment
        return view
    }


    private fun registerLauncher(){

        activityResulLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode== AppCompatActivity.RESULT_OK){

                val intentFromResult=result.data
                if (intentFromResult!=null){

                    selectedImage=intentFromResult.data
                    selectedImage.let {
                        binding.addPhotoBtnNewPostSc.setImageURI(it)
                    }
                }
            }
        }

        permissionLauncher=registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            if (result){
                //permission granted
                val intentToGallery=Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResulLauncher.launch(intentToGallery)

            } else {
                //permission denied
                Toast.makeText(requireContext(),"İzin Gerekli !", Toast.LENGTH_LONG).show()

            }
        }
    }


    fun selectImage(view: View){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            //Android 33+ --> READ_MEDIA_IMAGES

            if (ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED){
                if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),android.Manifest.permission.READ_MEDIA_IMAGES)){
                    //Rationale
                    Snackbar.make(view,"Galeri İzni Gerekli !", Snackbar.LENGTH_INDEFINITE).setAction("İzin Ver") {
                        //Request Permission
                        permissionLauncher.launch(android.Manifest.permission.READ_MEDIA_IMAGES)
                    }.show()

                } else {
                    //Request Permission
                    permissionLauncher.launch(android.Manifest.permission.READ_MEDIA_IMAGES)
                }


            } else {
                //Permission Granted
                val intentToGallery=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResulLauncher.launch(intentToGallery)
            }


        } else {
            //Android 32- -->READ_EXTERNAL_STORAGE

            if (ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),android.Manifest.permission.READ_EXTERNAL_STORAGE)){
                    //Rationale
                    Snackbar.make(view,"Galeri İzni Gerekli !", Snackbar.LENGTH_INDEFINITE).setAction("İzin Ver") {
                        //Request Permission
                        permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    }.show()

                } else {
                    //Request Permission
                    permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                }


            } else {
                //Permission Granted
                val intentToGallery=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResulLauncher.launch(intentToGallery)
            }

        }

    }


}