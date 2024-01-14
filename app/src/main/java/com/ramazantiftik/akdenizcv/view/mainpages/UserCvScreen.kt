package com.ramazantiftik.akdenizcv.view.mainpages

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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ramazantiftik.akdenizcv.databinding.FragmentUserCvScreenBinding
import com.ramazantiftik.akdenizcv.viewmodel.UserCvScViewModel
import com.squareup.picasso.Picasso
import java.util.UUID


class UserCvScreen : Fragment() {

    //viewBinding
    private var _binding: FragmentUserCvScreenBinding?=null
    private val binding get() = _binding!!

    //viewModel
    lateinit var viewModel: UserCvScViewModel

    //permission for select image & select image
    private lateinit var activityResulLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private var selectedImage: Uri?=null

    //firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var currentUser: String

    //dataList from viewModel
    private lateinit var dataList: ArrayList<String>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //viewBinding
        _binding= FragmentUserCvScreenBinding.inflate(inflater,container,false)
        val view=binding.root

        //viewModel initialize
        viewModel=ViewModelProvider(requireActivity()).get(UserCvScViewModel::class.java)

        //dataList initialize
        dataList= ArrayList()

        //firebase initialize
        auth= Firebase.auth
        firestore= Firebase.firestore
        currentUser= Firebase.auth.currentUser!!.uid

        //select image permission fun
        registerLauncher()

        //get student's data from firebase
        getData()


        //*****CLICKS*****


        binding.userPhotoImageViewCvSc.setOnClickListener {
            selectImage(it)
        }

        binding.saveBtnUserCvSc.setOnClickListener {
            setData()
            Toast.makeText(requireContext(),"Bilgileriniz Başarı Bir Şekilde Kaydedildi",Toast.LENGTH_LONG).show()
        }


        //*****Nav Buttons*****

        //go to userProfile
        binding.profileBtnUserCvSc.setOnClickListener {
            val action= UserCvScreenDirections.actionUserCvScreenToUserProfileScreen()
            Navigation.findNavController(it).navigate(action)
        }

        //go to homePage
        binding.homePageBtnUserCvSc.setOnClickListener {
            val action= UserCvScreenDirections.actionUserCvScreenToFeedScreen()
            Navigation.findNavController(it).navigate(action)
        }


        // Inflate the layout for this fragment
        return view
    }


    private fun setData(){

        firestore.collection("Student").document(currentUser).update(
            "data",binding.userBirthDateEditTxtUserCvSc.text.toString(),
            "phone",binding.userPhoneEditTxtUserCvSc.text.toString(),
            "place",binding.userAddressEditTxtUserCvSc.text.toString(),
            "linkedin",binding.userLinkedInEditTxtUserCvSc.text.toString(),
            "github",binding.userGithubEditTxtUserCvSc.text.toString(),
            "career",binding.summaryEditTxtUserCvSc.text.toString(),
            "university",binding.userUniversityEditTxtUserCvSc.text.toString(),
            "position",binding.userPositionEditTxtUserCvSc.text.toString(),
            "office",binding.userOfficeEditTxtUserCvSc.text.toString(),
            "programming",binding.userProgramingEditTxtUserCvSc.text.toString(),
            "other",binding.userOthersEditTxtUserCvSc.text.toString(),
            "social",binding.userSocialEditTxtUserCvSc.text.toString(),
            "language",binding.userLanguageEditTxtUserCvSc.text.toString(),
            "course",binding.userCourseEditTxtUserCvSc.text.toString(),
            "professional",binding.userProfessionalEditTxtUserCvSc.text.toString(),
            "hobby",binding.userHobbyEditTxtUserCvSc.text.toString()
        )

    }


    private fun getData(){
        firestore.collection("Student").document(currentUser).get().addOnSuccessListener {

            val showProfile = it.data?.get("showProfile").toString()
            val openWork = it.data?.get("openWork").toString()
            val email = it.data?.get("email").toString()
            val userName = it.data?.get("userName").toString()
            val userPictureUrl = it.data?.get("pictureUrl").toString()
            val dateOfBirth = it.data?.get("date").toString()
            val phone = it.data?.get("phone").toString()
            val place = it.data?.get("place").toString()
            val linkedin = it.data?.get("linkedin").toString()
            val github = it.data?.get("github").toString()
            val career = it.data?.get("career").toString()
            val university = it.data?.get("university").toString()
            val position = it.data?.get("position").toString()
            val office = it.data?.get("office").toString()
            val programming = it.data?.get("programming").toString()
            val other = it.data?.get("other").toString()
            val social = it.data?.get("social").toString()
            val language = it.data?.get("language").toString()
            val course = it.data?.get("course").toString()
            val professional = it.data?.get("professional").toString()
            val hobby = it.data?.get("hobby").toString()
            val reference = it.data?.get("reference").toString()


            if (userPictureUrl != null){
                Picasso.get().load(userPictureUrl).into(binding.userPhotoImageViewCvSc)
            }
            binding.userNameTxtUserCvSc.text=userName
            binding.summaryEditTxtUserCvSc.setText(career)
            binding.userEmailEditTxtUserCvSc.setText(email)
            binding.userPhoneEditTxtUserCvSc.setText(phone)
            binding.userBirthDateEditTxtUserCvSc.setText(dateOfBirth)
            binding.userAddressEditTxtUserCvSc.setText(place)
            binding.userLinkedInEditTxtUserCvSc.setText(linkedin)
            binding.userGithubEditTxtUserCvSc.setText(github)
            binding.userUniversityEditTxtUserCvSc.setText(university)
            binding.userPositionEditTxtUserCvSc.setText(position)
            binding.userOfficeEditTxtUserCvSc.setText(office)
            binding.userProgramingEditTxtUserCvSc.setText(programming)
            binding.userSocialEditTxtUserCvSc.setText(social)
            binding.userOthersEditTxtUserCvSc.setText(other)
            binding.userLanguageEditTxtUserCvSc.setText(language)
            binding.userCourseEditTxtUserCvSc.setText(course)
            binding.userProfessionalEditTxtUserCvSc.setText(professional)
            binding.userHobbyEditTxtUserCvSc.setText(hobby)
            binding.userReferenceEditTxtUserCvSc.setText(reference)

        }
    }

    private fun registerLauncher(){

        activityResulLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode== AppCompatActivity.RESULT_OK){

                val intentFromResult=result.data
                if (intentFromResult!=null){

                    selectedImage=intentFromResult.data
                    selectedImage.let {
                        binding.userPhotoImageViewCvSc.setImageURI(it)
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
    
    private fun selectImage(view: View){

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
                val intentToGallery=Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
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
                val intentToGallery=Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResulLauncher.launch(intentToGallery)
            }

        }

    }


}